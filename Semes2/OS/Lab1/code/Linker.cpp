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
    string error_message;
    for (int i = 0; i < m_modules_list.size(); i++)
    {
        vector<Operation> temp_op_list = m_modules_list[i].GetOperationList();
        for (int j = 0; j < temp_op_list.size(); j++)
        {
            op_address = temp_op_list[j].GetAbsoluteAddress();
            error_message = temp_op_list[j].GetErrorMessage();
            cout << std::setfill('0') << std::setw(3) << mem_count << ": " << 
                std::setfill('0') << std::setw(4) << op_address << " " << 
                error_message << endl;
            mem_count = mem_count + 1;
        }
        // Print out Warnings
        for (int k = 0; k < m_modules_list[i].m_warning_list.size(); k++)
        {
            cout << m_modules_list[i].m_warning_list[k];
        }
    }

    cout << "\n";
    int tmp_cmp_use_int; 
    int module_number = -1;
    string tmp_cmp_use_name, correct_name;
    for (int i = 0; i < m_entire_def_list.size(); i++)
    {
        for (int j = 0; j < m_modules_list.size(); j++)
        {
            vector<Symbol> temp_use_list = m_modules_list[j].GetUseList();
            for (int k = 0; k < temp_use_list.size(); k++)
            {
                tmp_cmp_use_name = temp_use_list[k].GetName();
                correct_name = m_entire_def_list[i].GetName();
                tmp_cmp_use_int = tmp_cmp_use_name.compare(correct_name);
                module_number = j + 1;
                if (tmp_cmp_use_int == 0)
                {
                    m_entire_def_list[i].SetUsed(true);
        //            cout << "Warning: Module " << module_number << " " << correct_name << " was defined but never used\n";
                }
            }
        } 
    }

    for (int i = 0; i < m_entire_def_list.size(); i++)
    {
        if (!m_entire_def_list[i].GetUsed())
        {
            int test_number, module_address, def_symb_address;
            bool found = false;
            for(int j = m_modules_list.size(); j > 0; j--)
            {
                test_number = j - 1; 
                module_address = m_modules_list[test_number].GetGlobalAddress();
                def_symb_address = m_entire_def_list[i].GetAddress();  
               if (module_address > def_symb_address)
               {
                    found = true;
                    cout << "Warning: Module " << j << ": " << m_entire_def_list[i].GetName() << " was defined but never used\n";
               }
            }
            if (!found)
            {
                    cout << "Warning: Module " << m_modules_list.size() << ": " << m_entire_def_list[i].GetName() << " was defined but never used\n";
            }
        }
    }

    for (int i = 0; i < m_post_mem_warns.size(); i++)
    {
        cout << m_post_mem_warns[i];
    }
    cout << "\n";
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
    bool can_add_def = true;


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
        temp_symbol.m_relative_address = relative_address;
        
        // Check if the element has already been defined
        // ERROR 2
        int temp_compare;
        string def_compare_string;
        for (int j = 0; j < m_entire_def_list.size(); j++)
        {
            def_compare_string = m_entire_def_list[j].GetName();
            temp_compare = def_compare_string.compare(symbol_name);
            
            // Already defined
            if (temp_compare == 0)
            {
                string error_msg = " Error: This variable is multiple times defined; first value used";
                m_entire_def_list[j].SetErrorMessage(error_msg); 
                can_add_def = false;
            }
        }

        // Add completed symbols to lists
        if (can_add_def)
        {
            m_entire_def_list.push_back(temp_symbol);
            ModPointer->AddToDefList(temp_symbol);
        }
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
        //relative_address = temp_def_list[i].GetAddress();
        relative_address = temp_def_list[i].m_relative_address;
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
            int foo_compare;
            for (int j = 0; j < m_entire_def_list.size(); j++)
            {
                foo_compare = symbol_name.compare(m_entire_def_list[j].GetName());
                if (foo_compare == 0)
                {
                    m_entire_def_list[j].SetAddress(0);
                }
            }
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
                    // RULE 4 Might need later
                    // if (compare_sym_names == 0)
                    // {
                    //     m_entire_def_list[j].SetUsed(true);
                    // }
                }
            }
        }
    } 

    //printing Error
    string warning_string; 
    int mod_number = m_modules_list.size() + 1;

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
    string error_message;

    for (int i = 0; i < codecount; i++)
    {
        type = ExtractOpType();
        instruction = ExtractNumber();
        address = instruction;
        //
        // Error 11
        // Handle E Type
        if (type == 'E')
        {
            int desired_operation_address = -1;
            int relative_e_location = instruction % 1000; 
            vector<Symbol> use_list = Mod.GetUseList(); 
            if (relative_e_location >= use_list.size())
            {
                // ERROR 6 DONT CHANGE ADDRESS TREATING AS IM
                error_message = "Error: External address exceeds length of uselist; treated as immediate";
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
                        // If we never hit this, this E has not been defined
                        // We check to see if desired_operation is not negative
                        desired_operation_address = it->GetAddress();
                    }
                }
                if (desired_operation_address == -1)
                {
                    // We never updated desired address, so it was not defined
                    // Error 3
                    error_message = "Error: " + symbol_name + " is not defined; zero used"; 
                    desired_operation_address = 0;
                }
                address = instruction - relative_e_location;
                address = address + desired_operation_address;
                // temp_op->SetInstruction(instruction);
            }
        }
        if (type == 'R')
        {
            // Check for error 9
            int address_spot = instruction % 1000;
            int mod_size = Mod.GetNumberOfLines();
            if (address_spot > mod_size)
            {
                // ERROR
                address = instruction - address_spot + Mod.GetGlobalAddress();
                error_message = "Error: Relative address exceeds module size; zero used";
            }
            else
                address = instruction + Mod.GetGlobalAddress();
        }

        if (type == 'A')
        {
            int address_spot = instruction % 1000;
            // Rule 8
            if (address_spot > 511)
            {
                error_message = "Error: Absolute address exceeds machine size; zero used";
                address = instruction - address_spot;
            } 
        }

        if (type == 'I')
        {
            if (instruction > 9999)
            {
                error_message = "Error: Illegal immediate value; treated as 9999";
                address = 9999;
            }
        } 
        else
        {
            if (instruction > 9999)
            {
                error_message = "Error: Illegal opcode; treated as 9999";
                address = 9999;
            }
        }
        // Place op on the operation list
        Operation *temp_op = new Operation(type, instruction, address);
        temp_op->SetErrorMessage(error_message);
        m_operation_list.push_back(*temp_op);
        
        // Add to Models Operation List
        vector<Operation> temp_op_list = Mod.GetOperationList();
        temp_op_list.push_back(*temp_op);
        Mod.SetOperationList(temp_op_list);
        error_message = "";
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
