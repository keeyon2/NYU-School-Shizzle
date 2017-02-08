#include <stdio.h>
#include <stdlib.h>
#include <cuda.h>
#include <cuda_profiler_api.h>

#define THREADSPBLK 1024
#define THREADSPSM 2048
#define TILE_WIDTH 32
#define TOTAL_ITERATIONS 50

int main_n;

__global__ void iterate(float* originalMatrixD, float* solutionD, int originalMatrixWidth, 
        int startingIndex) {
    // __shared__ float originalMatrixDS [TILE_WIDTH][TILE_WIDTH];
    __shared__ float originalMatrixDS [TILE_WIDTH * TILE_WIDTH];

    int tx = threadIdx.x;
    int ty = threadIdx.y;

    int blockId = blockIdx.x + blockIdx.y * gridDim.x;

    int currentMatrixIndex = blockId * (blockDim.x * blockDim.y) + 
        (threadIdx.y * blockDim.x) + threadIdx.x;

    currentMatrixIndex += startingIndex;

    originalMatrixDS[ty * TILE_WIDTH + tx] = originalMatrixD[currentMatrixIndex];

    // Sync up w/ shared data set up
    __syncthreads();

    float replaceAmount;
    bool onEdge = false;
    int XEdgeCheckMod = currentMatrixIndex % originalMatrixWidth;

    // X = 0 edge
    if ( XEdgeCheckMod == 0) {
        onEdge = true;
    }

    // X = N - 1
    else if ( XEdgeCheckMod == (originalMatrixWidth - 1)) {
        onEdge = true;
    }

    // Y = 0
    else if (currentMatrixIndex < originalMatrixWidth) {
        onEdge = true;
    }

    // Y = N - 1
    else if (currentMatrixIndex >= (originalMatrixWidth * originalMatrixWidth 
                - originalMatrixWidth)) {
        onEdge = true;
    }

    if (onEdge) {
        replaceAmount = originalMatrixDS[ty * TILE_WIDTH + tx];
    }

    else {
        // Top and Bottom come from Global memory
        float top = originalMatrixD[currentMatrixIndex - originalMatrixWidth];
        float bottom = originalMatrixD[currentMatrixIndex + originalMatrixWidth];
        float left;
        float right;

        // Left and right edge come from Global memory
        if (tx == 0 && ty == 0) {
            left = originalMatrixD[currentMatrixIndex - 1];
        }

        else {
            left = originalMatrixDS[ty * TILE_WIDTH + tx - 1];
        }

        if ((ty == TILE_WIDTH - 1) && (tx == TILE_WIDTH - 1)) {
            right = originalMatrixD[currentMatrixIndex + 1];
        }

        else {
            right = originalMatrixDS[ty * TILE_WIDTH + tx + 1];
        }

        replaceAmount = (left + right + top + bottom) / 4;
    }

    solutionD[currentMatrixIndex] = replaceAmount;
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
    float *originalMatrix = (float *)malloc(elements * sizeof(float));
    float *solution = (float *)malloc(elements * sizeof(float));
    //float originalMatrix[elements];
    //float solution[elements];

    setUp(originalMatrix, main_n);
   
    // GPU TIME BABY!
    dim3 dimGrid(2, 1);
    dim3 dimBlock(TILE_WIDTH, TILE_WIDTH);
    
    float* solutionD;
    float* originalMatrixD;

    int memorySize = main_n * main_n * sizeof(float);
    
    cudaMalloc((void**) &originalMatrixD, memorySize);
    cudaMalloc((void**) &solutionD, memorySize);

    //cudaMemcpy(originalMatrixD, originalMatrix, memorySize, cudaMemcpyHostToDevice);
     
    // INVOKE
    // for (int iter = 0; iter < TOTAL_ITERATIONS; iter++) {
    for (int iter = 0; iter < 50; iter++) {
        for (int i = 0; i < elements/THREADSPSM + 1; i++) {
            int startingIndex = i * THREADSPSM;
            cudaMemcpy(originalMatrixD, originalMatrix, memorySize, cudaMemcpyHostToDevice);
            iterate<<<dimGrid, dimBlock>>>(originalMatrixD, solutionD, main_n, startingIndex);
            cudaMemcpy(originalMatrix, solutionD, memorySize, cudaMemcpyDeviceToHost);
        }
    }

    // Finish
    cudaMemcpy(solution, solutionD, memorySize, cudaMemcpyDeviceToHost);

    // PRINTS
    /*
    int counter = 0;
    for (int i = 0; i < main_n * main_n; i++) {
        if (i % main_n == 0) {
            printf("\n\n\n\n\n%d\n%f", counter++, solution[i]);
        }
        else {
            printf(" %f", solution[i]);
        }
    }
    */

    cudaFree(originalMatrixD);
    cudaFree(solutionD);
    free(originalMatrix);
    free(solution);
}
