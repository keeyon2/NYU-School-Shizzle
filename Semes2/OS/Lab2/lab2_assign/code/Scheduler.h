#ifndef Scheduler_H
#define Scheduler_H

#include <iostream>
#include <string>
#include <stdlib.h>
#include <stdio.h>
#include <vector>
#include "Event.h"
#include "Process.h"
#include "RandomNumberGrabber.h"

using std::cout;
using std::endl;
using std::string;
using std::ifstream;
using std::vector;

class Scheduler
{
    public:
        RandomNumberGrabber* MrRandom;
        vector<Event> event_queue;
        vector<Process> ready_queue;
        vector<Process> all_processes;
        ifstream stream;
        int current_time;
        bool verbose;

        Scheduler(char* input_file, char* random_file, bool verb);
        void put_event(Event e);
        Event get_event();
        void put_ready_process(Process p);
        virtual Process get_ready_process() = 0;
        
        void CreateProcesses();
        void ReadUntilCharacter();
        int ExtractNumber();
        bool PeekEnd();
        void StartAnalyze();
        void IncrementTime();
};

#endif
