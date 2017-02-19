import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;


public class Run_Client implements Runnable {

	AmazonDynamoDBClient table_object;
	AmazonSQS queue_object;
	String src_queue;
	String dest_queue = "Dest_Queue";
	String fileName;
	
	//constructor for the class
	
	public Run_Client(String queue, AmazonDynamoDBClient table_object, AmazonSQS queue_object, String fileName) {
		this.src_queue = queue;
		this.table_object = table_object;
		this.queue_object = queue_object;
		this.fileName = fileName;
	}

	// function to get URL of the queue
	
	public String geturl(String queueName){
		String url;
		GetQueueUrlRequest geturl_object = new GetQueueUrlRequest(queueName);
		url = queue_object.getQueueUrl(geturl_object).getQueueUrl();
		return url;
	}
	@Override
	public void run() {
		
		BufferedReader reader = null;
		String line;
		int i=0;
		int numtasks = 0;
		List<Message> msg_obj;
		
		try {

	        // read from workload file
			reader = new BufferedReader(new FileReader(fileName));
			while ((line = reader.readLine()) != null) {
				
				//put message to Src_Queue SQS
				queue_object.sendMessage(new SendMessageRequest(geturl(src_queue), line));
				i++;
			}
			
			//receive message from Dest_Queue
			
			ReceiveMessageRequest msgReq_object = new ReceiveMessageRequest(geturl(dest_queue));
			msg_obj = queue_object.receiveMessage(msgReq_object).getMessages();
            
            while(numtasks < 10000){
            	
            	
            	msg_obj = queue_object.receiveMessage(msgReq_object).getMessages();
            	 
            	 for (Message message : msg_obj) {
            		
            		 //delete message
            		 
            		 String handle = msg_obj.get(0).getReceiptHandle();
            		 queue_object.deleteMessage(new DeleteMessageRequest(geturl(dest_queue), handle));
            	 }
            	 numtasks++;
            	 
            	 //fetch another message
            	 
            	 msg_obj = queue_object.receiveMessage(msgReq_object).getMessages();
            }
            

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}


}
