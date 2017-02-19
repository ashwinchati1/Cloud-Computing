import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CloudKon_Main {

	public static ConcurrentLinkedQueue<String> src_queue = new ConcurrentLinkedQueue<String>();
	public static ConcurrentLinkedQueue<String> dest_queue = new ConcurrentLinkedQueue<String>();
	
	public static void main(String args[]) {

		System.out.println("---------------------------------------------");
		System.out.println("\t\tCloudKon");
		System.out.println("---------------------------------------------");

		// get filename and number of workers from the command line arguments
		
		int num_worker = Integer.parseInt(args[4]);
		String fileName = args[6];
	
		//start time
		long stime = System.currentTimeMillis();
		
		//create client thread
		Create_Client.client_thread(fileName);
		
		//create worker thread
		Create_Worker.worker_thread(num_worker,src_queue,dest_queue);
		
		//end time
		long etime = System.currentTimeMillis();
		
		//calculate total time
		long total_time = etime - stime;
		
		System.out.println("Total time for "+num_worker+" workers: "+total_time+" msec");
	}

}
