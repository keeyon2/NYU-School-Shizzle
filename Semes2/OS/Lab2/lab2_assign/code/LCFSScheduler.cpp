#include "LCFSScheduler.h"

using std::string;

LCFSScheduler::LCFSScheduler(char* input_file, char* random_file, bool verb) : Scheduler(input_file, random_file, verb, std::numeric_limits<int>::max())
{
    scheduler_type = "LCFS";
}

Process LCFSScheduler::get_ready_process()
{
    Process p = ready_queue.back();
    ready_queue.pop_back(); 
    return p;
}
