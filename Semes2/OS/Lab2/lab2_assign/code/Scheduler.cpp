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

    //Create Processes
    CreateProcesses();

    // Run Scheduler Events
    StartAnalyze();
    
    // Print end of game summary 
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
        cout << "Created a Process!!!" << endl;

        // Create Event
        Event e (at, "CREATED", process_counter);
        put_event(e);
        process_counter += 1;
        ReadUntilCharacter();
    }
}

void Scheduler::put_event(Event e)
{
    event_queue.push_back(e);
}

Event Scheduler::get_event()
{
    Event e = event_queue[0];
    event_queue.erase(event_queue.begin());
    return e;
}

void Scheduler::put_ready_process(Process p)
{
    p.dynamic_priority = p.static_priority - 1;
    ready_queue.push_back(p);
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

void Scheduler::StartAnalyze() {
    // Check Event by Event
    //
   
    int total_values = event_queue.size();
    int current_counter = 0;
    // Only ends once the event queue is empty 
    while (!event_queue.empty())
    {
        if (current_counter > event_queue.size())
        {
            // This is where we need 
            // to check if thigns are running,
            // update states, and move things from running and not 
            // running 
            IncrementTime();
        }

        Event e = get_event();
        if (e.targetstate == "CREATED")
        {
            // Time to Create to Run
            if (all_processes[e.process_effected].AT == current_time)
            {
                Event e2 (current_time, "READY", e.process_effected);
                put_event(e2);
                if (verbose)
                {
                    int current_state_time = current_time - e.timestamp; 
                    cout << current_time << " " << e.process_effected << " " << 
                       current_state_time << ": CREATED -> READY" << endl; 
                }
                // Place this process in the ready_queue
                all_processes[e.process_effected].State = "READY";
                Process p (all_processes[e.process_effected]);
                put_ready_process(p);

                total_values = event_queue.size();
                current_counter = 0;
            }
            else
            {
                put_event(e);
                current_counter = current_counter + 1;
            }
        }

        if (e.targetstate == "READY")
        {
            // Time that it is ready
            if (e.timestamp == current_time)
            {
                // Place this process in the ready_queue
                all_processes[e.process_effected].State = "READY";
                Process p (all_processes[e.process_effected]);
                put_ready_process(p);

                total_values = event_queue.size();
                current_counter = 0;
            }
            else
            {
                put_event(e);
                current_counter = current_counter + 1;
            }
        }

        if (e.targetstate == "BLOCKED")
        {
            if (e.timestamp == current_time)
            {
                // Change process state to BLOCKED
                all_processes[e.process_effected].State = "BLOCKED";

                // Create a new ready event at time 
                int next_ib = MrRandom->myrandom(all_processes[e.process_effected].IO);
                all_processes[e.process_effected].current_ib = next_ib;
                int next_ready_time = current_time + all_processes[e.process_effected].current_ib;
                 
                Event e2 (next_ready_time, "READY", e.process_effected);
                put_event(e2);

                if (verbose)
                {
                    int current_state_time = next_ready_time - current_time; 
                    cout << next_ready_time << " " << e.process_effected << " " << 
                       current_state_time << ": BLOCK -> READY" << endl; 
                }

                total_values = event_queue.size();
                current_counter = 0;
            }
            else
            {
                put_event(e);
                current_counter = current_counter + 1;
            }
        }

        if (e.targetstate == "RUNNING")
        {
            if (e.timestamp == current_time)
            {
                // Change process state to Running
                all_processes[e.process_effected].State = "RUNNING"; 
                all_processes[e.process_effected].current_cb = 
                    MrRandom->myrandom(all_processes[e.process_effected].CB);
                Process p = all_processes[e.process_effected];
                // Find out if we go to Blocked with expired cb 
                // 1. Remaining Time
                // 2. cb -> Block
                // 3. quantum expiration 

                // Termination
                if ((p.remaining_time <= p.current_cb) && (p.remaining_time <= quantum))
                {
                    Event e2 (current_time + p.remaining_time, "DONE", p.id);
                    put_event(e2);
                }

                // Goes to block
                else if(p.current_cb <= quantum)
                {
                    all_processes[e.process_effected].current_ib = MrRandom->myrandom(p.IO);
                    all_processes[e.process_effected].remaining_time -= p.current_cb;
                    all_processes[e.process_effected].current_cb -= p.current_cb;
                    Event e2 (current_time + p.current_cb, "BLOCKED", p.id);
                    put_event(e2);

                    if (verbose)
                    {
                        cout << current_time + p.current_cb << " " << e.process_effected << 
                            " " << p.current_cb << ": RUNNG -> BLOCK" << "  ib=" <<  
                            all_processes[e.process_effected].current_ib <<
                            " rem= " << all_processes[e.process_effected].remaining_time <<
                            endl; 
                    } 

                }

                // Goes to ready with cb adjusted
                else
                {
                    // Change Priority
                    // Adjust cb
                    // Place in Ready
                    all_processes[e.process_effected].current_cb -= quantum;
                    all_processes[e.process_effected].dynamic_priority -= 1;

                    if (all_processes[e.process_effected].dynamic_priority == -1)
                    {
                        all_processes[e.process_effected].dynamic_priority = 
                            all_processes[e.process_effected].static_priority - 1;
                    }
                    Event e2 (current_time + quantum, "READY", p.id); 

                    if (verbose)
                    {
                        cout << current_time + quantum << " " << e.process_effected << 
                            " " << quantum << ": RUNNG -> READY" << "  cb=" <<  
                            all_processes[e.process_effected].current_cb <<
                            " rem= " << all_processes[e.process_effected].remaining_time <<
                            " prio= " << all_processes[e.process_effected].dynamic_priority
                            << endl; 
                    } 
                }

                total_values = event_queue.size();
                current_counter = 0;   
            }
            
            else
            {
                put_event(e);
                current_counter = current_counter + 1;
            }
        }

        if (e.targetstate == "DONE")
        {
            if (e.timestamp == current_time)
            {
                if (verbose)
                {
                    cout << current_time << " " << e.process_effected << 
                        "  ?: Done" << endl;
                }

                total_values = event_queue.size();
                current_counter = 0;
            }

            else
            {
                put_event(e);
                current_counter = current_counter + 1;
            }
        }

        //Need to take care of Ready to Running
        bool any_process_running = false;
        int process_running = 0;
        for (int i = 0; i < all_processes.size(); i++)
        {
            if (all_processes[i].State == "RUNNING")
            {
                any_process_running = true;
                break;
            }
        }

        if (!any_process_running)
        {
            if (ready_queue.size() > 0)
            {
                Process p = get_ready_process();
                all_processes[p.id].State = "RUNNING";
                all_processes[p.id].current_cb = MrRandom->myrandom(p.CB);
                Event e2 (current_time, "RUNNING", p.id);

                if (verbose)
                {
                    // Can implement time placed in ready if needed
                    cout << current_time << " " << p.id << " ?" << 
                       ": READY -> RUNNG " << endl; 
                }
            }
        }
    }
}

void Scheduler::IncrementTime(){
    current_time = current_time + 1;
}

