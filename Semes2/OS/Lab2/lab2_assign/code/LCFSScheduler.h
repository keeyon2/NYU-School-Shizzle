#ifndef LCFSScheduler_H
#define LCFSScheduler_H

#include "Scheduler.h"

class LCFSScheduler : public Scheduler
{
    public:
        LCFSScheduler(char* input_file, char* random_file, bool verb);
        Process get_ready_process();
};

#endif
