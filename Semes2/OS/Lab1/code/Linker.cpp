#include "Linker.h"

ifstream stream;

Linker::Linker(char *filename){
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
        m_stream_offset_number = 0;;
    }
    else
        m_stream_offset_number =+ 1;

    return c;
}
void Linker::ParseOneSetUp(){
    ParseOneModule(0);

    int nextAddress;
    while(!stream.eof())
    {
        nextAddress = 1 + m_modules_list.back().GetGlobalAddress() + 
          m_modules_list.back().GetNumberOfLines(); 
        ParseOneModule(nextAddress);
    }
    //Print Symbol Table and Start Parse two
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
    TempModulePointer->PrintCurrentStatus();

    // We have now finished module
    m_modules_list.push_back(*TempModulePointer);
    ReadUntilCharacter();
    delete TempModulePointer;
}

int Linker::ExtractNumber() {
    string number;
    char c;
    ReadUntilCharacter();

    while (!stream.eof())
    {
        c = StreamGet();
        if ((c != ' ') && (c != '\t') && (c != '\n'))
            number += c; 
        else
            // Need to do checks to make sure number is appropriate
            //return std::stoi(number);
            return atoi(number.c_str());
    }
}

//Extracts Symbol Name
string Linker::ExtractSymbolName() {
    string SymbolName;
    char c;
    ReadUntilCharacter();

    while (!stream.eof())
    {
        c = StreamGet();
        if ((c != ' ') && (c != '\t') && (c != '\n'))
        {    
            SymbolName += c; 
        }
        else
        {
            return SymbolName;
        }
    }
}

char Linker::ExtractOpType(){
    char type;
    ReadUntilCharacter();
    type = StreamGet();
    if ((type != 'I') || (type != 'A') || (type != 'R') || (type != 'E'))
    {
        //Error with Type Extraction
    }
    return type;
}

// Stream points after read first symbol name in DefList
void Linker::ParseOneDefList(Module *ModPointer) {
    string symbol_name;
    int relative_address;
    int symbol_absolute_address; 

    ReadUntilCharacter();
    int count = ExtractNumber();

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
    int count = ExtractNumber();
    // For the amount of defcount
    for (int i = 0; i < count; i++)
    {
        symbol_name = ExtractSymbolName();
    }  
        
        //Need to add these for parse 2, not parse 1
        //Symbol temp_symbol (symbol_name);
        //ModPointer->AddToUseList(temp_symbol);
    
}

void Linker::ParseOneOperationList(Module *ModPointer) {
    int codecount;
    char type;
    int instruction;

    ReadUntilCharacter();
    codecount = ExtractNumber();
    ModPointer->SetNumberOfLines(codecount);
    for (int i = 0; i < codecount; i++)
    {
        type = ExtractOpType();
        instruction = ExtractNumber();
    } 
}

void Linker::ReadUntilCharacter(){
    char c;
    while(!stream.eof())
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

