package com.csye6225.lab.ziyao.people.DAO;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.csye6225.lab.ziyao.program.DAO.Course;
import com.csye6225.lab.ziyao.program.DAO.Program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DynamoDBTable(tableName = "student")
public class Student extends People {
    String department;
    String studentId;
    List<String> courseList;
    String email;

    public Student(String firstName, String lastName, Date registerDate, String id) {
        super(firstName, lastName, registerDate, id);
        this.courseList = new ArrayList<>();
    }

    public Student() {
        courseList = new ArrayList<>();
    }


    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDBAttribute(attributeName = "lastName")
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDBAttribute(attributeName = "department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBIndexHashKey(attributeName = "studentId", globalSecondaryIndexName = "idx_studentId")
    public String getStudentId() {
        return this.studentId;
    }
    public void setStudentId(String id) {
        this.studentId = id;
    }


    @DynamoDBAttribute(attributeName = "registerDate")
    public String getRegisterStringDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(this.registerDate);
    }
    public void setRegisterStringDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.registerDate = sdf.parse(date);
    }

//    @DynamoDBAttribute(attributeName = "courseList")
    public List<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<String> courseList) {
        this.courseList = courseList;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
