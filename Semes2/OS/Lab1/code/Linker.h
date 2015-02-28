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
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

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
        void ParseOneSetUp();
        void ParseOneModule(int global_address);

        int ExtractNumber();
        string ExtractSymbolName();
        char ExtractOpType();

        void ParseOneDefList(int count, string first_symbol_name, Module *ModPointer);
        void ParseOneUseList(int count, string first_symbol_name, Module *ModPointer);
        void ParseOneOperationList(Module *ModPointer);

        void ReadUntilCharacter();
        void ReadUntilModule();
};

#endif
