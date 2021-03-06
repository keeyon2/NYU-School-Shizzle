#include "Module.h"

Module::Module(vector<Symbol> def_list, vector<Symbol> use_list,
                vector<Operation> operation_list, int global_address, 
                int number_of_lines){
    m_def_list = def_list;
    m_use_list = use_list;
    m_operation_list = operation_list;
    m_global_address = global_address;
    m_number_of_lines = number_of_lines;
}

Module::Module(int global_address){
    m_global_address = global_address;
    m_number_of_lines = 0;
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

void Module::SetOperationList(vector<Operation> operation_list){
    m_operation_list = operation_list;
}

vector<Operation> Module::GetOperationList(){
    return m_operation_list;
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

// Add to Functionality
void Module::AddToDefList(Symbol indv_symbol){
   m_def_list.push_back(indv_symbol); 
}

void Module::AddToUseList(Symbol indv_symbol){
    m_use_list.push_back(indv_symbol);
}

//Print current Module Status for debugging
void Module::PrintCurrentStatus(){
    // for (auto &symbol : m_def_list)
    // {
    //     symbol->PrintInfo();
    // }
    cout << "***********************************\n"; 
    cout << "************ DEF LIST *************\n";
    cout << "***********************************\n"; 

    for (std::vector<Symbol>::iterator it = m_def_list.begin(); it != m_def_list.end(); ++it)
    {
        it->PrintInfo();
    }

    cout << "***********************************\n"; 
    cout << "************ USE LIST *************\n";
    cout << "***********************************\n"; 

    for (std::vector<Symbol>::iterator it = m_use_list.begin(); it != m_use_list.end(); ++it)
    {
        it->PrintInfo();
    }
    
    cout << "***********************************\n"; 
    cout << "************ OP LIST **************\n";
    cout << "***********************************\n"; 

    for (std::vector<Operation>::iterator it = m_operation_list.begin(); it != m_operation_list.end(); ++it)
    {
        it->PrintInfo();
    }

    cout << "******** Global Address:  " << m_global_address << " *********\n";
    cout << "******** Total Lines:  " << m_number_of_lines << " *********\n";
    cout << "\n\n\n\n\n";
}
