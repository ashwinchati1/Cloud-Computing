import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Thread_Create_Merge {

public static void mergeFiles() {
		
		File folder = new File("/home/ashwin/workspace/My_Shared_Memory/outputfiles/");
		int i,fileExtension = 1;
		String f1,f2;
		
		// Creates threads for file_merge operation
		
		while(true){
			
			String[] fileNames = folder.list();
			int numFiles = fileNames.length;
			if(numFiles < 2){    
				break;
			}
			else{
				numFiles=numFiles-1;
				ExecutorService pool = Executors.newFixedThreadPool(1);
				int j = 0;
				
				while(j<numFiles){
					i=j+1;
					f1 = fileNames[j];
					f2 = fileNames[j+1];
					Thread t = new Thread(new fileMerge(f1,f2,fileExtension));
					fileExtension++;
					pool.execute(t);
					j+=2;
				}
				pool.shutdown();
		        while (!pool.isTerminated()) {
		        }
			}
		}
		
	}

}
