import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;


public class fileSplit implements Runnable {

	String key;
	String value;
	int numLinePerFile;
	
	int i;
	Map<String, String> split_map = new TreeMap<String, String>();
	
	public fileSplit(int i, int numLinePerFile) {
		this.numLinePerFile=numLinePerFile;
		this.i=i;
	}

	@Override
	public void run() {
		

		try {
			// File Split operation starts
			
			long position=0;
			position = position + (i*100*(long)numLinePerFile);
			StringBuffer stringCreator = new StringBuffer();
			
			BufferedWriter buffer = new BufferedWriter(new FileWriter(new File("/home/ashwin/workspace/My_Shared_Memory/outputfiles/output"+i+".txt")));
			byte[] totalBytes = new byte[100*numLinePerFile];
			byte[] bytesPerLine = new byte[100];
			RandomAccessFile File = new RandomAccessFile("input.txt", "r");
			File.seek(position);
			File.read(totalBytes, 0, (100*numLinePerFile));
			
			InputStream input = new ByteArrayInputStream(totalBytes);
			
			int j=1;
			
			while(j<=numLinePerFile){
				input.read(bytesPerLine,0,100);
				String eachLine = new String(bytesPerLine);
				key =  eachLine.substring(0,10); 
				value = eachLine.substring(10);
				split_map.put(key, value);
				j++;
			}

			
			Iterator<Entry<String, String>> map_iterator = split_map.entrySet().iterator();                                                                                     
			
			while(map_iterator.hasNext()){
				Map.Entry<String, String> pairs = map_iterator.next();
				stringCreator.append(pairs.getKey()+pairs.getValue());
			}
			
			
			buffer.write(stringCreator.toString());
			buffer.flush();
			buffer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
