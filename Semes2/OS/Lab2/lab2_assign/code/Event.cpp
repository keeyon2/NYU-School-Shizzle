#include "Event.h"

Event::Event(int time, string state, int process) {
    timestamp = time;
    targetstate = state;
    process_effected = process;
}

std::ostream& operator<<(std::ostream &strm, const Event &e)
{
    return strm << "Event Time: " << e.timestamp << "State: " << e.targetstate 
        << "Process: " << e.process_effected;
} 
