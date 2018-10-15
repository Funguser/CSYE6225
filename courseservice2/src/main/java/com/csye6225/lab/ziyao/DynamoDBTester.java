package com.csye6225.lab.ziyao;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;

import java.util.HashMap;
import java.util.Map;

public class DynamoDBTester {
    static AmazonDynamoDB dynamoDB;

    public static void init() throws Exception {
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        credentialsProvider.getCredentials();
        dynamoDB = AmazonDynamoDBClientBuilder
                    .standard()
                    .withCredentials(credentialsProvider)
                    .withRegion("us-west-2")
                    .build();

    }

    public static void main(String[] args) throws Exception {
        init();
        String tableName = "student_test";

        GetItemRequest getItemRequest = new GetItemRequest();

        // key that are you looking for: student_id with value 123
        Map<String, AttributeValue> itemToFetch = new HashMap<>();
        itemToFetch.put("student_id", new AttributeValue().withS("123"));

        getItemRequest.setKey(itemToFetch);

        // the table that we are looking at
        getItemRequest.setTableName(tableName);
        GetItemResult getItemResult = dynamoDB.getItem(getItemRequest);
        System.out.println("GetItemResult:" + getItemResult);
    }
}
