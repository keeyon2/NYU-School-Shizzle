#ifndef Module_H
#define Module_H


#include <string> 
#include <vector>
using std::vector;

#include <fstream>
using std::ifstream;

#include "Symbol.h"

class Module
{
    private:
        vector<Symbol> m_def_list;
        vector<Symbol> m_use_list;
        int m_global_address;
        int m_number_of_lines;

    public:
        Module(vector<Symbol> def_list, vector<Symbol> use_list, 
                int global_address, int number_of_lines);
       
        Module(int global_address); 

        void SetDefList(vector<Symbol> def_list);
        vector<Symbol> GetDefList();

        void SetUseList(vector<Symbol> use_list);
        vector<Symbol> GetUseList();

        void SetGlobalAddress(int address);
        int GetGlobalAddress();

        void SetNumberOfLines(int lines);
        int GetNumberOfLines();

        void AddToDefList(Symbol indv_symbol);
        void AddToUseList(Symbol indv_symbol);
        
        void PrintCurrentStatus();
};

#endif
