package com.csye6225.lab.ziyao.program.helper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.sns.AmazonSNS;
import com.csye6225.lab.ziyao.AWSService.DynamoDBConnector;
import com.csye6225.lab.ziyao.AWSService.SNSService;
import com.csye6225.lab.ziyao.program.DAO.CourseRegisteredStudent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseHelper {
    static DynamoDBConnector dynamoDB;
    static DynamoDBMapper mapper;

    static {
        try {
            dynamoDB = new DynamoDBConnector();
            dynamoDB.init();
            mapper = new DynamoDBMapper(dynamoDB.getConnector());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static List<CourseRegisteredStudent> queryByCourseId(String courseId, String studentId) {
//        CourseRegisteredStudent searchParam = new CourseRegisteredStudent();
//
//        if (courseId == null)
//            return null;
//        searchParam.setCourseId(courseId);
//        if (studentId != null)
//            searchParam.setStudentId(studentId);
//        DynamoDBQueryExpression<CourseRegisteredStudent> spec = new DynamoDBQueryExpression<CourseRegisteredStudent>();
//        spec.setHashKeyValues(searchParam);
//        spec.setIndexName("idx_courseId");
//        spec.setConsistentRead(false);
//        List<CourseRegisteredStudent> list = mapper.query(CourseRegisteredStudent.class, spec);
//        return list;
//    }

//    public static List<CourseRegisteredStudent> queryByBoth(String courseId, String studentId) {
//        CourseRegisteredStudent searchParam = new CourseRegisteredStudent();
//
//        Map<String, AttributeValue> eav = new HashMap<>();
//        eav.put(":val1", new AttributeValue().withS(courseId));
//        eav.put(":val2", new AttributeValue().withS(studentId));
//
//        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
//                .withFilterExpression("courseId = :val1 and studentId = :val2").withExpressionAttributeValues(eav);
//        List<CourseRegisteredStudent> result = mapper.scan(CourseRegisteredStudent.class, scanExpression);
//        return result;
//    }

    public static List<CourseRegisteredStudent> query(String courseId, String studentId) {
        if (courseId != null && studentId != null) {
            Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
            eav.put(":val1", new AttributeValue().withS(courseId));
            eav.put(":val2", new AttributeValue().withS(studentId));

            DynamoDBQueryExpression<CourseRegisteredStudent> queryExpression = new DynamoDBQueryExpression<CourseRegisteredStudent>()
                    .withKeyConditionExpression("courseId = :val1 and studentId = :val2").withExpressionAttributeValues(eav);
            return mapper.query(CourseRegisteredStudent.class, queryExpression);
        } else if (courseId == null) {
            CourseRegisteredStudent searchParam = new CourseRegisteredStudent();
            searchParam.setStudentId(studentId);
            DynamoDBQueryExpression<CourseRegisteredStudent> spec = new DynamoDBQueryExpression<CourseRegisteredStudent>();
            spec.setHashKeyValues(searchParam);
            spec.setIndexName("idx_studentId");
            spec.setConsistentRead(false);
            return mapper.query(CourseRegisteredStudent.class, spec);
        } else {
            Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
            eav.put(":val1", new AttributeValue().withS(courseId));

            DynamoDBQueryExpression<CourseRegisteredStudent> queryExpression = new DynamoDBQueryExpression<CourseRegisteredStudent>()
                    .withKeyConditionExpression("courseId = :val1").withExpressionAttributeValues(eav);
            return mapper.query(CourseRegisteredStudent.class, queryExpression);
        }
    }

    public static List<String> parseRelationToCourse(String courseId) {
        List<CourseRegisteredStudent> queryResult = query(courseId, null);
        List<String> result = new ArrayList<>();
        for (CourseRegisteredStudent i : queryResult) {
            result.add(i.getStudentId());
        }
        return result;
    }

    public static List<String> parseRelationToStudent(String studentId) {
        List<CourseRegisteredStudent> queryResult = query(null, studentId);
        List<String> result = new ArrayList<>();
        for (CourseRegisteredStudent i : queryResult) {
            result.add(i.getCourseId());
        }
        return result;
    }
}