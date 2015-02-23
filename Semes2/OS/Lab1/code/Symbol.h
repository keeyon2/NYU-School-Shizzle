#ifndef Symbol_H
#define Symbol_H

#include <string>

class Symbol
{
    private:
        std::string m_name;
        std::string m_error_message;
        int m_address_int;
        

    public:
        Symbol(std::string name, int address);
        Symbol(std::string name, int address, std::string error_message);

        void SetName(std::string name);
        std::string GetName();

        void SetErrorMessage(std::string message);
        std::string GetErrorMessage();

        std::string GetCompleteMessage();

        void SetAddress(int address);
        int GetAddress();
};

#endif
