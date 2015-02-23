#include "Module.h"

Module::Module(vector<Symbol> def_list, vector<Symbol> use_list,
                int global_address, int number_of_lines){
    m_def_list = def_list;
    m_use_list = use_list;
    m_global_address = global_address;
    m_number_of_lines = number_of_lines;

}

// Getters and Setters
void Module::SetDefList(vector<Symbol> def_list){
    m_def_list = def_list;
}

vector<Symbol> Module::GetDefList(){
    return m_def_list;
}

void Module::SetUseList(vector<Symbol> use_list){
    m_use_list = use_list;
}

vector<Symbol> Module::GetUseList(){
    return m_use_list;
}

void Module::SetGlobalAddress(int address){
    m_global_address = address;
}

int Module::GetGlobalAddress(){
    return m_global_address;
}

void Module::SetNumberOfLines(int lines){
    m_number_of_lines = lines;
}

int Module::GetNumberOfLines(){
    return m_number_of_lines;
}

void Module::AddToDefList(Symbol indv_symbol){
   m_def_list.push_back(indv_symbol); 
}

void Module::AddToUseList(Symbol indv_symbol){
    m_use_list.push_back(indv_symbol);
}

