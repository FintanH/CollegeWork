#include <stdlib.h>
#include <stdio.h>
#include <pthread.h> 

#define NUM_THREADS 4
#define numSteps 1000000
#define step (1.0/numSteps)
double pi = 0.0;
double micros = 0.0;
double millis = 0.0;
clock_t start, end;

void *CalculatePI(void *threadid) { 
  printf("Calculating for %d\n", threadid);
  double sum = 0.0; 
  int i;
  double x;
  for(i = threadid; i < numSteps; i += NUM_THREADS) { 
    x = (i+0.5)*step; 
    sum = sum + 4.0/(1.0+x*x); 
  } 
  pi += step * sum;
  printf("pi:%f\n", pi);
  pthread_exit(NULL); 
} 

int main() {
  start = clock();

  pthread_t threads[NUM_THREADS]; 
  int rc,t,sum; 
  for (t=0;t<NUM_THREADS;t++) { 
    printf("Creating thread %d\n",t); 
    rc = pthread_create(&threads[t],NULL,CalculatePI,(void *)t); 
    if (rc) { 
      printf("ERROR return code from pthread_create(): %d\n",rc); 
      exit(-1); 
    } 
  } 
  
  // wait for threads to exit 
  for(t=0;t<NUM_THREADS;t++) { 
    pthread_join( threads[t], NULL); 
  } 

  printf("pi ~= %f\n", pi);

  end = clock();

  micros = end - start;
  millis = micros / 1000;
  printf("Elapsed time: %fms\n", millis);
  return 0;
}