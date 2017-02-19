import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class My_Shared_Memory {
	
	public static void main(String args[]) throws FileNotFoundException{
		
		System.out.println("----------------------------------------------------------------------\n");
		System.out.println("\t\t\tShared Memory Program\n");
		System.out.println("----------------------------------------------------------------------\n");
		
		//Calculates start time of the application
		
		long start_time = System.currentTimeMillis();
		System.out.println("Program starts at: "+start_time);
		
		//Reads the input file
		
		File inputFile = new File("inputfile.txt");
		
		try {
			
			BufferedReader buffer = new BufferedReader(new FileReader("input.txt"));
			String Line;
			int count = 0;
			int linesPerFile = 100000;
			int numberOfFile;
			
			//Counts the number of records in the file
			
			while ((Line = buffer.readLine()) != null) {
				count++;
			}
		
			//Calculates the number of files to be created.
			
			numberOfFile = count / linesPerFile;
		
			Thread_Create_Split.splitFile(numberOfFile, linesPerFile);
			Thread_Create_Merge.mergeFiles();
			
			//calculates end_time of the application
			
			long end_time = System.currentTimeMillis();
			System.out.println("Program ends at: "+end_time);
			
			//Calculates total_time for the application
			
			long total_time = end_time - start_time;
			System.out.println("Total time taken by the program is: "+total_time+" msec");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
