package com.csye6225.lab.ziyao.people.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;
import com.csye6225.lab.ziyao.AWSService.DynamoDBConnector;
import com.csye6225.lab.ziyao.AWSService.SNSService;
import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.people.DAO.StudentSubscription;
import com.csye6225.lab.ziyao.people.resources.StudentResource;
import com.csye6225.lab.ziyao.program.DAO.Course;
import com.csye6225.lab.ziyao.program.DAO.CourseRegisteredStudent;
import com.csye6225.lab.ziyao.program.helper.CourseHelper;
import com.csye6225.lab.ziyao.program.service.CourseService;

import java.util.*;

import static com.csye6225.lab.ziyao.program.service.CourseService.getCourse;

public class StudentService {
    static DynamoDBConnector dynamoDB;
    static DynamoDBMapper mapper;
    static AmazonSNS snsClient;

    static {
        try {
            dynamoDB = new DynamoDBConnector();
            dynamoDB.init();
            mapper = new DynamoDBMapper(dynamoDB.getConnector());
            snsClient = SNSService.getSnsClient();
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

        DynamoDBQueryExpression<Student> spec = new DynamoDBQueryExpression<Student>();
        spec.setHashKeyValues(student);
        spec.setIndexName("idx_studentId");
        spec.setConsistentRead(false);
        List<Student> list = mapper.query(Student.class, spec);

        if (list.size() != 0) {
            Student stud = list.get(0);
            stud.setCourseList(CourseHelper.parseRelationToStudent(stud.getStudentId()));
            return list.get(0);
        }
        return null;
    }

    public Student addStudent(Student student) {
        if (student.getStudentId() == null)
            return null;
        if (getStudent(student.getStudentId()) != null)
            return null;
        student.setRegisterDate(new Date());
//        List<String> registeredCourse = new ArrayList<>();
//        if (student.getCourseList().size() != 0) {
//            for (String courseId : student.getCourseList()) {
//                if (getCourse(courseId) == null)
//                    continue;
//                Course course = getCourse(courseId);
//                if (isExist(course, student.getStudentId()))
//                    continue;
//                course.getRegisteredStudentList().add(student.getStudentId());
//                mapper.save(course);
//                registeredCourse.add(courseId);
//            }
//        }
//        student.setCourseList(registeredCourse);
        mapper.save(student);
        student.setCourseList(CourseHelper.parseRelationToStudent(student.getStudentId()));
        return student;
    }


    public Student deleteStudent(String id) {
        Student student = getStudent(id);
        if (student != null) {
            student.setCourseList(CourseHelper.parseRelationToStudent(student.getStudentId()));
            //delete course register information
            List<CourseRegisteredStudent> registered = CourseHelper.query(null, id);
            for (CourseRegisteredStudent i : registered) {
                mapper.delete(i);
            }

            //delete subscription information
            StudentSubscription studentSubscription = mapper.load(StudentSubscription.class, student.getStudentId());
            Map<String, String> subscriptionMap = studentSubscription.getSubscriptionMap();
            for (String arn : subscriptionMap.values()) {
                snsClient.unsubscribe(arn);
            }
            mapper.delete(studentSubscription);

            //delete TA information in course
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<Course> courseList = mapper.scan(Course.class, scanExpression);
            for (Course course : courseList) {
                if (id.equals(course.getTAId())) {
                    course.setTAId("");
                    mapper.save(course);
                }
            }

            //delete student
            mapper.delete(student);
            return student;
        }
        return null;
    }

    public Student editStudent(String id, Student student) {
        Student stu = getStudent(id);
        if (stu != null) {
            //replace previous information
            if (student.getDepartment() != null)
                stu.setDepartment(student.getDepartment());
            if (student.getFirstName() != null)
                stu.setFirstName(student.getFirstName());
            if (student.getLastName() != null)
                stu.setLastName(student.getLastName());

            //if contains new email information
            if (student.getEmail() != null && !student.getEmail().equals(stu.getEmail())) {
                List<CourseRegisteredStudent> registered = CourseHelper.query(null, student.getStudentId());

                //remove and unsubscribe previous subscription information
                StudentSubscription studentSubscription = mapper.load(StudentSubscription.class, stu.getStudentId());

                if (studentSubscription != null) {
                    for (String subscription : studentSubscription.getSubscriptionMap().values()) {
                        snsClient.unsubscribe(subscription);
                    }
                }
                //update and subscribe new subscription information
                Map<String, String> newSubscriptionMap = new HashMap<>();
                for (CourseRegisteredStudent i : registered) {
                    Course course = CourseService.getCourse(i.getCourseId());
                    SubscribeResult subscribeResult = snsClient.subscribe(new SubscribeRequest(course.getTopicArn(), "email", student.getEmail()).withReturnSubscriptionArn(true));
                    newSubscriptionMap.put(i.getCourseId(), subscribeResult.getSubscriptionArn());
                }
                studentSubscription.setSubscriptionMap(newSubscriptionMap);
                mapper.save(studentSubscription);
                stu.setEmail(student.getEmail());
            }
            if (student.getCourseList().size() <= 3 && student.getCourseList().size() > 0) {
                List<CourseRegisteredStudent> courseRegisteredStudent = CourseHelper.query(null, id);

                StudentSubscription studentSubscription = mapper.load(StudentSubscription.class, id);
                Map<String, String> subscriptionMap = studentSubscription.getSubscriptionMap();

                HashSet<String> courseSet = new HashSet<>(student.getCourseList());
                //add new course and subscription
                for (String courseId : courseSet) {
                    if (subscriptionMap.containsKey(courseId)) {
                        subscriptionMap.remove(courseId);
                        continue;
                    }
                    registerCourse(id, courseId);
                }
                //delete old course and subscription
                for (String courseId : subscriptionMap.keySet()) {
                    dropCourse(id, courseId);
                }
            }
            mapper.save(stu);
            stu.setCourseList(CourseHelper.parseRelationToStudent(student.getStudentId()));
            return stu;
        }
        return null;
    }

    public List<Student> getAllStudent() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Student> result = mapper.scan(Student.class, scanExpression);
        for (Student i : result) {
            i.setCourseList(CourseHelper.parseRelationToStudent(i.getStudentId()));
        }
        return result;
    }

    public List<Student> getStudentByDepartment(String department) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(department));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("department = :val1").withExpressionAttributeValues(eav);
        List<Student> result = mapper.scan(Student.class, scanExpression);
        for (Student i : result) {
            i.setCourseList(CourseHelper.parseRelationToStudent(i.getStudentId()));
        }
        return result;
    }


    public Student registerCourse(String id, String courseId) {
        Student student = getStudent(id);
        Course course = getCourse(courseId);
        if (student == null || course == null)
            return null;
        if (student.getCourseList().size() > 3)
            return null;

        List<CourseRegisteredStudent> result = CourseHelper.query(courseId, id);
        if (result.size() == 0) {
            mapper.save(new CourseRegisteredStudent(courseId, id));
        }
        if (student.getEmail() != null) {
            SubscribeRequest subscribeRequest = new SubscribeRequest(course.getTopicArn(), "email", student.getEmail());
            SubscribeResult subscribeResult = snsClient.subscribe(subscribeRequest.withReturnSubscriptionArn(true));

            StudentSubscription studentSubscription = mapper.load(StudentSubscription.class, student.getStudentId());
            studentSubscription.getSubscriptionMap().put(courseId, subscribeResult.getSubscriptionArn());
            mapper.save(studentSubscription);
        }
        mapper.save(student);
        student.setCourseList(CourseHelper.parseRelationToStudent(student.getStudentId()));
        return student;
    }

    public Student dropCourse(String id, String courseId) {
        Student student = getStudent(id);
        Course course = getCourse(courseId);
        if (student == null || course == null)
            return null;
        List<CourseRegisteredStudent> result = CourseHelper.query(courseId, id);
        if (result.size() != 0) {
            for (CourseRegisteredStudent i : result) {
                mapper.delete(i);

                StudentSubscription studentSubscription = mapper.load(StudentSubscription.class, id);
                snsClient.unsubscribe(studentSubscription.getSubscriptionMap().get(i.getCourseId()));
                studentSubscription.getSubscriptionMap().remove(i.getCourseId());

                mapper.save(studentSubscription);
            }
        }


        student.setCourseList(CourseHelper.parseRelationToStudent(student.getStudentId()));
        return student;
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
