#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

// Include .h 's with " "
#include "RandomNumberGrabber.h"

#include "Event.h"
#include "FCFSScheduler.h"
#include "LCFSScheduler.h"
#include "SJFScheduler.h"
#include "RRScheduler.h"

using std::cout;
using std::endl;
using std::string;
using std::ofstream;

ofstream random_file;
ofstream input_file;

int main(int argc, char **argv)
{
    char *svalue = NULL;
    bool verbose = false;
    int index;
    int c;
    int count = 0;
    opterr = 0;
    string input_file_name;
    string random_file_name; 

    // Gather which scheduler
    while ((c = getopt (argc, argv, "vs:")) != -1)
    {
       count = count + 1;
       switch (c)
         {
         case 'v':
           verbose = true;
           break;
         case 's':
           svalue = optarg;
           break;
         case '?':
           if (optopt == 's')
           {
             cout << "Option -" << optopt << " requires and argument"<< endl;
             abort ();
           }
           else
           {
             cout << "Unknown option character -" << optopt << endl;
             abort ();
           }
           return 1;
         default:
           abort ();
         }
    }

    string string_svalue(svalue);
    input_file_name = argv[argc - 2];
    random_file_name = argv[argc - 1];
    char* char_random_file_name = argv[argc - 1]; 
    char* char_input_file_name = argv[argc - 2];
    RandomNumberGrabber* RandomNumberObject = new RandomNumberGrabber(char_random_file_name);
    //FCFSScheduler* Scheduler = new FCFSScheduler(char_input_file_name, char_random_file_name);
    // First come First Serve
    if (strcmp (svalue, "F") == 0)
    {
        FCFSScheduler Scheduler (char_input_file_name, char_random_file_name, verbose);
        Scheduler.InitializeProcess();
    }

    // Last come first serve
    else if(strcmp (svalue, "L") == 0)
    {
        LCFSScheduler Scheduler (char_input_file_name, char_random_file_name, verbose);
        Scheduler.InitializeProcess(); 
    }

    //Shortest Job First
    else if(strcmp (svalue, "S") == 0)
    {
        SJFScheduler Scheduler (char_input_file_name, char_random_file_name, verbose);
        Scheduler.InitializeProcess(); 
    }

    // Round Robin
    else if(string_svalue[0] == 'R')
    {
        // Delete first letter
        string_svalue.erase(string_svalue.begin());
        int quantum_number = atoi(string_svalue.c_str());
        
        RRScheduler Scheduler (char_input_file_name, char_random_file_name, verbose, quantum_number);
        Scheduler.InitializeProcess();
    }
   
    // Priority Queue 
    else if(string_svalue[0] == 'P')
    {
        // Delete first letter
        string_svalue.erase(string_svalue.begin());
        int quantum_number = atoi(string_svalue.c_str());
    }
    return 0;
}
