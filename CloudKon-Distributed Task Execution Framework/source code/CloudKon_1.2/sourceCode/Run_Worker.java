import java.util.Queue;

public class Run_Worker implements Runnable {

	Queue<String> src_queue, dest_queue;

	public Run_Worker(Queue<String> src_queue2, Queue<String> dest_queue2) {
		src_queue = src_queue2;
		dest_queue = dest_queue2;
	}

	@Override
	public void run() {
		
		while (!src_queue.isEmpty()) {

			// read tasks from queue
			String string = src_queue.poll();
			
			// get sleep time from task
			
			String task[] = string.split(":");
			String time[] = task[0].split(" ");

			try {
				
				//Execute the tasks
				
				Thread.sleep(Integer.parseInt(time[1]));
				
				// put status of execution in dest_queue
				
				dest_queue.add(task[1] + ":" + "Status 0");
				
			} catch (Exception e) {
				
				e.printStackTrace();
			} 
		}
	}
}
