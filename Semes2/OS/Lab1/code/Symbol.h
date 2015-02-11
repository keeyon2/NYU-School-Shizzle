#ifndef Symbol_H
#define Symbol_H

#include <string>

class Symbol
{
    private:
        std::string m_address_message;
        std::string m_error_message;
        int m_address_int;
        

    public:
        Symbol(std::string message, int address);
        Symbol(std::string address_message, int address, std::string error_message);

        void SetAddressMessage(std::string message);
        std::string GetAddressMessage();

        void SetErrorMessage(std::string message);
        std::string GetErrorMessage();

        std::string GetCompleteMessage();

        void SetAddress(int address);
        int GetAddress();
};

#endif
