package net.lomeli.awshelper.util;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

/**
 * Go to http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/QueryingJavaDocumentAPI.html?shortFooter=true
 * for info about querying dynamoDB
 */
public enum DynamoDBUtil {
    INSTANCE();

    private AmazonDynamoDBAsyncClient client;
    private DynamoDB dynamoDB;

    DynamoDBUtil() {
        client = (AmazonDynamoDBAsyncClient) AmazonDynamoDBAsyncClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        dynamoDB = new DynamoDB(client);
    }

    public Table getTable(String tableName) {
        return dynamoDB.getTable(tableName);
    }

    public ItemCollection<QueryOutcome> query(Table table, QuerySpec querySpec) {
        return table != null ? table.query(querySpec) : null;
    }

    public ItemCollection<QueryOutcome> query(String tableName, QuerySpec querySpec) {
        return query(getTable(tableName), querySpec);
    }

    public Item query(Table table, GetItemSpec spec) {
        return table != null ? table.getItem(spec) : null;
    }

    public Item query(String tableName, GetItemSpec spec) {
        return query(getTable(tableName), spec);
    }

    public AmazonDynamoDBAsyncClient getClient() {
        return client;
    }

    public DynamoDB getDynamoDB() {
        return dynamoDB;
    }
}
