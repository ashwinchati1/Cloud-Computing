/* Subject: CS 553: CLoud  Computing
   Author : Ashwin Chati
   CWID   : A20354704
   Program: Disk Benchmarking
*/ 

#include<stdio.h>
#include<string.h>
#include<pthread.h>
#include<time.h>
#include <stdlib.h>

// Variable Declaration

int no_of_threads,i,instruction;
double total_time_sec, total_time_ms,latency,latency_per_operation,Throughput;
pthread_t threads [4];
char buffer[1024*1024];
FILE *fptr;
clock_t start_time,end_time;

// Function Declaration

void *sequential_write(void *byte);
void *random_write(void *byte);
void *sequential_read(void *byte);
void *random_read(void *byte);
int one_byte_seq_write();
int one_kb_seq_write();
int one_mb_seq_write();
int one_byte_ran_write();
int one_kb_ran_write();
int one_mb_ran_write();
int one_byte_seq_read();
int one_kb_seq_read();
int one_mb_seq_read();
int one_byte_ran_read();
int one_kb_ran_read();
int one_mb_ran_read();
int calculate_instruction(int byte_size);
void thread_create_sequential_write(int byte);
void thread_create_random_write(int byte);
void thread_create_sequential_read(int byte);
void thread_create_random_read(int byte);
void calculate(clock_t s_time,clock_t e_time, int thr,int bt);

//Main Function

int main()
{
printf("------------------------------------------------------\n");
printf("\t\t Disk Benchmarking\n");
printf("------------------------------------------------------\n");

// inserting data into buffer

for (i=0;i<(1024*1024);i++)
{
buffer[i]='C';
}

one_byte_seq_write();
one_kb_seq_write();
one_mb_seq_write();
one_byte_ran_write();
one_kb_ran_write();
one_mb_ran_write();
one_byte_seq_read();
one_kb_seq_read();
one_mb_seq_read();
one_byte_ran_read();
one_kb_ran_read();
one_mb_ran_read();
}

int one_byte_seq_write()
{
	thread_create_sequential_write(1);
	return 0;
}

int one_kb_seq_write()
{
	thread_create_sequential_write(1024);
	return 0;
}

int one_mb_seq_write()
{
	thread_create_sequential_write(1048576);
	return 0;
}

int one_byte_ran_write()
{
	thread_create_random_write(1);
	return 0;
}

int one_kb_ran_write()
{
	thread_create_random_write(1024);
	return 0;
}

int one_mb_ran_write()
{
	thread_create_random_write(1048576);
	return 0;
}

int one_byte_seq_read()
{
	thread_create_sequential_read(1);
	return 0;
}

int one_kb_seq_read()
{
	thread_create_sequential_read(1024);
	return 0;
}

int one_mb_seq_read()
{
	thread_create_sequential_read(1048576);
	return 0;
}

int one_byte_ran_read()
{
	thread_create_random_read(1);
	return 0;
}

int one_kb_ran_read()
{
	thread_create_random_read(1024);
	return 0;
}

int one_mb_ran_read()
{
	thread_create_random_read(1048576);
	return 0;
}

// Create thread for sequential write operation

void thread_create_sequential_write(int byte)
{
	int byte_size = byte;
	clock_t start_time;
	
	for (no_of_threads=1;no_of_threads<3;no_of_threads++)
	{	
	printf("Program Starts for Sequential Write operations for %d threads\n",no_of_threads);
	start_time = clock();
	
	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&threads[i],NULL, sequential_write, &byte_size);
	}
	for (i = 0; i < no_of_threads; i++)  //Joining threads
	{
	    pthread_join(threads[i], NULL);  
	}
	clock_t end_time = clock();
	calculate(start_time,end_time,no_of_threads,byte_size);
	}
}

// Create thread for random write operation

void thread_create_random_write(int byte)
{
	int byte_size = byte;
	clock_t start_time;
	
	for (no_of_threads=1;no_of_threads<3;no_of_threads++)
	{	
	printf("Program Starts for Random Write operations for %d threads\n",no_of_threads);
	start_time = clock();
	
	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&threads[i],NULL, random_write, &byte_size);
	}
	for (i = 0; i < no_of_threads; i++) 
	{
	    pthread_join(threads[i], NULL);  
	}
	clock_t end_time = clock();
	calculate(start_time,end_time,no_of_threads,byte_size);
	}
}

// Create thread for sequential read operation

void thread_create_sequential_read(int byte)
{
	int byte_size = byte;
	clock_t start_time;
	for (no_of_threads=1;no_of_threads<3;no_of_threads++)
	{	
	printf("Program Starts for Sequential Read operations for %d threads\n",no_of_threads);
	start_time = clock();
	
	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&threads[i],NULL, sequential_read, &byte_size);
	}
	for (i = 0; i < no_of_threads; i++) 
	{
	    pthread_join(threads[i], NULL);  
	}
	clock_t end_time = clock();
	calculate(start_time,end_time,no_of_threads,byte_size);
	}
}

// Create thread for random read operation

void thread_create_random_read(int byte)
{
	int byte_size = byte;
	clock_t start_time;
	for (no_of_threads=1;no_of_threads<3;no_of_threads++)
	{	
	printf("Program Starts for Random Read operations for %d threads\n",no_of_threads);
	start_time = clock();
	
	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&threads[i],NULL, random_read, &byte_size);
	}
	for (i = 0; i < no_of_threads; i++) 
	{
	    pthread_join(threads[i], NULL);  
	}
	clock_t end_time = clock();
	calculate(start_time,end_time,no_of_threads,byte_size);
	}
}


// Sequential write operation starts

void *sequential_write(void *byte)
{
	int byte_size = *(int *)byte;
	int instruction = calculate_instruction(byte_size);
	fptr=fopen("test_sequential.txt","a+");
	
	for (i=0;i<instruction;i++)
	{		
	fwrite(buffer , 1 , byte_size , fptr);
	}	
	//fclose(fptr);
	pthread_exit(NULL);
}

// Random write operation starts

void *random_write(void *byte)
{
	int byte_size = *(int *)byte;
	int instruction = calculate_instruction(byte_size);
	fptr=fopen("test_random.txt","a+");

	for (i=0;i<instruction;i++)
	{
	fseek(fptr,(rand()%1000),SEEK_SET);	
	fwrite(buffer , 1 , byte_size , fptr);
	}	
	//fclose(fptr);
	pthread_exit(NULL);
}

// Sequential read operation starts

void *sequential_read(void *byte)
{
	int byte_size = *(int *)byte;
	int instruction = calculate_instruction(byte_size);
	fptr=fopen("test_sequential.txt","r");
	
	for (i=0;i<instruction;i++)
	{		
	fread(buffer , 1 , byte_size , fptr);
	}	
	//fclose(fptr);
	pthread_exit(NULL);
}

// Random read operation starts

void *random_read(void *byte)
{
	int byte_size = *(int *)byte;
	int instruction = calculate_instruction(byte_size);	
	fptr=fopen("test_random.txt","r");
	
	for (i=0;i<instruction;i++)
	{
	fseek(fptr,(rand()%1000),SEEK_SET);	
	fread(buffer , 1 , byte_size , fptr);
	}	
	//fclose(fptr);
	pthread_exit(NULL);
}

// Number of instructions calculation

int calculate_instruction(int byte)
{
	int byte_size = byte;
	if (byte_size == 1)
	{
		instruction = 100000000;
	}
	else if(byte_size == 1024)
	{
		instruction = 100000;
	}
	else
	{
		instruction = 100;
	}
	return instruction;
}

// Calculate latency and throughput

void calculate(clock_t s_time,clock_t e_time, int thr,int bts)
{
	double total_data;
	int th = thr;
	int bt = bts;
	int instruction = calculate_instruction(bt);	
	FILE *fptr;	
	fptr=fopen("Disk_Benchmark.txt","a+");	
	
	total_time_sec = (double)(e_time - s_time)/CLOCKS_PER_SEC;
	
	total_time_ms = (double)total_time_sec * 1000;
	
	total_data=(double)(th*instruction*bt)/1000000;
	
	printf("\nFor %d byte %d thread\n",bt,th);
	
	fprintf(fptr,"\nFor %d byte\n",bt);
	
	latency =  (double) (total_time_ms)/ total_data;
	
	latency_per_operation = (double)total_time_ms/(th * instruction);

	Throughput = (double) total_data/total_time_sec;
	
	printf("Latency = %lf ms\n",latency);
	
	fprintf(fptr,"Latency = %lf ms\n",latency);

	printf("Latency/Operation = %lf ms\n",latency_per_operation);
	
	fprintf(fptr,"Latency/Operation = %lf ms\n",latency_per_operation);
	
	printf("Throughput = %lf mb/sec \n",Throughput);
	
	fprintf(fptr,"Throughput = %lf mb/sec\n",Throughput);
	
	printf("----------------------------------------\n");
	
	fclose(fptr);
}


