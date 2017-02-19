import java.io.BufferedReader;
import java.io.FileReader;


public class Run_Client implements Runnable {

	static String fileName;
	
	public Run_Client(String fileName) {
	this.fileName = fileName;	
	
	}

	@Override
	public void run() {
		
		BufferedReader reader = null;
		String line;
		int i=0;
		
		try {
			reader = new BufferedReader(new FileReader(fileName));
			
			// read Workload file
			
			while ((line = reader.readLine()) != null) {
				
				//add to src_queue
				
				CloudKon_Main.src_queue.add(line+":"+"message "+i);
				i++;
			}

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}


}
