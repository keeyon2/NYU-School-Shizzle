#include <stdio.h>
#include <stdlib.h>
#include <cuda.h>
#include <cuda_profiler_api.h>

#define SIZE 1000
#define BLKS 4
#define THREADSPBLKS 256

__global__
void vecAddKernel(int * device)
{
  int i = threadIdx.x + (blockDim.x * blockIdx.x);
  
  
  
  device[i] = i;
  
}

void vecAdd()
{
  
  int inhost[100];
  int j;
  
  int * device;
  
  for( j = 0; j < 100; j++)
    inhost[j] = 0;
  
  for( j = 0; j < 100; j++)
    printf("inhost[%d] = %d\n", j, inhost[j]);
  
  printf("---------\n");
  
  cudaMalloc(&device, 100*sizeof(int));

  // kernel invocation
  vecAddKernel<<<10,10>>>(device);

  //transfer C_d from device to host
  cudaMemcpy(inhost, device, 400, cudaMemcpyDeviceToHost);
  
  cudaFree(device);
  
  for( j = 0; j < 100; j++)
    printf("inhost[%d] = %d\n", j, inhost[j]);

}


int main()
{
   vecAdd();
   
  
    return 0;
}
