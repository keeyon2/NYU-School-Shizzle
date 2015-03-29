#ifndef SJFScheduler_H
#define SJFScheduler_H

#include "Scheduler.h"

class SJFScheduler : public Scheduler
{
    public:
        SJFScheduler(char* input_file, char* random_file, bool verb);
        Process get_ready_process();
};

#endif
