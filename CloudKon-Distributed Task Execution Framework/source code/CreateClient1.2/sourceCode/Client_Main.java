import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfilesConfigFile;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;


public class Client_Main {

	static AmazonDynamoDBClient table_object;
	static AmazonSQS queue_object;
	
	public static void main(String args[]){
		
		System.out.println("---------------------------------------------");
		System.out.println("\t\tCloudKon Client");
		System.out.println("---------------------------------------------");
		
		//get queuename and workload file from command-line argument
		
		String Qname = args[2];
		String fileName = args[4];
		
		// AWS  configuration
		
		initialize();
		
		//start time
		long stime = System.currentTimeMillis();
		
		Create_Client.client_thread(Qname, table_object, queue_object, fileName);
		
		//end time
		long etime = System.currentTimeMillis();
		
		//calculate total time
		long total_time = etime - stime;
		System.out.println("Total time: "+total_time+"msec");
		
		
	}
	
public static void initialize(){
		
		// Insert your AWS credentials here....
		
	}
}
