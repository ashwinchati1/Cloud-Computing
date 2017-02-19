import java.util.Scanner;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfilesConfigFile;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class Worker_Main {

	static AmazonDynamoDBClient table_object;
	static AmazonSQS queue_object;
	
	public static void main(String args[]) {
		System.out.println("---------------------------------------------");
		System.out.println("\t\tCloudKon Worker");
		System.out.println("---------------------------------------------");
	
		//get from command-line argument
		
		String src_queue = args[2];
		int numThreads = Integer.parseInt(args[4]);
	
		//configure AWS
		initialize();
		
		//create queue
		String src_url = Create_Queue.src_queue(queue_object,src_queue);
		String dest_url = Create_Queue.dest_queue(queue_object);
		
		//create table
		Create_table.create_table(table_object,queue_object);
		
		//Launch worker
		Create_Worker.worker_thread(numThreads,table_object,queue_object,src_queue,dest_url);
	
	
	}
	
	public static void initialize(){
		
		// AWS credentials....
		
	}
}
