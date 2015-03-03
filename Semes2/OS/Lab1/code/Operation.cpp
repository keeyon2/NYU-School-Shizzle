#include "Operation.h"

Operation::Operation(){
    m_type = 'A';
    m_instruction = -1;
    m_absolute_address = -1;
}

Operation::Operation(char type, int instruction, int address){
    m_type = type;
    m_instruction = instruction;
    m_absolute_address = address;
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

void Operation::SetErrorMessage(string error_message){
    m_error_message = error_message;
}

string Operation::GetErrorMessage(){
    return m_error_message;
}

void Operation::PrintInfo(){
    std::cout << "Op Type: " << m_type << " Op Inst: " << m_instruction 
        << " Op Addr: " << m_absolute_address << std::endl;
}

