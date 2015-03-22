#include "Process.h"

Process::Process(int at, int tc, int cb, int io, int static_prio)
{
    AT = at;
    TC = tc;
    CB = cb;
    IO = io;
    static_priority = static_prio;
}
