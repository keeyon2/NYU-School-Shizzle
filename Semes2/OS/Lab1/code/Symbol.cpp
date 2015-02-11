#include "Symbol.h"

//Symbol Constructor
Symbol::Symbol(std::string message, int address){
    m_address_message = message;
    m_address_int = address;
    m_error_message = "";
}

//Symbol Constructor
Symbol::Symbol(std::string address_message, int address, std::string error_message){
    m_address_message = address_message;
    m_address_int = address;
    m_error_message = error_message;
}

// Getters and Setters

// Address Message
void Symbol::SetAddressMessage(std::string message){
    m_address_message = message;
}

std::string Symbol::GetAddressMessage() {
    return m_address_message;
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

// Complete Message
std::string Symbol::GetCompleteMessage() {
    return m_address_message + m_error_message;
}
