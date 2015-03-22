#ifndef Process_H
#define Process_H

#include <iostream>
#include <string>
#include <stdlib.h>
#include <stdio.h>
#include <vector>

using std::cout;
using std::endl;
using std::string;
using std::ifstream;
using std::vector;

class Process
{
    public: 
        int AT; // Arival Time
        int TC; // Total CPU Time
        int CB; // CPU Burst
        int IO; // IO Burst
        int CW; // Time in Ready State
        string State;
        int static_priority;
        int dynamic_priority;
        int quantrum;

        Process(int at, int tc, int cb, int io, int static_prio);
};

#endif