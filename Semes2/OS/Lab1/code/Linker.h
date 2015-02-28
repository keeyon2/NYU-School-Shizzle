#ifndef Linker_H
#define Linker_H

#include <string>
using std::string;

#include <vector>
using std::vector;

#include <iostream>
#include <fstream>
using std::ifstream;

#include <sstream>
#include <stdlib.h>
#include "Symbol.h"
#include "Module.h"

//using std::stoi;

class Linker
{
    private:
        vector<Symbol> m_entire_def_list;
        vector<Module> m_modules_list;
        string m_symbol_table;
        string m_memory_map;
        char* m_input_file_name;

    public:
        // Entry point with filename
        Linker(char* filename);

        void StartLinker(char* filename);

        void SetDefList(vector<Symbol> def_list);
        vector<Symbol> GetDefList();

        void SetModulesList(vector<Module> modules_list);
        vector<Module> GetModList();

        char* GetInputFileName();

        // Functionality
        void ParseModule(int global_address);

        bool IsNumber(char c);

        int ExtractNumber();
        string ExtractSymbolName();

        void ParseDefList(int count, Module *ModPointer);
        void ReadUntilCharacter();
};

#endif