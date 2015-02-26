#include "Linker.h"

Linker::Linker(string filename){
        StartLinker(filename);
}

void Linker::StartLinker(string filename){
    m_input_file_name = filename;
    //ifstream fin = new ifstream();
    ifstream fin;
    fin.open(filename); //Open File
    if (!fin.good())
        //return 1; // Exit if file not found
        std::cout << "Could not open File: " << filename << std::endl;

     //Parse Module
    ParseModule(fin, 0);

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

string Linker::GetInputFileName(){
    return m_input_file_name;
}

// Functionality

void Linker::ParseModule(ifstream stream, int global_address){
    char c;
    int count; 

    // Set up Module
    Module* TempModulePointer;
    TempModulePointer->SetGlobalAddress(global_address);

    //stream.peek(c);
    c = stream.peek();

    // This will be the defcount or usecount
    if (IsNumber(c))
    {

        count = ExtractNumber(stream);
        if (count > 16)
        {
            //return 1;  // Defcount or Usecount needs to be less that 16
        } 
         
        //stream.peek(c);
        c = stream.peek(); 
        if (IsNumber(c))
        {
            // We now know this is a deflist 
            ParseDefList(stream, count, TempModulePointer);
        }
        //ParseUseList(stream, count, TempModule); 
        // Print Module this far 

    }
    else
    {
        // return 1;  // This needs to be a number for defcount or usecount
    } 


}

// Determine if char is for a number
bool Linker::IsNumber(char c){
    if ( (c >= '0') && (c <= '9'))
        return true;

    return false;
}

int Linker::ExtractNumber(ifstream &stream) {
    string number;
    char c;

    while (true)
    {
        stream.get(c);
        if ((c != ' ') && (c != '\t') && (c != '\n'))
            number += c; 
        else
            // Need to do checks to make sure number is appropriate
            return stoi(number);
    }
}

//Extracts Symbol Name
string Linker::ExtractSymbolName(ifstream &stream) {
    string SymbolName;
    char c;
    bool found_first_char = false;
    ReadUntilCharacter(stream);

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

void Linker::ParseDefList(ifstream &stream, int count, Module *ModPointer) {
    string symbol_name;
    int relative_address;
    
    // For the amount of defcount
    for (int i = 0; i < count; i++)
    {
        symbol_name = ExtractSymbolName(stream);
        relative_address =  ExtractNumber(stream);
        //Symbol temp_symbol = new Symbol(symbol_name, relative_address);
        Symbol temp_symbol (symbol_name, relative_address);
        ModPointer->AddToDefList(temp_symbol);
        //delete temp_symbol;
    }
}

void Linker::ReadUntilCharacter(ifstream &stream){
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

