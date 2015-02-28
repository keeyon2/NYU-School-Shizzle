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
    ParseModule(0);

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

void Linker::ParseModule(int global_address){
    char c;
    int count; 
    string first_symbol_name;

    // Set up Module
    Module* TempModulePointer = new Module(global_address);
    TempModulePointer->SetGlobalAddress(global_address);
    c = stream.peek();
    if (isdigit(c))
    {
        count = ExtractNumber();
        first_symbol_name = ExtractSymbolName();
        if (count > 16)
        {
            //return 1;  // Defcount or Usecount needs to be less that 16
        } 
         
        c = stream.peek(); 
        if (isdigit(c))
        {
            // We now know this is a deflist 
            ParseDefList(count, first_symbol_name, TempModulePointer);

            // Set up Parse Use List
            count = ExtractNumber();
            first_symbol_name = ExtractSymbolName();
        }
        ParseUseList(count, first_symbol_name, TempModulePointer); 
        
        // Print Module this far 
        TempModulePointer->PrintCurrentStatus();
    }
    else
    {
        // return 1;  // This needs to be a number for defcount or usecount
    } 
    std::cout << "Concluded coding section\n";
    delete TempModulePointer;
}

int Linker::ExtractNumber() {
    string number;
    char c;
    ReadUntilCharacter();

    while (true)
    {
        stream.get(c);
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

    while (true)
    {
        stream.get(c);
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

// Stream points after read first symbol name in DefList
void Linker::ParseDefList(int count, string first_symbol_name, Module *ModPointer) {
    string symbol_name;
    int relative_address;
    
    // For the amount of defcount
    for (int i = 0; i < count; i++)
    {
        if (i == 0)
        {
            symbol_name = first_symbol_name;
        }
        else
        {
            symbol_name = ExtractSymbolName();
        }
        relative_address =  ExtractNumber();
        //Symbol temp_symbol = new Symbol(symbol_name, relative_address);
        Symbol temp_symbol (symbol_name, relative_address);
        ModPointer->AddToDefList(temp_symbol);
        //delete temp_symbol;
    }
}

// Stream points after read first symbol name in DefList
void Linker::ParseUseList(int count, string first_symbol_name, Module *ModPointer) {
    string symbol_name;
    int relative_address;
    
    // For the amount of defcount
    for (int i = 0; i < count; i++)
    {
        if (i == 0)
        {
            symbol_name = first_symbol_name;
        }
        else
        {
            symbol_name = ExtractSymbolName();
        }
        Symbol temp_symbol (symbol_name);
        ModPointer->AddToUseList(temp_symbol);
    }
}

void Linker::ReadUntilCharacter(){
    char c;
    while(true)
    {
        c = stream.peek();
        if ((c == ' ') || (c == '\t') || (c == '\n'))
        {
            stream.get(c);
        }
        else
            return;
    }
     
}

