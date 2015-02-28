#ifndef Symbol_H
#define Symbol_H

#include <string>
#include <iostream>

class Symbol
{
    private:
        std::string m_name;
        std::string m_error_message;
        int m_address_int;
        

    public:
        Symbol(std::string name, int address);
        Symbol(std::string name, int address, std::string error_message);
        Symbol(std::string name);

        void SetName(std::string name);
        std::string GetName();

        void SetErrorMessage(std::string message);
        std::string GetErrorMessage();


        void SetAddress(int address);
        int GetAddress();

        std::string GetCompleteMessage();

        void PrintInfo();
};

#endif
