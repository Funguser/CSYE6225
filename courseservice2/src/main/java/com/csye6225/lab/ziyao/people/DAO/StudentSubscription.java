package com.csye6225.lab.ziyao.people.DAO;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.HashMap;
import java.util.Map;

@DynamoDBTable(tableName = "student_subscription")
public class StudentSubscription {
    String studentId;
    Map<String, String> subscriptionMap;

    public StudentSubscription() {
        subscriptionMap = new HashMap<>();
    }
    public StudentSubscription(String studentId) {
        this.studentId = studentId;
        subscriptionMap = new HashMap<>();
    }

    @DynamoDBHashKey
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @DynamoDBAttribute(attributeName = "subscriptionMap")

    public Map<String, String> getSubscriptionMap() {
        return subscriptionMap;
    }

    public void setSubscriptionMap(Map<String, String> subscriptionMap) {
        this.subscriptionMap = subscriptionMap;
    }
}
