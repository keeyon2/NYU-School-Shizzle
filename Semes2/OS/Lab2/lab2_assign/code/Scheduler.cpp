#include "Scheduler.h"

Scheduler::Scheduler(char* input_file, char* random_file, bool verb, int quant){
    // Whatever
    MrRandom = new RandomNumberGrabber(random_file);
    stream.open(input_file);
    verbose = verb;
    quantum = quant;
    current_time = 0;
    current_running_count = 0;
    current_io_count = 0;
    one_proc_running = 0;
    one_proc_blocked = 0;
    //t();
}

void Scheduler::CreateProcesses()
{
    int at, tc, bc, io, static_prio;
    int process_counter = 0;
    while(!PeekEnd())
    {
        // Create Process
        at = ExtractNumber();
        tc = ExtractNumber();
        bc = ExtractNumber();
        io = ExtractNumber();
        static_prio = MrRandom->myrandom(4);
        Process p (at, tc, bc, io, static_prio, "CREATED", process_counter); 
        p.remaining_time = tc;
        p.State = "CREATED";
        all_processes.push_back(p); 

        // Create Event
        Event e (at, "CREATED", process_counter);
        put_event_new(e);
        process_counter += 1;
        ReadUntilCharacter();
    }
}

void Scheduler::put_event_old(Event e)
{
    e.visited = true;
    event_queue.push_back(e);
}

void Scheduler::put_event_new(Event e)
{
    event_queue.push_back(e);
    Mark_All_Events_Not_Visited();
}

Event Scheduler::get_event()
{
    Event e = event_queue[0];
    event_queue.erase(event_queue.begin());
    return e;
}

bool Scheduler::Event_Head_Visited()
{
    if (event_queue.size() > 0)
        return event_queue[0].visited;
    return true;
}

void Scheduler::put_ready_process(Process p)
{
    all_processes[p.id].dynamic_priority = 
        all_processes[p.id].static_priority - 1;
    ready_queue.push_back(all_processes[p.id]);
}

int Scheduler::ExtractNumber() {
    string number;
    char c;
    ReadUntilCharacter();

    // If we have readed End of the file Expect Number
    if (PeekEnd())
    {
        //Error
    }

    while (!PeekEnd())
    {
        stream.get(c);
        if ((c != ' ') && (c != '\t') && (c != '\n'))
        {
            if (!isdigit(c))
            {
                cout << "SHOULD BE DIGIT" << endl;
            }

            number += c;
        }
        else
            break;
    }
    return atoi(number.c_str());
}

void Scheduler::ReadUntilCharacter(){
    char c;
    while(!PeekEnd())
    {
        c = stream.peek();
        if ((c == ' ') || (c == '\t') || (c == '\n'))
        {
            stream.get(c);
        }
        else
            return;
    }
    if (PeekEnd())
    {
        //Reset to the start of the file
        return;
    }
}

bool Scheduler::PeekEnd() {
    return (stream.peek(), stream.eof());
}

void Scheduler::Mark_All_Events_Not_Visited()
{
    for (int i = 0; i < event_queue.size(); i++)
    {
        event_queue[i].visited = false;
    }
}

void Scheduler::StartAnalyze() {
    // Check Event by Event
    //
   
    // Only ends once the event queue is empty 
    while (true)
    {
        bool event_Q_empty = event_queue.empty();
        bool ready_Q_empty = ready_queue.empty();
        if (event_Q_empty && ready_Q_empty)
        {
            break;
        }

        if (Event_Head_Visited())
        {
            //Need to take care of Ready to Running
            if (AddRunning()) 
            {
                Mark_All_Events_Not_Visited();
                continue;
            }
            IncrementTime();
            Mark_All_Events_Not_Visited(); 
        }

        Event e = get_event();
        if (e.targetstate == "CREATED")
        {
            // Time to Create to Run
            if (all_processes[e.process_effected].AT == current_time)
            {
                // Place this process in the ready_queue
                ChangeProcessState(e.process_effected, "READY");
                Process p (all_processes[e.process_effected]);
                put_ready_process(p);

                if (verbose)
                {
                    int current_state_time = current_time - e.timestamp; 
                    cout << current_time << " " << e.process_effected << " " << 
                       current_state_time << ": CREATED -> READY" << endl; 
                }
            }
            else
            {
                put_event_old(e);
            }
        }

        if (e.targetstate == "READY")
        {
            if (e.timestamp == current_time)
            {
                // Take care of verbose
                if (verbose)
                {
                    int correct_time = current_time - 
                            all_processes[e.process_effected].current_inst_time;

                    if (all_processes[e.process_effected].State == "RUNNING")
                    {
                        // Place this process in the ready_queue
                        ChangeProcessState(e.process_effected, "READY");
                        Process p (all_processes[e.process_effected]);
                        put_ready_process(p);
                        cout << current_time << " " << e.process_effected << 
                            " " << correct_time << ": RUNNG -> READY" << "  cb=" <<  
                            all_processes[e.process_effected].current_cb <<
                            " rem=" << all_processes[e.process_effected].remaining_time <<
                            " prio=" << all_processes[e.process_effected].dynamic_priority
                            << endl; 

                        //Now place Priority Decreaser
                        all_processes[e.process_effected].dynamic_priority -= 1;
                        // if (all_processes[e.process_effected].dynamic_priority == -1)
                        // {
                        //     all_processes[e.process_effected].dynamic_priority = 
                        //         all_processes[e.process_effected].static_priority - 1;
                        // }
                    }

                    else if(all_processes[e.process_effected].State == "BLOCKED")
                    {
                        // Place this process in the ready_queue
                        ChangeProcessState(e.process_effected, "READY");

                        // Reset Priority
                        all_processes[e.process_effected].dynamic_priority =
                            all_processes[e.process_effected].static_priority - 1;

                        Process p (all_processes[e.process_effected]);
                        put_ready_process(p);

                        cout << current_time << " " << e.process_effected << " " <<
                            correct_time << ": BLOCK -> READY" << endl;
                    }

                    // This is if the previous state was Created
                    else
                    {
                        // Place this process in the ready_queue
                        ChangeProcessState(e.process_effected, "READY");
                        Process p (all_processes[e.process_effected]);
                        put_ready_process(p);

                    }
                } 
                
                // If we are not in verbose
                else
                {
                    if (all_processes[e.process_effected].State == "RUNNING")
                    {
                        // Place this process in the ready_queue
                        ChangeProcessState(e.process_effected, "READY");
                        Process p (all_processes[e.process_effected]);
                        put_ready_process(p);
                        all_processes[e.process_effected].dynamic_priority -= 1;
                    }

                    else if(all_processes[e.process_effected].State == "BLOCKED")
                    {
                        // Place this process in the ready_queue
                        ChangeProcessState(e.process_effected, "READY");

                        // Reset Priority
                        all_processes[e.process_effected].dynamic_priority =
                            all_processes[e.process_effected].static_priority - 1;

                        Process p (all_processes[e.process_effected]);
                        put_ready_process(p);
                    }

                    // In Created
                    else
                    {
                        // Place this process in the ready_queue
                        ChangeProcessState(e.process_effected, "READY");
                        Process p (all_processes[e.process_effected]);
                        put_ready_process(p);
                    }
                    // Place this process in the ready_queue
                    // ChangeProcessState(e.process_effected, "READY");
                    // Process p (all_processes[e.process_effected]);
                    // put_ready_process(p);
                } 
            }
            else
            {
                put_event_old(e);
            }
        }

        if (e.targetstate == "BLOCKED")
        {
            if (e.timestamp == current_time)
            {
                // Change process state to BLOCKED
                if (verbose)
                {
                    if (all_processes[e.process_effected].State == "RUNNING")
                    {
                        cout << current_time << " " << e.process_effected << " "
                            << current_time - 
                            all_processes[e.process_effected].current_inst_time << 
                            ": RUNNG -> BLOCK" << " ib=" <<
                           all_processes[e.process_effected].current_ib << " rem="
                          << all_processes[e.process_effected].remaining_time
                         << endl; 
                    }
                }
                ChangeProcessState(e.process_effected, "BLOCKED");

                // Create a new ready event at time 
                int next_ready_time = current_time + all_processes[e.process_effected].current_ib;
                 
                Event e2 (next_ready_time, "READY", e.process_effected);
                put_event_new(e2);
            }
            else
            {
                put_event_old(e);
            }
        }

        if (e.targetstate == "RUNNING")
        {
            if (e.timestamp == current_time)
            {
                // Change process state to Running
                //all_processes[e.process_effected].State = "RUNNING"; 
                if (all_processes[e.process_effected].current_cb == 0)
                {
                    all_processes[e.process_effected].current_cb = 
                        MrRandom->myrandom(all_processes[e.process_effected].CB);
                }

                // else
                // {
                //     all_processes[e.process_effected].dynamic_priority -= 1;
                //     if (all_processes[e.process_effected].dynamic_priority == -1)
                //     {
                //         all_processes[e.process_effected].dynamic_priority = 
                //             all_processes[e.process_effected].static_priority - 1;
                //     }
                // }

                Process p = all_processes[e.process_effected];
                
                if (verbose)
                {
                    // Can implement time placed in ready if needed
                    cout << current_time << " " << p.id << " " << 
                        current_time - all_processes[e.process_effected].current_inst_time 
                       << ": READY -> RUNNG cd=" << all_processes[p.id].current_cb << 
                       " rem=" << all_processes[p.id].remaining_time << " prio=" << 
                       all_processes[p.id].dynamic_priority << endl;
                }

                ChangeProcessState(e.process_effected, "RUNNING");
                // Find out if we go to Blocked with expired cb 
                // 1. Remaining Time
                // 2. cb -> Block
                // 3. quantum expiration 

                // Termination
                if ((p.remaining_time <= p.current_cb) && (p.remaining_time <= quantum))
                {
                    Event e2 (current_time + p.remaining_time, "DONE", p.id);
                    put_event_new(e2);
                }

                // Goes to block
                else if(p.current_cb <= quantum)
                {
                    all_processes[e.process_effected].current_ib = MrRandom->myrandom(p.IO);
                    all_processes[e.process_effected].remaining_time -= p.current_cb;
                    all_processes[e.process_effected].current_cb -= p.current_cb;
                    Event e2 (current_time + p.current_cb, "BLOCKED", p.id);
                    put_event_new(e2);
                }

                // Goes to ready with cb adjusted
                else
                {
                    // Change Priority
                    // Adjust cb
                    // Place in Ready
                    all_processes[e.process_effected].remaining_time -= quantum;
                    all_processes[e.process_effected].current_cb -= quantum;
                    // all_processes[e.process_effected].dynamic_priority -= 1;

                    Event e2 (current_time + quantum, "READY", p.id); 
                    put_event_new(e2);
                }
            }
            
            else
            {
                put_event_old(e);
            }
        }

        if (e.targetstate == "DONE")
        {
            if (e.timestamp == current_time)
            {

                //all_processes[e.process_effected].State = "DONE";
                ChangeProcessState(e.process_effected, "DONE");
                all_processes[e.process_effected].FT = current_time;

                if (verbose)
                {
                    cout << current_time << " " << e.process_effected << 
                        " " << current_time - all_processes[e.process_effected].current_inst_time
                       << ": Done" << endl; 
                }
                Mark_All_Events_Not_Visited();
            }

            else
            {
                put_event_old(e);
            }
        }
    }
}

void Scheduler::IncrementTime(){
    bool one_blocked = false, one_running = false;
    for (int i = 0; i < all_processes.size(); i++)
    {
        if (all_processes[i].State == "READY")
        {
           all_processes[i].CW = all_processes[i].CW + 1; 
        }
        else if (all_processes[i].State == "BLOCKED")
        {
            all_processes[i].IT = all_processes[i].IT + 1;
            one_blocked = true;
        }
        else if(all_processes[i].State == "RUNNING")
        {
            one_running = true;
        }
    }

    if (one_running)
    {
        one_proc_running += 1;
    }

    if (one_blocked)
    {
        one_proc_blocked += 1;
    } 

    current_time = current_time + 1;
}

void Scheduler::InitializeProcess()
{
    //Create Processes
    CreateProcesses();

    // Run Scheduler Events
    StartAnalyze();
    
    // Print end of game summary 
    PrintEndSummary();
}

void Scheduler::PrintEndSummary()
{

    cout << scheduler_type << endl;
    int total_finishing_time = 0; 
    double average_turnaround = 0.0, average_cpuwaiting = 0.0, total_proc = 0.0;
    total_proc = (double) all_processes.size();
    // Print all Processes Info
    for (int i = 0; i < all_processes.size(); i++)
    {
        int TT = all_processes[i].FT - all_processes[i].AT;
        cout << setw(4) << setfill('0') << all_processes[i].id << ":";
        cout << setw(5) << setfill(' ') << all_processes[i].AT;
        cout << setw(5) << setfill(' ') << all_processes[i].TC;
        cout << setw(5) << setfill(' ') << all_processes[i].CB;
        cout << setw(5) << setfill(' ') << all_processes[i].IO;
        cout << setw(2) << setfill(' ') << all_processes[i].static_priority;
        cout << " |";
        cout << setw(6) << setfill(' ') << all_processes[i].FT;
        cout << setw(6) << setfill(' ') << TT;
        cout << setw(6) << setfill(' ') << all_processes[i].IT;
        cout << setw(6) << setfill(' ') << all_processes[i].CW << endl;

        if (all_processes[i].FT > total_finishing_time)
        {
            total_finishing_time = all_processes[i].FT;
        }

        average_turnaround += (double) TT;
        average_cpuwaiting += (double) all_processes[i].CW;
    }

   average_turnaround = average_turnaround / total_proc; 
   average_cpuwaiting = average_cpuwaiting / total_proc;

    // Print Total Summary
    double CPU_util = 0, IO_util = 0;
    double total_time = (double) total_finishing_time;
    double proc_running = (double) one_proc_running;
    double proc_blocked = (double) one_proc_blocked;
    double throughput = total_proc / (total_time / 100.0);

    CPU_util = (proc_running / total_time) * 100.0;
    IO_util = (proc_blocked / total_time) * 100.0;
    cout << "SUM: " << total_finishing_time << " ";  
    cout << fixed;
    cout << setprecision(2) << CPU_util << " ";
    cout << setprecision(2) << IO_util << " ";
    cout << setprecision(2) << average_turnaround << " "; 
    cout << setprecision(2) << average_cpuwaiting << " ";
    cout << setprecision(3) << throughput;
    // Need to find Average turnaround and average cpu waiting
    cout << endl;
}

void Scheduler::PrintEventQueue()
{
    for (int i = 0; i < event_queue.size(); i++)
    {
        Event e(event_queue[i]);
        cout << "------------------------" << endl;
        cout << "Event: " << i << endl;
        cout << "Event Time: " << e.timestamp << endl;
        cout << "Event State: " << e.targetstate << endl;
        cout << "Event Process: " << e.process_effected<< endl;
        cout << "Event Visited: " << e.visited << endl;
        cout << "------------------------" << endl;
    }
}

void Scheduler::PrintProcessQueue()
{
    for (int i = 0; i < all_processes.size(); i++)
    {
        Process p(all_processes[i]);
        cout << "------------------------" << endl;
        cout << "Process: " << i << endl;
        cout << "AT: " << p.AT << endl;
        cout << "TC: " << p.TC << endl;
        cout << "CB: " << p.CB << endl;
        cout << "IO: " << p.IO << endl;
        cout << "State: " << p.State << endl;
        cout << "id: " << p.id << endl;
        cout << "static_priority: " << p.static_priority << endl;
        cout << "dynamic_priority: " << p.dynamic_priority << endl;
        cout << "current_cb: " << p.current_cb << endl;
        cout << "current_ib: " << p.current_ib << endl;
        cout << "remaning_time: " << p.remaining_time << endl;
        cout << "------------------------" << endl;

    }
} 

void Scheduler::PrintReadyQueue()
{
    for (int i = 0; i < ready_queue.size(); i++)
    {
        Process p(ready_queue[i]);
        cout << "------------------------" << endl;
        cout << "Process: " << i << endl;
        cout << "AT: " << p.AT << endl;
        cout << "TC: " << p.TC << endl;
        cout << "CB: " << p.CB << endl;
        cout << "IO: " << p.IO << endl;
        cout << "State: " << p.State << endl;
        cout << "id: " << p.id << endl;
        cout << "static_priority: " << p.static_priority << endl;
        cout << "dynamic_priority: " << p.dynamic_priority << endl;
        cout << "current_cb: " << p.current_cb << endl;
        cout << "current_ib: " << p.current_ib << endl;
        cout << "remaning_time: " << p.remaining_time << endl;
        cout << "------------------------" << endl;
    }
}

void Scheduler::ChangeProcessState(int process_number, string state)
{
    all_processes[process_number].State = state;
    all_processes[process_number].current_inst_time = current_time;
}

bool Scheduler::AddRunning()
{
    bool any_process_running = false, any_event_running = false;
    int process_running = 0;
    for (int i = 0; i < all_processes.size(); i++)
    {
        if (all_processes[i].State == "RUNNING")
        {
            any_process_running = true;
            break;
        }
    }

    for (int j = 0; j < event_queue.size(); j++)
    {
        if (event_queue[j].targetstate == "RUNNING")
        {
            any_event_running = true;
            break;
        }
    }

    if (!any_process_running && !any_event_running)
    {

        if (ready_queue.size() > 0)
        {
            Process p = get_ready_process();
            Event e2 (current_time, "RUNNING", p.id);
            put_event_new(e2);

            return true;
        }
    }
    return false;

}
