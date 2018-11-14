package com.csye6225.lab.ziyao.people.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.csye6225.lab.ziyao.DynamoDB.DynamoDBConnector;
import com.csye6225.lab.ziyao.people.DAO.Professor;
import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.program.DAO.Course;
import com.csye6225.lab.ziyao.program.service.CourseService;
import com.csye6225.lab.ziyao.resource.InMemoryDatabase;

import java.util.*;

public class StudentService {
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


    public StudentService() throws Exception {
        dynamoDB = new DynamoDBConnector();
        dynamoDB.init();
        mapper = new DynamoDBMapper(dynamoDB.getConnector());

    }

    public static Student getStudent(String id) {
        if (id == null)
            return null;
        Student student = new Student();
        student.setStudentId(id);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put("v_id", new AttributeValue().withN(String.valueOf(id)));
        DynamoDBQueryExpression<Student> spec = new DynamoDBQueryExpression<Student>();
        spec.setHashKeyValues(student);
        spec.setIndexName("idx_studentId");
        spec.setConsistentRead(false);
        List<Student> list = mapper.query(Student.class, spec);

        if (list.size() != 0)
            return list.get(0);
        return null;
    }

    public Student addStudent(Student student){
        if (student.getStudentId() == null)
            return null;
        if (getStudent(student.getStudentId()) != null)
            return null;
        student.setRegisterDate(new Date());
        List<String> registeredCourse = new ArrayList<>();
        if (student.getCourseList().size() != 0) {
            for (String courseId : student.getCourseList()) {
                if (CourseService.getCourse(courseId) == null)
                    continue;
                Course course = CourseService.getCourse(courseId);
                if (isExist(course, student.getStudentId()))
                    continue;
                course.getRegisteredStudentList().add(student.getStudentId());
                mapper.save(course);
                registeredCourse.add(courseId);
            }
        }
        student.setCourseList(registeredCourse);
        mapper.save(student);
        return student;
    }

    public boolean isExist(Course course, String studentId) {
        for (String id : course.getRegisteredStudentList()) {
            if (id.equals(studentId))
                return true;
        }
        return false;
    }

    public Student deleteStudent(String id) {
        Student student = getStudent(id);
        if (student != null) {
            for (String courseId : student.getCourseList()) {
                Course course = CourseService.getCourse(courseId);
                if (course == null)
                    continue;
                if (!isExist(course, id))
                    continue;
                course.getRegisteredStudentList().remove(id);
                mapper.save(course);
            }
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<Course> courseList = mapper.scan(Course.class, scanExpression);
            for (Course course : courseList) {
                if (id.equals(course.getTAId())){
                    course.setTAId("");
                    mapper.save(course);
                }
            }
            mapper.delete(student);
            return student;
        }
        return null;
    }

    public Student editStudent(String id, Student student) {
        Student stu = getStudent(id);
        if (stu != null) {
            deleteStudent(id);
            stu.setDepartment(student.getDepartment());
            stu.setFirstName(student.getFirstName());
            stu.setLastName(student.getLastName());
            stu.setCourseList(student.getCourseList());
            addStudent(stu);
            return stu;
        }
        return null;
    }

    public List<Student> getAllStudent() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Student> result = mapper.scan(Student.class, scanExpression);
        return result;
    }

    public List<Student> getStudentByDepartment(String department) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(department));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("department = :val1").withExpressionAttributeValues(eav);
        List<Student> result = mapper.scan(Student.class, scanExpression);
        return result;
    }
//
//    public List<Course> getCourseList(int id) {
//        if (!allStudent.containsKey(id))
//            return null;
//        return allStudent.get(id).getCourseList();
//    }
//
//    public List<Course> registerCourse(int id, String courseName) {
//        if (!allStudent.containsKey(id))
//            return null;
//        if (!database.getCourseDB().containsKey(courseName))
//            return null;
//        List<Course> courseList = allStudent.get(id).getCourseList();
//        for (Course course : courseList) {
//            if (course != null && course.getCourseName().equals(courseName))
//                return courseList;
//        }
//        courseList.add(database.getCourseDB().get(courseName));
//        database.getCourseDB().get(courseName).getStudentList().add(id);
//        return courseList;
//    }
//
//    public List<Course> dropCourse(int id, String courseName) {
//        if (!allStudent.containsKey(id))
//            return null;
//        if (!database.getCourseDB().containsKey(courseName))
//            return null;
//        List<Course> courseList = allStudent.get(id).getCourseList();
//        Iterator iter = courseList.iterator();
//        while (iter.hasNext()) {
//            Course tmp = (Course)iter.next();
//            if (tmp.getCourseName().equals(courseName))
//                iter.remove();
//        }
//        List<Integer> courseEnroll = database.getCourseDB().get(courseName).getStudentList();
//        iter = courseEnroll.iterator();
//        while (iter.hasNext()) {
//            int tmp = (int)iter.next();
//            if (tmp == id)
//                iter.remove();
//        }
//        return courseList;
//    }
}
