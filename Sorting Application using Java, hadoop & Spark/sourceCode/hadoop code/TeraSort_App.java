import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class TeraSort_App {

	public static void main(String[] args) throws Exception {

		long start_time = System.currentTimeMillis();
		System.out.println("start_time:" + start_time);

		Configuration conf = new Configuration();
		Job job = new Job(conf, "TeraSort_App_Ashwin");
		job.setJarByClass(TeraSort_App.class);
		job.setMapperClass(SortMapper.class);
		job.setCombinerClass(SortReducer.class);
		job.setReducerClass(SortReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
		long end_time = System.currentTimeMillis();
		System.out.println("end_time:" + end_time);

		long total_time = end_time - start_time;
		System.out.println("total time taken:" + total_time);
	}
}