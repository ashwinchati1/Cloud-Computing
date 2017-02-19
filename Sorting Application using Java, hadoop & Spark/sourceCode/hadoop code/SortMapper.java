import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SortMapper extends Mapper<Object, Text, Text, Text> {

	private final static Text Value = new Text();
	private Text Key = new Text();

	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		String line = value.toString();
		Key.set(line.substring(0, 10));
		Value.set(line.substring(10));
		context.write(Key, Value);
	}
}
