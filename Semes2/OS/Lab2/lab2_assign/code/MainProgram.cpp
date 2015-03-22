#include <iostream>
#include <fstream>
#include <string>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

// Include .h 's with " "
#include "RandomNumberGrabber.h"
#include "Event.h"
#include "FCFSScheduler.h"


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

    input_file_name = argv[argc - 2];
    random_file_name = argv[argc - 1];
    char* char_random_file_name = argv[argc - 1]; 
    char* char_input_file_name = argv[argc - 2];
    RandomNumberGrabber* RandomNumberObject = new RandomNumberGrabber(char_random_file_name);
    //FCFSScheduler* Scheduler = new FCFSScheduler(char_input_file_name, char_random_file_name);
    FCFSScheduler Scheduler (char_input_file_name, char_random_file_name, verbose);



    return 0;
}

