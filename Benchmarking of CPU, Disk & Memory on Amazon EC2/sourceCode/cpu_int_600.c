/* Subject: CS 553: CLoud  Computing
   Author : Ashwin Chati
   CWID   : A20354704
   Program: CPU Benchmarking
*/ 

#include <stdio.h>
#include <stdlib.h>
#include<time.h>
#include<string.h>
#include<pthread.h>
#include <signal.h>

// Variable and function declaration

int i,j;
double c;
int no_of_threads=4;

double total_time;
pthread_t threads [4];
double array[4];
long total_operations;

void int_thread_create();
void *int_operations(void *input);

int main()
{
	printf("----------------------------------------------\n");
	printf(" CPU Benchmarking 600 seconds experiment for int\n");
	printf("----------------------------------------------\n");
	
	total_operations =0;
	array[0] = 0;
	array[1] = 0;
	array[2] = 0;
	array[3] = 0;

	int_thread_create();
}

// Thread creation for int operation

void int_thread_create()
{
	int param[4];
	param[0]=0;
	param[1]=1;
	param[2]=2;
	param[3]=3;
	double GIOPS;
	
	FILE *fptr;
	fptr=fopen("CPU_600sec_IOPS.txt","a+");	
	fprintf(fptr,"------------------------------------------------------------------\n");	
	fprintf(fptr,"\t\t CPU Benchmarking 600 seconds for IOPS\n");
	fprintf(fptr,"------------------------------------------------------------------\n");
	
	for(i=0;i<no_of_threads;i++)
	{
	
	pthread_create(&threads[i],NULL, int_operations, &param[i]);

	}

	clock_t start_time1, end_time1;
	clock_t start_time2, end_time2;
	
	start_time1=clock();
	start_time2=clock();

	while(1)
	{
			if (((double)(clock()-start_time1)/CLOCKS_PER_SEC) > 1)
			{
							
			total_operations = array[0]+array[1]+array[2]+array[3];
			//printf("%lf\n",total_operations);
			GIOPS = (double)total_operations / 1000000000;	
			printf("GIOPS = %lf \n",GIOPS);		
			fprintf(fptr,"GIOPS = %lf \n",GIOPS);			
			start_time1=clock();
				total_operations=0;
				array[0]=0;
				array[1]=0;
				array[2]=0;
				array[3]=0;
			}
			
			if (((double)(clock()-start_time2)/CLOCKS_PER_SEC) > 600)
			{
				for (j=0;j<4;j++)
				{
				pthread_kill(threads[j],1);	
				}	
			}

	}

	fclose(fptr);
}

// Integer operation starts

void *int_operations(void *input)
{
	int in = *(int *)input;	
	int addition;
	while(1)
	{
		addition = (100 + 10)+(100 + 10)+(100 + 10)+(100 + 10)+(100 + 10)+(100 + 10)+(100 + 10)+(100 + 10)+(100 + 10)+(100 + 10)+(100);
	
	array[in] = array[in] + 20;

	}
}
