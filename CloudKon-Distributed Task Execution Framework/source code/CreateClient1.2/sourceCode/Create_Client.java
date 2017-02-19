import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.sqs.AmazonSQS;


public class Create_Client {

	public static void client_thread(String src_queue, AmazonDynamoDBClient table_object, AmazonSQS queue_object, String fileName){
		
		// create client threadpool
		
		ExecutorService executor_client = Executors.newFixedThreadPool(1);
		for (int i = 0; i < 1; i++) {
			Run_Client client = new Run_Client(src_queue, table_object, queue_object, fileName);
			Thread thread_client = new Thread(client);
			executor_client.execute(thread_client);
		}
			executor_client.shutdown();
			while (!executor_client.isTerminated()) {

			}
	}
}
