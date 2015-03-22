#ifndef FCFSScheduler_H
#define FCFSScheduler_H

#include "Scheduler.h"

class FCFSScheduler : public Scheduler
{
    public:
        FCFSScheduler(char* input_file, char* random_file, bool verb);
        Process get_ready_process();
};

#endif
