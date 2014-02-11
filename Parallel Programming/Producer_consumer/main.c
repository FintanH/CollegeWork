#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <pthread.h>
#include <math.h>

#define MAX_BUFFER 16
#define PROCESS_LENGTH 100
#define NUM_CONSUMERS 10
#define RATE_OF_PRODUCTION 100000
#define RATE_OF_CONSUMPTION 100000000

pthread_cond_t  cond = PTHREAD_COND_INITIALIZER;
pthread_mutex_t mutex;

typedef struct queue
{
	int max_size;
	int cur_size;
} queue;

queue *process_queue;

queue *init_queue()
{
	queue *new_queue;
	new_queue = malloc(sizeof(queue));
	new_queue->max_size = MAX_BUFFER;
	new_queue->cur_size = 0;
	return new_queue;
}

int is_empty(queue *q)
{
	if(q->cur_size == 0)
	{
		return 1;
	}else{
		return 0;
	}
}

int is_full(queue *q)
{
	if(q->cur_size == q->max_size)
	{
		return 1;
	}else{
		return 0;
	}
}

int enqueue(queue *q)
{
	if(!(is_full(q)))
	{
		q->cur_size++;
		return 1;
	}else{
		printf("Full Buffer\n");
		return 0;
	}
}

int dequeue(queue *q)
{
	if(!(is_empty(q)))
	{
		q->cur_size--;
		return 1;
	}else{
		printf("Empty Buffer\n");
		return 0;
	}
}

void print_queue(queue *q)
{
	printf("current size: %d\nmax size: %d\n\n",q->cur_size,q->max_size);
}

void produce(queue *q)
{
	int r = rand()%RATE_OF_PRODUCTION;
	while(r)
	{
		r--;
	}

	enqueue(q);
	pthread_cond_signal(&cond);
}

void consume(queue *q)
{
	int rc;
	int r = rand()%RATE_OF_CONSUMPTION;
	while(r)
	{
		r--;
	}

	dequeue(q);
	pthread_cond_signal(&cond);
}

void *begin_consumer(void *t)
{
	int i,rc;
	printf("Consumer %d began\n", t);
	struct timespec start,end;

	//clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &start);
	for(i=0; i<(PROCESS_LENGTH/NUM_CONSUMERS); i++)
	{
		pthread_mutex_lock(&mutex);

		/*if(is_empty(process_queue))
		{
			pthread_cond_signal(&cond);
		}*/

		while(is_empty(process_queue))
		{
			pthread_cond_wait(&cond,&mutex);
		}

		printf("Consumer %d consuming...\n", t);
		consume(process_queue);
		print_queue(process_queue);
		pthread_mutex_unlock(&mutex);
	}
	/*clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &end);
	double time_sec = (double)(end.tv_sec-start.tv_sec);
	double time_nsec = (double)(end.tv_nsec-start.tv_nsec);
	double time_result = (double)(time_sec+(time_nsec/1E9));
	printf("Time for consumer %d is %fs\n", t, time_result);*/
}

void *begin_producer()
{
	int i,rc;
	printf("Producer began\n");
	for(i=0; i<PROCESS_LENGTH; i++)
	{
		pthread_mutex_lock(&mutex);

		/*if(is_full(process_queue))
		{
			pthread_cond_signal(&cond);
		}*/

		while(is_full(process_queue))
		{
			pthread_cond_wait(&cond,&mutex);
		}

		printf("producing...\n");
		produce(process_queue);
		print_queue(process_queue);
		pthread_mutex_unlock(&mutex);
	}
}

int main()
{
	pthread_t consumers[NUM_CONSUMERS];
	pthread_t producer;
	int rc,t,i;
	srand(time(NULL));

	process_queue = init_queue();

	printf("Initial queue:\n");
	print_queue(process_queue);

	pthread_mutex_init(&mutex, NULL);
	
	rc = pthread_create(&producer,NULL,begin_producer,NULL); 
    if (rc) { 
      printf("ERROR return code from pthread_create(): %d\n",rc); 
      exit(-1); 
    }

    for(t=0; t<NUM_CONSUMERS; t++)
    {
    	rc = pthread_create(&consumers[t],NULL,begin_consumer,(void *)t); 
	    if (rc) { 
	      printf("ERROR return code from pthread_create(): %d\n",rc); 
	      exit(-1);
	    }
    }

	pthread_join(producer, NULL);
    for(t=0;t<NUM_CONSUMERS;t++) { 
    	pthread_join(consumers[t], NULL); 
  	} 

	return 0;
}