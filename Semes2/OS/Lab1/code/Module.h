#ifndef Module_H
#define Module_H

#include <string> 
#include <vector>
#include "Symbol.h"

class Module
{
    private:
        std::vector<Symbol> m_def_list;
        std::vector<Symbol> m_use_list;
        int m_global_address;
        int m_number_of_lines;

    public:
        Module(std::vector<Symbol> def_list, std::vector<Symbol> use_list, 
                int global_address, int number_of_lines);
        
        void SetDefList(std::vector<Symbol> def_list);
        std::vector<Symbol> GetDefList();

        void SetUseList(std::vector<Symbol> use_list);
        std::vector<Symbol> GetUseList();

        void SetGlobalAddress(int address);
        int GetGlobalAddress();

        void SetNumberOfLines(int lines);
        int GetNumberOfLines();
};

#endif
