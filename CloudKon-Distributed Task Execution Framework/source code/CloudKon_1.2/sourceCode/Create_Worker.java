import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Create_Worker {

	public static void worker_thread(int num_worker, ConcurrentLinkedQueue<String> src_queue, ConcurrentLinkedQueue<String> dest_queue){
		
		// create threadpool for workers
		
		ExecutorService executor_worker = Executors
				.newFixedThreadPool(num_worker);

		for (int i = 0; i < num_worker; i++) {
			Run_Worker worker = new Run_Worker(src_queue,dest_queue);
			Thread thread_worker = new Thread(worker);
			executor_worker.execute(thread_worker);
		}
		executor_worker.shutdown();
		while (!executor_worker.isTerminated()) {

		}
	}
}
