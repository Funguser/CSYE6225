package com.csye6225.lab.ziyao.program.DAO;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "course_student_relation")
public class CourseRegisteredStudent {
    String studentId;
    String courseId;

    public CourseRegisteredStudent() {

    }
    public CourseRegisteredStudent(String courseId, String studentId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }


    @DynamoDBRangeKey(attributeName = "studentId")
    @DynamoDBIndexHashKey(attributeName = "studentId", globalSecondaryIndexName = "idx_studentId")
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }


    @DynamoDBIndexRangeKey(attributeName = "courseId", globalSecondaryIndexName = "idx_studentId")
    @DynamoDBHashKey(attributeName = "courseId")
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
