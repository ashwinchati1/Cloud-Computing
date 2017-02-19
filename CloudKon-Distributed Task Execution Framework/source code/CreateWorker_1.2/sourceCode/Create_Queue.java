import com.amazonaws.AmazonClientException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;

public class Create_Queue {

	public static String src_queue(AmazonSQS queue_object, String src_queue) {

		String url = null;
			
		// get URL of queue
			GetQueueUrlRequest geturl = new GetQueueUrlRequest(
					src_queue);
			url = queue_object.getQueueUrl(geturl).getQueueUrl();
		
			System.out.println("Creating a new source SQS queue.\n");
			CreateQueueRequest create = new CreateQueueRequest(
					src_queue);
			url = queue_object.createQueue(create).getQueueUrl();
			
			return url;
	}

	public static String dest_queue(AmazonSQS queue_object) {

		String url = null;
	
			GetQueueUrlRequest geturl = new GetQueueUrlRequest(
					"Dest_Queue");
			url = queue_object.getQueueUrl(geturl).getQueueUrl();
		
			System.out.println("Creating a new Destination SQS queue.\n");
			CreateQueueRequest create = new CreateQueueRequest(
					"Dest_Queue");
			url = queue_object.createQueue(create).getQueueUrl();

			return url;
		
	}

}
