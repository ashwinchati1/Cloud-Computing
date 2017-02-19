/* Subject: CS 553: CLoud  Computing
   Author : Ashwin Chati
   CWID   : A20354704
   Program: CPU Benchmarking
*/ 

#include<stdio.h>
#include<pthread.h>
#include<time.h>

//Variable and function declaration

pthread_t threads [4];

void *float_operations();
void *int_operations();
void int_thread_create();
void float_thread_create();

//Main function

int main()
{

	printf("----------------------------------------------\n");
	printf("\t\t CPU Benchmarking\n");
	printf("----------------------------------------------\n");

	
	float_thread_create();
	
	int_thread_create();

}

// Thread creation for floating point operation

void float_thread_create()
{
	FILE *fp;
	fp=fopen("CPU_Benchmarking.txt", "a+");
	int i;
	int no_of_threads=1;
	double total_time;

	//int no_of_threads = threads;
	for (no_of_threads=1;no_of_threads<5;no_of_threads=no_of_threads*2)
	{
	printf("\nProgram Starts for float operations with thread %d \n",no_of_threads);
	clock_t	start_time = clock();

		
		for(i=0;i<no_of_threads;i++)
		{
	
		pthread_create(&threads[i],NULL, float_operations, NULL);

		}

		for (i = 0; i < no_of_threads; i++) 
		{
	  	  pthread_join(threads[i], NULL);  
		}
	
	clock_t	end_time = clock();
	
	total_time = (double)(end_time - start_time)/CLOCKS_PER_SEC;

	fprintf(fp,"------------------------------------------------------------------\n");	
	fprintf(fp,"\t\t CPU Benchmarking for GFLOPS\n");
	fprintf(fp,"------------------------------------------------------------------\n");

	fprintf(fp,"\nProgram Starts for int operations for %d threads\n",no_of_threads);
	printf("Total time taken by CPU is %f seconds\n",total_time);
		
	fprintf(fp,"Total time taken by CPU is %f seconds\n",total_time);
	
	double FLOPS = no_of_threads*20*(double)(1000000000/total_time);

	//printf("FLOPS = %lf operations/second\n",FLOPS);

	double GFLOPS = (double) FLOPS / 1000000000;

	printf("GFLOPS = %lf\n",GFLOPS);
	fprintf(fp,"GFLOPS = %lf\n",GFLOPS);
	
}
fclose(fp);
}

// Thread creation for int operation

void int_thread_create()
{
	FILE *fp;
	fp=fopen("CPU_Benchmarking.txt", "a+");
	int i;
	int no_of_threads;
	double total_time;

	for (no_of_threads=1;no_of_threads<5;no_of_threads=no_of_threads*2)
	{	
	printf("\nProgram Starts for int operations for %d threads\n",no_of_threads);	
	clock_t start_time = clock();

	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&threads[i],NULL, int_operations, NULL);
	}

	for (i = 0; i < no_of_threads; i++) 
	{
	    pthread_join(threads[i], NULL);  
	}

	clock_t end_time = clock();
	
	total_time = (double)(end_time - start_time)/CLOCKS_PER_SEC;
	fprintf(fp,"------------------------------------------------------------------\n");	
	fprintf(fp,"\t\t CPU Benchmarking for IPOS\n");
	fprintf(fp,"------------------------------------------------------------------\n");
	fprintf(fp,"\nProgram Starts for int operations for %d threads\n",no_of_threads);
	printf("Total time taken by CPU is %f seconds\n",total_time);

	fprintf(fp,"Total time taken by CPU is %f seconds\n",total_time);

	double IOPS = no_of_threads*20*(double)(1000000000/total_time);

	//printf("IOPS = %lf operations/second\n",IOPS);

	double GIOPS = (double) IOPS / 1000000000;

	printf("GIOPS = %lf\n",GIOPS);
	fprintf(fp,"GIOPS = %lf\n",GIOPS);
}
fclose(fp);
}

// Perform floating operation

void *float_operations()
{	long addition;
	int i;
	for (i=0;i<1000000000;i++)
	{
		addition = (100.0 + 10.0)+(100.0 + 10.0)+(100.0 + 10.0)+(100.0 + 10.0)+(100.0 + 10.0)+(100.0 + 10.0)+(100.0 + 10.0)+(100.0 + 10.0)+(100.0 + 10.0)+(100.0 + 10.0)+(i);
	}
	pthread_exit(NULL);
}

// Perform integer operation

void *int_operations()
{
	int addition;
	int i;
	for (i=0;i<1000000000;i++)
	{
		addition = (100 + 10)+(100 + 10)+(100 + 10)+(100 + 10)+(100 + 10)+(100 + 10)+(100 + 3)+(100 + 10)+(100 + 3)+(100 + 10)+(100);
	}
	pthread_exit(NULL);
}

