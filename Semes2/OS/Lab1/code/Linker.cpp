#include "Linker.h"

ifstream stream;

Linker::Linker(char *filename){
    m_stream_prev_line_last_offset = 0;
    m_stream_prev_line_last_offset_2 = 0;
    m_stream_line_number = 1;
    m_last_line_newline = false;
    m_last_line_and_prev_newline = false;
    StartLinker(filename);
}

void Linker::StartLinker(char *filename){
    m_input_file_name = filename;
    //ifstream fin = new ifstream();
    //ifstream fin;
    stream.open(filename); //Open File
    if (!stream.good())
        //return 1; // Exit if file not found
        std::cout << "Could not open File: " << filename << std::endl;
    
    char c;
    c = stream.peek();
    StreamLastCharNewline();

    stream.close();
    stream.open(filename); //Open File
    if (!stream.good())
        //return 1; // Exit if file not found
        std::cout << "Could not open File: " << filename << std::endl;


    //Parse Module
    ParseOneSetUp();

}

//Getters and Setters
void Linker::SetDefList(vector<Symbol> def_list){
    m_entire_def_list = def_list;
}

vector<Symbol> Linker::GetDefList(){
  return m_entire_def_list; 
}

void Linker::SetModulesList(vector<Module> modules_list){
   m_modules_list = modules_list;
}

vector<Module> Linker::GetModList(){
  return m_modules_list;
}

char* Linker::GetInputFileName(){
    return m_input_file_name;
}

// Functionality
char Linker::StreamGet(){
    char c;
    stream.get(c);
    
    // Ensure we keep track of Streams current 
    // Line and Offset Number
    if (c == '\n')
    {
        m_stream_line_number += 1;
        m_stream_prev_line_last_offset_2 = m_stream_prev_line_last_offset;
        m_stream_prev_line_last_offset = m_stream_offset_number;
        m_stream_offset_number = 0;
    }
    else
        m_stream_offset_number += 1;

    return c;
}

void Linker::ParseOneSetUp(){
   
    ParseOneModule(0);

    int nextAddress;
    while(!PeekEnd())
    {
        nextAddress = m_modules_list.back().GetGlobalAddress() + 
          m_modules_list.back().GetNumberOfLines(); 
        ParseOneModule(nextAddress);
    }
    
    //Print Pre Symbol warnings
    for (int i = 0; i < m_pre_symb_warns.size(); i++)
    {
        cout << m_pre_symb_warns[i];
    }

    //Print Symbol Table and Start Parse two
    cout << "Symbol Table" << endl;
    
    for (vector<Symbol>::iterator it = m_entire_def_list.begin(); 
            it != m_entire_def_list.end(); ++it)
    {
        it->PrintForTable();
    }
    cout << "\n";

    //Start Parse 2
    //Reset Stream
    stream.close();
    stream.open(m_input_file_name); //Open File
    if (!stream.good())
        //return 1; // Exit if file not found
        std::cout << "Could not open File: " << m_input_file_name << std::endl;

    ParseTwoSetUp();

    // Print off Memory Map
    cout<< "Memory Map" << endl;
    int op_address; 
    int mem_count = 0;
    for (int i = 0; i < m_modules_list.size(); i++)
    {
        vector<Operation> temp_op_list = m_modules_list[i].GetOperationList();
        for (int j = 0; j < temp_op_list.size(); j++)
        {
            op_address = temp_op_list[j].GetAbsoluteAddress();
            cout << std::setfill('0') << std::setw(3) << mem_count << ": " << op_address << endl;
            mem_count = mem_count + 1;
        }
        // Print out Warnings
        for (int k = 0; k < m_modules_list[i].m_warning_list.size(); k++)
        {
            cout << m_modules_list[i].m_warning_list[k];
        }
    }

    // for (int i = 0; i < m_operation_list.size(); i++)
    // {
    //     op_address = m_operation_list[i].GetAbsoluteAddress();
    //     cout << std::setfill('0') << std::setw(3) << i << ": " << op_address << endl;
    // } 

    // Print Post Mem Warnings
    for (int i = 0; i < m_post_mem_warns.size(); i++)
    {
        cout << m_post_mem_warns[i];
    }
}

void Linker::ParseOneModule(int global_address){
    char c;
    int count; 
    string first_symbol_name;

    // Set up Module
    Module* TempModulePointer = new Module(global_address);
    TempModulePointer->SetGlobalAddress(global_address);
    ParseOneDefList(TempModulePointer);
    ParseOneUseList(TempModulePointer);
    ParseOneOperationList(TempModulePointer); 
   
    // Print Module this far 
    //TempModulePointer->PrintCurrentStatus();

    // We have now finished module
    m_modules_list.push_back(*TempModulePointer);
    ReadUntilCharacter();
    delete TempModulePointer;
}

int Linker::ExtractNumber() {
    string number;
    char c;
    ReadUntilCharacter();

    //We have reached End of file Expect Number
    if (PeekEnd())
    {
        PrintEOFParseError(0);
    } 

    while (!PeekEnd())
    {
        c = StreamGet();

        if ((c != ' ') && (c != '\t') && (c != '\n'))
        {
            // Error Check
            if (!isdigit(c))
            {
                PrintParseError(0);
            }

            number += c; 
        }
        else
            break;
    }
    return atoi(number.c_str());
    // Number error at end of file

}

//Extracts Symbol Name
string Linker::ExtractSymbolName() {
    string SymbolName;
    char c;
    ReadUntilCharacter();
    
    //We have reached End of file Expect Number
    if (stream.eof())
    {
        PrintEOFParseError(1);
    } 

    // Error Check
    c = stream.peek();
    if (!isalpha(c))
    {
        // This get is so we update the cursor pos correctly
        c = StreamGet();
        PrintParseError(1);
    }
     
    int temp_offset = m_stream_offset_number;
    // Extract Symbol
    while (!PeekEnd())
    {
        c = StreamGet();
        if ((c != ' ') && (c != '\t') && (c != '\n'))
        {    
            SymbolName += c; 
        }
        else
        {
            if (SymbolName.length() > 16)
            {
                m_stream_offset_number = temp_offset + 1;
                PrintParseError(3);
            }
            return SymbolName;
        }
    }
    // Should never get here
    return SymbolName;
}

char Linker::ExtractOpType(){
    char type;
    ReadUntilCharacter();
    if (stream.eof())
    {
        PrintEOFParseError(2);
    }
    type = StreamGet();
    if ((type == 'I') || (type == 'A') || (type == 'R') || (type == 'E'))
    {
        return type;
    }
    // Error Occurred
    PrintParseError(2);
    return type;
}

// Stream points after read first symbol name in DefList
void Linker::ParseOneDefList(Module *ModPointer) {
    string symbol_name;
    int relative_address;
    int symbol_absolute_address; 

    ReadUntilCharacter();
    int temp_offset = m_stream_offset_number;
    int count = ExtractNumber();
    if (count > 16)
    {
        m_stream_offset_number = temp_offset + 1;
        PrintParseError(4);
    }

    for (int i = 0; i < count; i++)
    {
        symbol_name = ExtractSymbolName();

        // Give Exact Address based on Module address
        relative_address =  ExtractNumber();

        symbol_absolute_address = relative_address + ModPointer->GetGlobalAddress();
        Symbol temp_symbol (symbol_name, symbol_absolute_address);

        // Add completed symbols to lists
        m_entire_def_list.push_back(temp_symbol);
        ModPointer->AddToDefList(temp_symbol);
    }
}

// Stream points after read first symbol name in DefList
void Linker::ParseOneUseList(Module *ModPointer) {
    string symbol_name;
    int relative_address;
    
    ReadUntilCharacter();
    int temp_offset = m_stream_offset_number;
    int count = ExtractNumber();
    
    if (count > 16)
    {
        m_stream_offset_number = temp_offset + 1;
        PrintParseError(5);
    }
    // For the amount of defcount
    for (int i = 0; i < count; i++)
    {
        symbol_name = ExtractSymbolName();
        Symbol temp_symbol (symbol_name);
        ModPointer->AddToUseList(temp_symbol);
    }  
}

void Linker::ParseOneOperationList(Module *ModPointer) {
    int codecount;
    char type;
    int instruction;

    ReadUntilCharacter();
    int temp_offset = m_stream_offset_number;
    codecount = ExtractNumber();
    ModPointer->SetNumberOfLines(codecount);
    
    // Check for Rule 5
    vector<Symbol> temp_def_list = ModPointer->GetDefList(); 
    vector<Symbol> temp_use_list = ModPointer->GetUseList();
    int relative_address;
    string symbol_name;
    for (int i = 0; i < temp_def_list.size(); i++)
    {
        relative_address = temp_def_list[i].GetAddress();
        symbol_name = temp_def_list[i].GetName();
        if (relative_address > codecount)
        {
            //Add Error
            //Set to 0
            //int mod_number = FindWhichModWithAddress(ModPointer);
            int mod_number = m_modules_list.size() + 1;
            string rule_5_error = "Warning: Module " + to_string(mod_number) +
               ": " + symbol_name + " to big " + to_string(relative_address) + 
               " (max=" + to_string(codecount-1) + ") assume zero relative\n"; 
            m_pre_symb_warns.push_back(rule_5_error);
            relative_address = 0;
        }
    }

    // Continue after rule 5 check
    m_total_instructions += codecount;
    if (m_total_instructions > 512)
    {
        m_stream_offset_number = temp_offset + 1;
        PrintParseError(6);
    }
    
    // Checking for rule 4 7
    // While incrementing stream
    for (int i = 0; i < codecount; i++)
    {
        type = ExtractOpType();
        instruction = ExtractNumber();
        if (type == 'E')
        {
            /// Logic for rule 4/7
            string temp_symbol_name; 
            int temp_inst = instruction;
            temp_inst = temp_inst % 1000;
            // Check for rule 6
            if (temp_inst >= temp_use_list.size())
            {
                //This means we have Rule 6 going down
            }
            else
            {
                temp_use_list[temp_inst].SetUsed(true);
                temp_symbol_name = temp_use_list[temp_inst].GetName(); 
                for (int j = 0; j < m_entire_def_list.size(); j++)
                {
                    int compare_sym_names = 
                        temp_symbol_name.compare(m_entire_def_list[j].GetName());
                    if (compare_sym_names == 0)
                        m_entire_def_list[j].SetUsed(true);
                }
            }
        }
    } 

    //printing Error
    string warning_string; 
    int mod_number = m_modules_list.size() + 1;
    // Rule 4
    // NEED TO WORK ON
    // Need to do this after entire module
    // for (int i = 0; i < temp_def_list.size(); i++)
    // {
    //     if (!temp_def_list[i].GetUsed())
    //     {
    //         warning_string = "Warning: Module " + to_string(mod_number) +
    //             ": " + temp_def_list[i].GetName() + " was defined but never used\n";
    //         m_post_mem_warns.push_back(warning_string);
    //     }
    // }

    // Rule 7
    for (int i = 0; i < temp_use_list.size(); i++)
    {
        if (!temp_use_list[i].GetUsed())
        {
            warning_string = "Warning: Module " + to_string(mod_number) +
                ": " + temp_use_list[i].GetName() +
                " appeared in the uselist but was not actually used\n"; 
            //m_post_mem_warns.push_back(warning_string);
            ModPointer->m_warning_list.push_back(warning_string);
        }
    }
}

void Linker::ParseTwoSetUp(){
    // Make sure we get into each Module the right mod
   for (int i = 0; i < m_modules_list.size(); i++)
   {
       ParseTwoDefList(m_modules_list[i]);
       ParseTwoUseList(m_modules_list[i]);
       ParseTwoOperationList(m_modules_list[i]);
   }  
}

void Linker::ParseTwoDefList(Module &Mod) {
    ReadUntilCharacter();
    int count = ExtractNumber();
    string symbol_name;
    int relative_address;
    for (int i = 0; i < count; i++)
    {
        symbol_name = ExtractSymbolName();
        relative_address = ExtractNumber();
    }
}

void Linker::ParseTwoUseList(Module &Mod) {
    string symbol_name;
    int relative_address;

    ReadUntilCharacter();
    int count = ExtractNumber();
    for (int i = 0; i < count; i++)
    {
        symbol_name = ExtractSymbolName();
    }
}

void Linker::ParseTwoOperationList(Module &Mod){
    ReadUntilCharacter();
    int codecount = ExtractNumber();
    char type;
    int instruction;
    int address;
        
    for (int i = 0; i < codecount; i++)
    {
        type = ExtractOpType();
        instruction = ExtractNumber();
        address = instruction;
        //
        // Handle E Type
        if (type == 'E')
        {
            int desired_operation_address;
            int relative_e_location = instruction % 1000; 
            vector<Symbol> use_list = Mod.GetUseList(); 
            if (relative_e_location >= use_list.size())
            {
                // ERROR 4 DONT CHANGE ADDRESS TREATING AS IM
            }
            else
            {
                Symbol correct_symbol = use_list[relative_e_location];
                string symbol_name = correct_symbol.GetName(); 
                string temp_symbol_name;
                int temp_compare_int;
                // Grab from our Def list
                
                for (vector<Symbol>::iterator it = m_entire_def_list.begin(); 
                        it != m_entire_def_list.end(); ++it)
                {
                    temp_symbol_name = it->GetName();
                    temp_compare_int = symbol_name.compare(temp_symbol_name);
                    if (temp_compare_int == 0)
                    {
                        desired_operation_address = it->GetAddress();
                    }
                }
                address = instruction - relative_e_location;
                address = address + desired_operation_address;
                // temp_op->SetInstruction(instruction);
            }
        }
        if (type == 'R')
        {
            address = instruction + Mod.GetGlobalAddress();
        }

        // Place op on the operation list
        Operation *temp_op = new Operation(type, instruction, address);
        m_operation_list.push_back(*temp_op);
        
        // Add to Models Operation List
        vector<Operation> temp_op_list = Mod.GetOperationList();
        temp_op_list.push_back(*temp_op);
        Mod.SetOperationList(temp_op_list);

    }    
}

void Linker::ReadUntilCharacter(){
    char c;
    while(!PeekEnd())
    {
        c = stream.peek();
        if ((c == ' ') || (c == '\t') || (c == '\n'))
        {
            c = StreamGet();
        }
        else
            return;
    }
}

void Linker::PrintEOFParseError(int errcode) {
    // Check if the last char is a "\n"
    // If so, update some data and send to reg Pars Error
    //
    m_stream_line_number -= 1;
    if (m_last_line_newline && !m_last_line_and_prev_newline)
    {
        m_stream_line_number -= 1;
        m_stream_offset_number = m_stream_prev_line_last_offset_2 + 1;
        // 1 or 2, must check
    }
    else
    {
        m_stream_offset_number = m_stream_prev_line_last_offset + 1;
    }

    PrintParseError(errcode);
}

void Linker::PrintParseError(int errcode){
    const string errstr[] = {
        "NUM_EXPECTED",
        "SYM_EXPECTED",
        "ADDR_EXPECTED",
        "SYM_TOLONG",
        "TO_MANY_DEF_IN_MODULE",
        "TO_MANY_USE_IN_MODULE",
        "TO_MANY_INSTR",
    };
    cout << "Parse Error line " << m_stream_line_number << " offset " 
        << m_stream_offset_number << ": " << errstr[errcode] << endl;
    exit(1);
}

int Linker::FindWhichModWithAddress(Module *mod){
    int mod_address, temp_mod_address;
    mod_address = mod->GetGlobalAddress(); 
    for (int i = 0; i < m_modules_list.size(); i++)
    {
        temp_mod_address = m_modules_list[i].GetGlobalAddress();
        if (temp_mod_address == mod_address)
        {
            return i + 1;
        }  
    }
    
    // Failure
    return -1;
}
void Linker::StreamLastCharNewline(){
    char c, d;
    int current_position = stream.tellg();

    // Get Last Char
    stream.seekg(0, stream.end);
    int length = stream.tellg();
    stream.seekg(length-3, stream.beg);
    stream.get(c);
    stream.get(d);
    // Set back to original Pos
    stream.seekg(current_position, stream.beg);
    if (d == '\n')
        m_last_line_newline = true;
    if ((c == '\n') && (d == '\n'))
        m_last_line_and_prev_newline = true;
}

bool Linker::PeekEnd(){
    // int c = stream.peek();
    // if (c == EOF)
    //     return true;
    // return false;
    return (stream.peek(), stream.eof());
}
