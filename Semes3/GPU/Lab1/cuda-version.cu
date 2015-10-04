#include <stdio.h>
#include <stdlib.h>
#include <cuda.h>
#include <cuda_profiler_api.h>

#define THREADSPBLK 1024
#define THREADSPSM 2048
#define TILE_WIDTH 16
#define TOTAL_ITERATIONS 50

int main_n;

__global__ void iterate(float* originalMatrixD, float* solutionD, int originalMatrixWidth, 
        int startingIndex) {
    __shared__ float originalMatrixDS [TILE_WIDTH][TILE_WIDTH];

    int tx = threadIdx.x;
    int ty = threadIdx.y;

    int blockId = blockIdx.x + blockIdx.y * gridDim.x;

    int currentMatrixIndex = blockId * (blockDim.x * blockDim.y) + 
        (threadIdx.y * blockDim.x) + threadIdx.x;

    currentMatrixIndex += startingIndex;

    originalMatrixDS[ty][tx] = originalMatrixD[currentMatrixIndex];

    // Sync up w/ shared data set up
    __syncthreads();

    float replaceAmount;
    bool onEdge = false;
    int XEdgeCheckMod = currentMatrixIndex % originalMatrixWidth;

    // X = 0 edge
    if ( XEdgeCheckMod == 0) {
        //onEdge = true;
        replaceAmount = 11.0;

    }

    // X = N - 1
    else if ( XEdgeCheckMod == (originalMatrixWidth - 1)) {
        //onEdge = true;
        replaceAmount = 22.0;
    }

    // Y = 0
    else if (currentMatrixIndex < originalMatrixWidth) {
        //onEdge = true;
        replaceAmount = 33.0;
    }

    // Y = N - 1
    else if (currentMatrixIndex >= (originalMatrixWidth * originalMatrixWidth 
                - originalMatrixWidth)) {
        //onEdge = true;
        replaceAmount = 44.0;
    }

    else {
        replaceAmount = 55.0;
    }

    /*
    if (onEdge) {
        //replaceAmount = originalMatrixDS[ty][tx];
        //replaceAmount = originalMatrixD[currentMatrixIndex];
        replaceAmount = 55.05;
    }

    else {
        //replaceAmount = (originalMatrixDS[ty+1][tx] + originalMatrixDS[ty-1][tx] +
            // originalMatrixDS[ty][tx+1] + originalMatrixDS[ty][tx-1])/4.0;
        replaceAmount = 88.0;
    }
    */

    solutionD[currentMatrixIndex] = replaceAmount;
    //solutionD[currentMatrixIndex] = originalMatrixD[currentMatrixIndex];
}

void setUp(float *a, int size) {
    for (int y = 0; y < size; y++) {
        for (int x = 0; x < size; x++) {
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

int main ( int argc, char *argv[] )
{
    if (argc == 2) {
        main_n = atoi(argv[1]);
        main_n +=1;
    }
    else {
        main_n += 101;
    }
    
    int elements = main_n * main_n;
    float originalMatrix[elements];
    float solution[elements];

    setUp(originalMatrix, main_n);
   
    // GPU TIME BABY!
    dim3 dimGrid(4, 2);
    dim3 dimBlock(TILE_WIDTH, TILE_WIDTH);
    
    float* solutionD;
    float* originalMatrixD;

    int memorySize = main_n * main_n * sizeof(float);
    
    cudaMalloc((void**) &originalMatrixD, memorySize);
    cudaMalloc((void**) &solutionD, memorySize);

    /*
    for (int i = 0; i < TOTAL_ITERATIONS; i++) {
        cudaMemcpy(originalMatrixD, originalMatrix, memorySize, cudaMemcpyHostToDevice);
     
        // INVOKE
        iterate<<<dimGrid, dimBlock>>>(originalMatrixD, solutionD, main_n);

        // Finish
        cudaMemcpy(solution, solutionD, memorySize, cudaMemcpyDeviceToHost);

        printf("After iteration solution value @ 0 20: %f\n", solution[20 * main_n + 0]);
        printf("After iteration solution value @ 30 30: %f\n", solution[30 * main_n + 30]);
        // copy to solution to OriginalMatrixD for iteration
        memcpy(originalMatrix, solution, memorySize);
    }
    */

    cudaMemcpy(originalMatrixD, originalMatrix, memorySize, cudaMemcpyHostToDevice);
     
    // INVOKE
    for (int i = 0; i < elements/THREADSPSM + 1; i++) {
        int startingIndex = i * THREADSPSM;
        iterate<<<dimGrid, dimBlock>>>(originalMatrixD, solutionD, main_n, startingIndex);
    }

    // Finish
    cudaMemcpy(solution, solutionD, memorySize, cudaMemcpyDeviceToHost);

    int counter = 0;
    for (int i = 0; i < main_n * main_n; i++) {
        if (i % main_n == 0) {
            printf("\n\n\n\n\n%d\n%f", counter++, solution[i]);
        }
        else {
            printf(" %f", solution[i]);
        }
    }

    // copy to solution to OriginalMatrixD for iteration
    //memcpy(originalMatrix, solution, memorySize);

    cudaFree(originalMatrixD);
    cudaFree(solutionD);

    int Y = 10;
    int X = 10;

    float printSolutionAtIndex = solution[Y * main_n + X];
    printf("After iterations, at [10][10] we have: %f\n", printSolutionAtIndex);
    //free(solution);
}



