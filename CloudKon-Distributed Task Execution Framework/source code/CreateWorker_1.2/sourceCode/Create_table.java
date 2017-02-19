import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.Tables;
import com.amazonaws.services.sqs.AmazonSQS;


public class Create_table {

	public static void create_table(AmazonDynamoDBClient tabel_object, AmazonSQS queue_object) {
		
		//create table
		
		 try {
	            String tableName = "table_dynamo";
	            
	            if (Tables.doesTableExist(tabel_object, tableName)) {
	                System.out.println("Table " + tableName + " is already ACTIVE");
	            } else {
	                // Create a table with a primary hash key named 'name', which holds a string
	                CreateTableRequest create = new CreateTableRequest().withTableName(tableName)
	                    .withKeySchema(new KeySchemaElement().withAttributeName("taskid").withKeyType(KeyType.HASH))
	                    .withAttributeDefinitions(new AttributeDefinition().withAttributeName("taskid").withAttributeType(ScalarAttributeType.S))
	                    .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(100L));
	                    TableDescription createdTableDescription = tabel_object.createTable(create).getTableDescription();
	                System.out.println("Created Table: " + createdTableDescription);

	                System.out.println("Waiting for " + tableName + " to get inititlized");
	                Tables.awaitTableToBecomeActive(tabel_object, tableName);
	            }

	            } catch (Exception e) {
	    		
	    			System.out.println("Cannot create tabel...caught Amazon Exception");
	    		}
		
	}

}
