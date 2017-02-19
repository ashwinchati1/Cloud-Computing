import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.sqs.AmazonSQS;


public class Create_Worker {

	public static void worker_thread(int num_threads, AmazonDynamoDBClient table_object, AmazonSQS queue_object, String src_queue, String dest_url){
		
		//create worker threadpool
		
		ExecutorService executor_worker = Executors
				.newFixedThreadPool(num_threads);

		for (int i = 0; i < num_threads; i++) {
			Run_Worker worker = new Run_Worker(table_object, queue_object, src_queue,dest_url);
			Thread thread_worker = new Thread(worker);
			executor_worker.execute(thread_worker);
		}
		executor_worker.shutdown();
		while (!executor_worker.isTerminated()) {

		}
	}
}
