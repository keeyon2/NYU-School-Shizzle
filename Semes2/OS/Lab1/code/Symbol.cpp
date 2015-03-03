#include "Symbol.h"

//Symbol Constructor
Symbol::Symbol(std::string name, int address){
    m_name = name;
    m_address_int = address;
    m_error_message = "";
    m_times_used = 0;
    m_used = false;
}

//Symbol Constructor
Symbol::Symbol(std::string name){
    m_name = name;
    m_address_int = -1;
    m_error_message = "";
    m_times_used = 0;
    m_used = false;
}

//Symbol Constructor
Symbol::Symbol(std::string name, int address, std::string error_message){
    m_name = name;
    m_address_int = address;
    m_error_message = error_message;
    m_times_used = 0;
    m_used = false;
}

// Getters and Setters

// Address Message
void Symbol::SetName(std::string name){
    m_name = name;
}

std::string Symbol::GetName() {
    return m_name;
}

// Error Message
void Symbol::SetErrorMessage(std::string message){
    m_error_message = message;
}

std::string Symbol::GetErrorMessage() {
    return m_error_message;
}

// Int Address
void Symbol::SetAddress(int address){
    m_address_int = address;
}

int Symbol::GetAddress(){
    return m_address_int;
}

void Symbol::SymbolUsed(){
    m_times_used += 1;
}

int Symbol::GetTimesUsed(){
    return m_times_used;
}

// Complete Message
std::string Symbol::GetCompleteMessage() {
    return m_name + m_error_message;
}

void Symbol::PrintInfo(){
    std::cout << "Symbol Name: " << m_name << " Symbol Address: " << m_address_int << "\n";
}

void Symbol::PrintForTable(){
    std::cout << m_name << "=" << m_address_int << m_error_message << "\n";
}

void Symbol::SetUsed(bool used){
    m_used = used;
}

bool Symbol::GetUsed(){
    return m_used;
}
