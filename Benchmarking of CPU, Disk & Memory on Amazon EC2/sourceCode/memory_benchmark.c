/* Subject: CS 553: CLoud  Computing
   Author : Ashwin Chati
   CWID   : A20354704
   Program: Memory Benchmarking
*/ 

#include <stdio.h>
#include <stdlib.h>
#include<time.h>
#include<string.h>
#include<pthread.h>

// Variable Declaration

int no_of_threads,i,instruction;
double total_time_sec, total_time_ms,latency,latency_per_operation,Throughput;
pthread_t threads [4];
clock_t start_time,end_time;
//char w[2] = 'A';
char *src_seq;
char *dest_seq;
char *src_ran;
char *dest_ran;

//Function Declaration

void thread_create_seq_read_write(int byte);
void thread_create_random_read_write(int byte);
void one_byte_seq_read_write();
void one_kb_seq_read_write();
void one_mb_seq_read_write();
void one_byte_ran_read_write();
void one_kb_ran_read_write();
void one_mb_ran_read_write();
void *seq_read_write(void *byte);
void *random_read_write(void *byte);
int calculate_instruction(int byte_size);
void calculate(clock_t s_time,clock_t e_time, int thr,int bts);


void one_byte_seq_read_write()
{
	thread_create_seq_read_write(1);
}

void one_kb_seq_read_write()
{
	thread_create_seq_read_write(1024);
}

void one_mb_seq_read_write()
{
	thread_create_seq_read_write(1048576);
}

void one_byte_ran_read_write()
{
	thread_create_random_read_write(1);
}

void one_kb_ran_read_write()
{
	thread_create_random_read_write(1024);
}

void one_mb_ran_read_write()
{
	thread_create_random_read_write(1048576);
}

// Create thread for sequential read-write operation

void thread_create_seq_read_write(int byte)
{
	int byte_size = byte;
	clock_t start_time;
	for (no_of_threads=1;no_of_threads<3;no_of_threads++)
	{
	printf("Program Starts for Sequential Read-Write operations for %d threads\n",no_of_threads);
	start_time = clock();
	
	for(i=0;i<no_of_threads;i++)
	{	
		pthread_create(&threads[i],NULL, seq_read_write, &byte_size);
	}
	for (i = 0; i < no_of_threads; i++) 
	{	
	    pthread_join(threads[i], NULL);  
	}
	clock_t end_time = clock();
	calculate(start_time,end_time,no_of_threads,byte_size);
}
}

// Create thread for random read-write operation

void thread_create_random_read_write(int byte)
{
	int byte_size = byte;
	clock_t start_time;
	for (no_of_threads=1;no_of_threads<3;no_of_threads++)
	{	
	printf("Program Starts for Random Read-Write operations for %d threads\n",no_of_threads);
	start_time = clock();
	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&threads[i],NULL, random_read_write, &byte_size);
	}
	for (i = 0; i < no_of_threads; i++) 
	{
	    pthread_join(threads[i], NULL);  
	}
	clock_t end_time = clock();
	calculate(start_time,end_time,no_of_threads,byte_size);
}
}

//Sequential read-write operarion

void *seq_read_write(void *byte)
{
	int byte_size = *(int *)byte;
	int instruction = calculate_instruction(byte_size);
	for (i=0;i<instruction;i++)
	{
	 memcpy ( dest_seq, src_seq, byte_size);
	 dest_seq = dest_seq + byte_size;
	 src_seq = src_seq + byte_size;
	}	
	pthread_exit(NULL);
}

//Random read-write operation

void *random_read_write(void *byte)
{
	int byte_size = *(int *)byte;
	int random; //= rand() % ((7*1024*1024)-(byte_size+1));
	int instruction = calculate_instruction(byte_size);
	for (i=0;i<instruction;i++)
	{
	random = rand() % ((7*1024*1024)-(byte_size+1));
	 memcpy ( &dest_ran[random], &src_ran[random], byte_size);
	}	
	pthread_exit(NULL);
}

//Calculate number of instructions

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
	fptr=fopen("Memory_Benchmark.txt","a+");	
	
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

//Main Function

int main()
{
	printf("-----------------------------------------------------\n");
	printf("\t\t Memory Benchmarking\n");
	printf("-----------------------------------------------------\n");

	src_seq = (char *) malloc(200*1024*1024);
	dest_seq = (char *) malloc(200*1024*1024);
	src_ran = (char *) malloc(200*1024*1024);
	dest_ran = (char *) malloc(200*1024*1024);

	// Writing data into buffer 

	for (i=0;i<(200*1024*1024);i++)
	{
	src_seq[i]='C';
	src_ran[i]='A';
	}

	one_byte_seq_read_write();
	one_kb_seq_read_write();
	one_mb_seq_read_write();
	one_byte_ran_read_write();
	one_kb_ran_read_write();
	one_mb_ran_read_write();
}
