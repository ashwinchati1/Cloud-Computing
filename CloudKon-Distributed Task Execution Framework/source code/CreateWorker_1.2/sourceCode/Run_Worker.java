import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;


public class Run_Worker implements Runnable {

	String src_queue, dest_queue, message_ID, msg_body, src_url, dest_url;
	String task[];
	static AmazonDynamoDBClient table_object;
	static List<Message> msgs;
	AmazonSQS queue_object;
	static String tableName;
	
	//constructor
	public Run_Worker(AmazonDynamoDBClient table_object, AmazonSQS queue_object, String src_queue, String dest_url) {
		this.src_queue = src_queue;
		dest_queue = dest_url;
		tableName = "table_dynamo";
		this.table_object = table_object;
		this.queue_object = queue_object;

	}

	//check for duplicate task
	
	public static boolean check_task(String message_ID){
        
		boolean noDuplicate = true;
		
		   	HashMap<String, Condition> sf = new HashMap<String, Condition>();
	    	
	    	Condition constraint = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
	                .withAttributeValueList(new AttributeValue().withS(message_ID));
	    	sf.put("taskid", constraint);
	    	ScanRequest iterate = new ScanRequest(tableName).withScanFilter(sf);
	    	ScanResult iterate_result = table_object.scan(iterate);
	    	
	    	if (iterate_result.getCount() > 0){
	    		noDuplicate = false;
	   	    	}
	    	else {
	    		noDuplicate = true;
	    	}
		
		return noDuplicate;
	}
	
	
	@Override
	public void run() {
		
		try {
			//fetch task
			ReceiveMessageRequest msgreq = new ReceiveMessageRequest(src_queue);
			msgs = queue_object.receiveMessage(msgreq).getMessages();
            
            System.out.println("worker listening");
            
            while (true){
            	 for (Message m : msgs) {
            		
            		 message_ID = m.getMessageId();
                     msg_body = m.getBody();
                    
                     // check for duplicate
                     boolean noDuplicate = check_task(message_ID);
                     if (noDuplicate == false){
                    	 
                     }else{
                     String messageReceiptHandle = msgs.get(0).getReceiptHandle();
                   
                     //split message
                     task = msg_body.split(" ");
                     Thread.sleep(Integer.parseInt(task[1]));
             
                     //put in dynamoDB
                     
                     Map<String, AttributeValue> table = new HashMap<String, AttributeValue>();
                     table.put("taskid", new AttributeValue(message_ID));
                     PutItemRequest IR = new PutItemRequest(tableName, table);
                     PutItemResult iResult = table_object.putItem(IR);
                     
                     //send task status to Dest_Queue
                     
                     queue_object.sendMessage(new SendMessageRequest(dest_queue, message_ID+":"+"status 0"));
                     queue_object.deleteMessage(new DeleteMessageRequest(src_queue, messageReceiptHandle));
            	 }
            	 }
            	 msgs = queue_object.receiveMessage(msgreq).getMessages();
            }
         
		}catch (Exception e) {
			   System.out.println("worker listeninng ends");
		}

	}

}
