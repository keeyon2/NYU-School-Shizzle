#ifndef Scheduler_H
#define Scheduler_H

#include <iostream>
#include <iomanip>
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
using std::setw;
using std::setfill;
using std::setprecision;
using std::fixed;

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
        int current_running_count;
        int current_io_count;
        int quantum;
        string scheduler_type;
        int one_proc_running;
        int one_proc_blocked;

        Scheduler(char* input_file, char* random_file, bool verb, int quant);
        void put_event_old(Event e);
        void put_event_new(Event e);
        Event get_event();
        bool Event_Head_Visited();
        void Mark_All_Events_Not_Visited();
        virtual void put_ready_process(Process p);
        virtual Process get_ready_process() = 0;
        
        void CreateProcesses();
        void ReadUntilCharacter();
        int ExtractNumber();
        bool PeekEnd();
        void StartAnalyze();
        void IncrementTime();
        void InitializeProcess();
        void PrintEndSummary();
        void PrintEventQueue();
        void PrintProcessQueue();
        void PrintReadyQueue();

        void ChangeProcessState(int process_number, string state);
        bool AddRunning();
};

#endif
