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
        int m_times_used;        
        bool m_used;

    public:
        int m_relative_address;
        Symbol(std::string name, int address);
        Symbol(std::string name, int address, std::string error_message);
        Symbol(std::string name);

        void SetName(std::string name);
        std::string GetName();

        void SetErrorMessage(std::string message);
        std::string GetErrorMessage();

        void SetAddress(int address);
        int GetAddress();

        void SymbolUsed();
        int GetTimesUsed();

        std::string GetCompleteMessage();

        void PrintInfo();
        void PrintForTable();

        void SetUsed(bool used);
        bool GetUsed();
};

#endif
