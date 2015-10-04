#include <stdio.h>
#include <stdlib.h>

void setUpProblem(int n);
double * createInitialGrid(int n);
void setUp(double *a, int size);
int main_n;
void pass(double [main_n][main_n], int size);


int main ( int argc, char *argv[] )
{
    if (argc == 2) {
        main_n = atoi(argv[1]);
        main_n +=1;
    }
    else {
        main_n += 101;
    }
    
    double array[main_n][main_n];
    setUp(*array, main_n);
    for (int i = 0; i < 50; i++) {
        pass(array, main_n);
    }

    printf("After iterations, at [10][10] we have: %f\n", array[10][10]);
    printf("After iterations, at [0][20] we have: %f\n", array[0][20]);
}

void pass(double input[main_n][main_n], int size) {
    double orig[size][size];
    for (int x = 0; x < size; x++) {
        for (int y = 0; y < size; y++) {
            orig[x][y] = input[x][y];
        }
    }

    for (int x = 1; x < size; x++) {
        for (int y = 1; y < size; y++) {
            input[x][y] = (orig[x][y+1] + 
                    orig[x][y-1] +
                    orig[x+1][y] +
                    orig[x-1][y])/4.0;
        }
    }
}

void setUp(double *a, int size) {
    for (int x = 0; x < size; x++) {
        for (int y = 0; y < size; y++) {
            if (y == 0) {
                //a[y * size + x] = 80;
                a[x * size + y] = 80;
            }

            else if (y == size - 1) {
                //a[y * size + x] = 80;
                a[x * size + y] = 80;
            }

            else if (x == size - 1) {
                //a[y * size + x] = 80;
                a[x * size + y] = 80;
            }

            else if (x == 0) {
                if ((y >= 10) && (y <= 30)) {
                    a[x * size + y] = 150;
                }
                else {
                    a[x * size + y] = 80;
                }
            }
            else {
                a[x * size + y] = 0;
            }
        }
    }
}

double* createInitialGrid(int n) {
    int sizeOfGrid = n + 1;
    double *arr = (double *)malloc(sizeOfGrid *
            sizeOfGrid * sizeof(double));
    
    for (int i = 0; i < sizeOfGrid; i++) {
        for (int j = 0; j < sizeOfGrid; j++) {
            *(arr + i * sizeOfGrid + j) = 0;
        }
    }
    return arr;
}

/*
double* createInitialGrid(int n) {
    double arr[n];

    for (int i = 0; i < n; i++) {
        arr[i] = 0.0;
    }
    return arr;
}
*/

void setUpProblem(int n) {
}

