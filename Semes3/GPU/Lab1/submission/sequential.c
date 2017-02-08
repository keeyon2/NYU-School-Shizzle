#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define ITERATIONS 50

void setUp(float *a, long size);
long main_n;
void pass(float *input, long size);


int main ( int argc, char *argv[] )
{
    if (argc == 2) {
        main_n = atoi(argv[1]);
        main_n +=1;
    }
    else {
        main_n += 101;
    }
    
    //float array[main_n][main_n];
    //float (*array)[main_n] = malloc(main_n * main_n * sizeof(float));
    float *array = (float *)malloc(main_n * main_n * sizeof(float));
    setUp(array, main_n);
    int i;
    for (i = 0; i < ITERATIONS; i++) {
        pass(array, main_n);
    }

    // printf("10 10 is %f", array[10 * main_n + 10]);

}

void pass(float* input, long size) {
    float *orig = (float *)malloc(size * size * sizeof(float));
    long x; 
    long x2;
    long y; 
    long y2;

    /*
    for (x = 0; x < size; x++) {
        for (y = 0; y < size; y++) {
            orig[y * size + x] = input[y * size + x];
        }
    }
    */

    memcpy(orig, input, size * size * sizeof(float));

    float up;
    float down;
    float left;
    float right;
    for (x2 = 1; x2 < size; x2++) {
        for (y2 = 1; y2 < size; y2++) {
            up = orig[y2 * size + x2 - size];
            down = orig[y2 * size + x2 + size];
            left = orig[y2 * size + x2 - 1];
            right = orig[y2 * size + x2 + 1];

            input[y2 * size + x2] = (up + down + left + right)/4.0;
        }
    }
}

void setUp(float *a, long size) {
    long x; 
    long y;
    for (x = 0; x < size; x++) {
        for (y = 0; y < size; y++) {
            if (y == 0) {
                a[y * size + x] = 80;
            }

            else if (y == size - 1) {
                a[y * size + x] = 80;
            }

            else if (x == size - 1) {
                a[y * size + x] = 80;
            }

            else if (x == 0) {
                if ((y >= 10) && (y <= 30)) {
                    a[y * size + x] = 150;
                }
                else {
                    a[y * size + x] = 80;
                }
            }
            else {
                a[y * size + x] = 0;
            }
        }
    }
}
