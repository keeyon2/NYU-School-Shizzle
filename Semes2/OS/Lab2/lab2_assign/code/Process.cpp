#include "Process.h"

Process::Process(int at, int tc, int cb, int io, int static_prio, string state)
{
    AT = at;
    TC = tc;
    CB = cb;
    IO = io;
    static_priority = static_prio;
    State = state;
    dynamic_priority = static_prio - 1;
    current_cb = 0;
    current_ib = 0;
    remaining_time = 0;
}
