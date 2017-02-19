import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Thread_Create_Split {

	public static void splitFile(int numberOfFile,int linesPerFile){
		
	// Create the threads for file_Split operation
		
	ExecutorService pool = Executors.newFixedThreadPool(1);
	
	int i = 0;
	
		while (i<numberOfFile){	
		
			Thread t = new Thread(new fileSplit(i,linesPerFile));
			pool.execute(t);
			i++;
	
		}
	
		pool.shutdown();
    
		while (!pool.isTerminated()) {
    
		}
	}


}
