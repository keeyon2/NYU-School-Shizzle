#include "Operation.h"

Operation::Operation(){
    m_type = 'A';
    m_instruction = -1;
    m_absolute_address = -1;
}

Operation::Operation(char type, int instruction, int module_address){
    m_type = type;
    m_instruction = instruction;

    if (m_type == 'R')
       m_absolute_address = m_instruction + module_address; 
    else
       m_absolute_address = instruction;
}

void Operation::SetType(char type){
    m_type = type;
}

char Operation::GetType(){
    return m_type;
}

void Operation::SetInstruction(int instruction){
    m_instruction = instruction;
}

int Operation::GetInstruction(){
    return m_instruction;
}

void Operation::SetAbsoluteAddress(int address){
    m_absolute_address = address;
}

int Operation::GetAbsoluteAddress(){
    return m_absolute_address;
}

void Operation::PrintInfo(){
    std::cout << "Op Type: " << m_type << " Op Inst: " << m_instruction 
        << " Op Addr: " << m_absolute_address << std::endl;
}

