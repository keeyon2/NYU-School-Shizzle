#include "Event.h"

Event::Event(int time, string state, int process) {
    timestamp = time;
    targetstate = state;
    process_effected = process;
}
