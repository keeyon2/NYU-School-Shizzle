#include "RRScheduler.h"

using std::string;

RRScheduler::RRScheduler(char* input_file, char* random_file, bool verb, int quantum) : Scheduler(input_file, random_file, verb, quantum)
{
    std::ostringstream s;
    s << "RR " << quantum;
    scheduler_type = s.str();
}

Process RRScheduler::get_ready_process()
{
    Process p = ready_queue[0];
    ready_queue.erase(ready_queue.begin());
    return p;
}
