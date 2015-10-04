#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main ( int argc, char *argv[] )
{
    long main_n;
    if (argc == 2) {
        main_n = atoi(argv[1]);
    }
    else {
        main_n = 100;
    }

    float **array2 = (float **)malloc(12 * sizeof(float));
    long total_size = 1000000000 * 1000000000;
    for (long i = 0; i < total_size/main_n; i++) {
        array2[i] = (float *)malloc(main_n * sizeof(float));
    }

    /*
    for (int i = 0; i < main_n * main_n; i++)
    {
        array[i] = i;
    }
    */
    printf("OKEY dokey%.6f\n", array2[100000 * main_n + 5]);
    free(array2);
}
