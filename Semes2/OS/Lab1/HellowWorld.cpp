#include <iostream>
#include <fstream>

using namespace std;

ofstream myfile;

void write(string input)
{
    cout << input;
    myfile << input;

}
int main()
{
    myfile.open("output.txt");
    //myfile << "Write this to the file.\n";

    write("Hi how are you Sam?\n");
    write("Good, thanks for asking\n");
    myfile.close();
    return 0;
}

