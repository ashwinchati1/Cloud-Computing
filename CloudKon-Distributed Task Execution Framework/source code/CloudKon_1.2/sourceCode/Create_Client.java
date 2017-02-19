import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Create_Client {

	public static void client_thread(String fileName){
		
		//create threadpool 
		
		ExecutorService executor_client = Executors.newFixedThreadPool(1);
		for (int i = 0; i < 1; i++) {
			Run_Client client = new Run_Client(fileName);
			Thread thread_client = new Thread(client);
			executor_client.execute(thread_client);
		}
			executor_client.shutdown();
			while (!executor_client.isTerminated()) {

			}
	}
}
