#include "Module.h"

Module::Module(std::vector<Symbol> def_list, std::vector<Symbol> use_list,
                int global_address, int number_of_lines){
    m_def_list = def_list;
    m_use_list = use_list;
    m_global_address = global_address;
    m_number_of_lines = number_of_lines;
}

// Getters and Setters
void Module::SetDefList(std::vector<Symbol> def_list){
    m_def_list = def_lift;
}

std::vector<Symbol> Module::GetDefList(){
    return m_def_list;
}

void Module::SetUseList(std::vector<Symbol> use_list){
    m_use_list = use_lift;
}

std::vector<Symbol> Module::GetUseList(){
    return m_use_list;
}

void SetGlobalAddress(int address){
    m_global_address = address;
}

int GetGlobalAddress(){
    return m_global_address;
}

void SetNumberOfLines(int lines){
    m_number_of_lines = lines;
}

int GetNumberOfLines(){
    return m_number_of_lines;
}
