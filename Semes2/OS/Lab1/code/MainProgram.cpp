 #include <iostream>
 #include <fstream>
#include "Symbol.h"
#include "Module.h"
#include "Linker.h"

using std::cout;
using std::string;
using std::ofstream;

 ofstream myfile;

void write(string input)
{
    cout << input;
    myfile << input;
}

int main(int argc, char *argv[])
{
    // Input file will be argv[1]
    // myfile.open("output.txt");
    // myfile << "Write this to the file.\n";

    // write("Hi how are you Sam?\n");
    // write("Good, thanks for asking\n");
    //Symbol car ("Address Message.Yea", 12);
    //write(car.GetAddressMessage() + "\n");
    //myfile.close();

    if (argc < 1)
        cout << "Please Run Again with argument file";
    else
        Linker MainLinker (argv[1]);
    return 0;
}

