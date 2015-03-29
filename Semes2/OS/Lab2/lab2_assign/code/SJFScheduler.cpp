#include "SJFScheduler.h"

using std::string;

SJFScheduler::SJFScheduler(char* input_file, char* random_file, bool verb) : Scheduler(input_file, random_file, verb, std::numeric_limits<int>::max())
{
    scheduler_type = "SJF";
}

Process SJFScheduler::get_ready_process()
{
    int min_remaining_time = std::numeric_limits<int>::max();
    int min_remaining_proc = std::numeric_limits<int>::max(); 

    for (int i = 0; i < ready_queue.size(); i++)
    {
        if (ready_queue[i].remaining_time < min_remaining_time)
        {
            min_remaining_time = ready_queue[i].remaining_time;
            min_remaining_proc = i;
        }
    }

    Process p = ready_queue[min_remaining_proc];
    ready_queue.erase(ready_queue.begin() + min_remaining_proc);
    return p;
}
