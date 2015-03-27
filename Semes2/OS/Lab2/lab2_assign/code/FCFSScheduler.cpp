#include "FCFSScheduler.h"

using std::string;

FCFSScheduler::FCFSScheduler(char* input_file, char* random_file, bool verb) : Scheduler(input_file, random_file, verb, std::numeric_limits<int>::max())
{
    scheduler_type = "FCFS";
}

Process FCFSScheduler::get_ready_process()
{
    Process p = ready_queue[0];
    ready_queue.erase(ready_queue.begin());
    return p;
}
