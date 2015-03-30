#ifndef PRIOScheduler_H
#define PRIOScheduler_H

#include "Scheduler.h"

class PRIOScheduler : public Scheduler
{
    public:
        vector<Process> active_queue;
        vector<Process> expired_queue;
        PRIOScheduler(char* input_file, char* random_file, bool verb, int quantum);
        Process get_ready_process();
        void put_ready_process(Process p);
};

#endif
