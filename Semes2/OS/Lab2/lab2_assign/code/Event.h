#ifndef Event_H
#define Event_H

#include <iostream>
#include <string>
#include <stdlib.h>
#include <stdio.h>
#include <vector>

using std::cout;
using std::endl;
using std::string;
using std::ifstream;
using std::vector;

class Event
{
    public:
        int timestamp;
        string targetstate;
        int process_effected;
        Event(int time, string state, int process);
        friend std::ostream& operator <<(std::ostream&, const Event&);
        bool visited;
};

#endif
