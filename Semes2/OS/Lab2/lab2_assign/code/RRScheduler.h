#ifndef RRScheduler_H
#define RRScheduler_H

#include "Scheduler.h"

class RRScheduler : public Scheduler
{
    public:
        RRScheduler(char* input_file, char* random_file, bool verb, int quantum);
        Process get_ready_process();
};

#endif
