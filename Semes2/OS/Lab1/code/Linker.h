#ifndef Linker_H
#define Linker_H

#include <string>
using std::string;
using std::to_string;

#include <vector>
using std::vector;

#include <iostream>
#include <iomanip>
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
        vector<Operation> m_operation_list;
        vector<string> m_pre_symb_warns;
        vector<string> m_post_mem_warns;
        string m_symbol_table;
        string m_memory_map;
        int m_total_instructions;

        char* m_input_file_name;
        int m_stream_line_number;
        int m_stream_offset_number;
        int m_stream_prev_line_last_offset;
        int m_stream_prev_line_last_offset_2;
        bool m_last_line_newline;
        bool m_last_line_and_prev_newline; 
        char StreamGet();
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

        void ParseOneDefList(Module *ModPointer);
        void ParseOneUseList(Module *ModPointer);
        void ParseOneOperationList(Module *ModPointer);

        void ParseTwoSetUp();
        void ParseTwoDefList(Module &Mod);
        void ParseTwoUseList(Module &Mod);
        void ParseTwoOperationList(Module &Mod);

        void ReadUntilCharacter();
        void PrintEOFParseError(int errcode);
        void PrintParseError(int errcode);

        int FindWhichModWithAddress(Module *mod);
        void StreamLastCharNewline();
        bool PeekEnd();
};

#endif
