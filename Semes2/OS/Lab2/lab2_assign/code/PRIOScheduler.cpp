#include "PRIOScheduler.h"

using std::string;

PRIOScheduler::PRIOScheduler(char* input_file, char* random_file, bool verb, int quantum) : Scheduler(input_file, random_file, verb, quantum)
{
    std::ostringstream s;
    s << "PRIO " << quantum;
    scheduler_type = s.str();
}

Process PRIOScheduler::get_ready_process()
{
    // Udate active Queue/Expired Queue
    int id = 0;
    for (int a = 0; a < active_queue.size(); a++)
    {
        id = active_queue[a].id;
        if (all_processes[id].dynamic_priority < 0)
        {
            all_processes[id].dynamic_priority = 
                all_processes[id].static_priority - 1;

            expired_queue.push_back(all_processes[id]);
            active_queue.erase(active_queue.begin() + a); 
        }
    }
    
    //Check if empty
    if (active_queue.empty())
    {
        active_queue = expired_queue;
        expired_queue.clear();
    }

    // Grab from active queue
    int max_priority = std::numeric_limits<int>::min();
    int max_priority_proc = std::numeric_limits<int>::min(); 
    for (int i = 0; i < active_queue.size(); i++)
    {
        id = active_queue[i].id; 
        if (all_processes[id].dynamic_priority > max_priority)
        {
            max_priority = all_processes[id].dynamic_priority;
            max_priority_proc = i;
        }

    }
    
    // Update Active Queue
    id = active_queue[max_priority_proc].id;
    Process p = all_processes[id];
    active_queue.erase(active_queue.begin() + max_priority_proc);

    // Update Ready Queue
    for (int b = 0; b < ready_queue.size(); b++)
    {
        if (ready_queue[b].id == id)
        {
            ready_queue.erase(ready_queue.begin() + b);
        }
    }

    return p;
}

void PRIOScheduler::put_ready_process(Process p)
{
    active_queue.push_back(all_processes[p.id]);
    ready_queue.push_back(all_processes[p.id]);
}
