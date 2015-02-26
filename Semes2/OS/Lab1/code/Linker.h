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
#include "Symbol.h"
#include "Module.h"
#include <stdlib.h>

using std::stoi;

class Linker
{
    private:
        vector<Symbol> m_entire_def_list;
        vector<Module> m_modules_list;
        string m_symbol_table;
        string m_memory_map;
        string m_input_file_name;

    public:
        // Entry point with filename
        Linker(string filename);

        void StartLinker(string filename);

        void SetDefList(vector<Symbol> def_list);
        vector<Symbol> GetDefList();

        void SetModulesList(vector<Module> modules_list);
        vector<Module> GetModList();

        string GetInputFileName();

        // Functionality
        void ParseModule(ifstream stream, int global_address);

        bool IsNumber(char c);

        int ExtractNumber(ifstream &stream);
        string ExtractSymbolName(ifstream &stream);

        void ParseDefList(ifstream &stream, int count, Module *ModPointer);
        void ReadUntilCharacter(ifstream &stream);
};

#endif
