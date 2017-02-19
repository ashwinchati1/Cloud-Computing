import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class fileMerge implements Runnable {

	private int numThread;
	private String file1;
	private String file2;
	private int j;
	
	
	public fileMerge(String file1, String file2, int j) {

		this.file1 = file1;
		this.file2 = file2;
		this.j=j;
	}
	
	public void run() {
		
		try {
	
			// File Merge operation starts
			
			Path path = Paths.get("/home/ashwin/workspace/Shared_Memory_1.4/outputfiles/");
			String Line1, Line2,key1,key2,value1,value2;
			
			FileInputStream fileInput1 = new FileInputStream(path+"/"+file1);
			FileInputStream fileInput2 = new FileInputStream(path+"/"+file2);
			BufferedReader buffer1 = new BufferedReader(new InputStreamReader(fileInput1));
			BufferedReader buffer2 = new BufferedReader(new InputStreamReader(fileInput2));
						
			BufferedWriter bufferwriter = new BufferedWriter(new FileWriter(new File("/home/ashwin/workspace/Shared_Memory_1.4/outputfiles/outputsort"+j+".txt")));
		
			Line1 = buffer1.readLine();
			Line2 = buffer2.readLine();
			
			while(buffer1.ready() && buffer2.ready()){
					
					key1 = Line1.substring(0,10); 
					value1 = Line1.substring(11);
					key2 = Line2.substring(0,10); 
					value2 = Line2.substring(11);
					
					if(key1.compareTo(key2)>0){
						bufferwriter.write(key2+" "+value2+"\r\n");
						Line2 = buffer2.readLine();
					} 
					else{
						bufferwriter.write(key1+" "+value1+"\r\n");
						Line1 = buffer1.readLine();
					}
				}
				
				if(!Line1.isEmpty())
				{
					bufferwriter.write(Line1+"\r\n");
					while(buffer1.ready())
					{
						Line1 = buffer1.readLine();
						bufferwriter.write(Line1+"\r\n");
					}
				}
				
				if(!Line2.isEmpty())
				{
					bufferwriter.write(Line2+"\r\n");
					while(buffer2.ready())
					{
						Line2 = buffer2.readLine();
						bufferwriter.write(Line2+"\r\n");
					}
				}
				
				bufferwriter.flush();
				bufferwriter.close();
				
				Files.delete(Paths.get("./outputfiles/"+file1));
				Files.delete(Paths.get("./outputfiles/"+file2));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
