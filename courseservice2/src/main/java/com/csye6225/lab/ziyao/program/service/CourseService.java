package com.csye6225.lab.ziyao.program.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.DeleteTopicRequest;
import com.csye6225.lab.ziyao.AWSService.DynamoDBConnector;
import com.csye6225.lab.ziyao.AWSService.SNSService;
import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.program.DAO.Course;
import com.csye6225.lab.ziyao.program.DAO.CourseRegisteredStudent;
import com.csye6225.lab.ziyao.program.helper.CourseHelper;
//import com.csye6225.lab.ziyao.resource.InMemoryDatabase;

import java.util.*;

import static com.csye6225.lab.ziyao.people.service.ProfessorService.getProfessor;
import static com.csye6225.lab.ziyao.people.service.StudentService.getStudent;

public class CourseService {
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

    public CourseService() throws Exception {
        dynamoDB = new DynamoDBConnector();
        dynamoDB.init();
        mapper = new DynamoDBMapper(dynamoDB.getConnector());
        snsClient = SNSService.getSnsClient();
    }


    public List<Course> getAllCourses() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Course> result = mapper.scan(Course.class, scanExpression);
        for (Course course : result) {
            course.setRegisteredStudentList(CourseHelper.parseRelationToCourse(course.getCourseId()));
        }
        return result;
    }

    public static Course getCourse(String courseId) {
        if (courseId == null)
            return null;
        Course course = new Course();
        course.setCourseId(courseId);

        DynamoDBQueryExpression<Course> spec = new DynamoDBQueryExpression<Course>();
        spec.setHashKeyValues(course);
        spec.setIndexName("idx_courseId");
        spec.setConsistentRead(false);
        List<Course> list = mapper.query(Course.class, spec);

        if (list.size() != 0) {
            Course result = list.get(0);
            result.setRegisteredStudentList(CourseHelper.parseRelationToCourse(result.getCourseId()));
            return result;
        }
        return null;
    }

    public Course addCourse(Course course) {
        if (course.getCourseId() == null)
            return null;
        if (getCourse(course.getCourseId()) != null)
            return null;
        if (course.getProfessorId() != null && getProfessor(course.getProfessorId()) == null)
            return null;
        if (course.getDepartment() == null || course.getDepartment().isEmpty())
            return null;
        if (course.getTAId() != null && getStudent(course.getTAId()) == null)
            return null;

        CreateTopicRequest createTopicRequest = new CreateTopicRequest(course.getCourseId() + course.getCourseName());
        CreateTopicResult createTopicResult = snsClient.createTopic(createTopicRequest);

        String topicArn = createTopicResult.getTopicArn();

        course.setTopicArn(topicArn);

//        if (course.getRegisteredStudentList().size() != 0) {
//            List<CourseRegisteredStudent> query = CourseHelper.queryByCourseId(course.getCourseId());
//            for (String studentId : course.getRegisteredStudentList()) {
//
//                Student student = StudentService.getStudent(studentId);
//                if (student == null)
//                    continue;
//                boolean isFound = false;
//                for (CourseRegisteredStudent i : query) {
//                    if (studentId.equals(i.getStudentId())) {
//                        isFound = true;
//                        break;
//                    }
//                }
//                if (isFound)
//                    continue;
//                mapper.save(new CourseRegisteredStudent(course.getCourseId(), studentId));
//            }
//        }

        mapper.save(course);
        course.setRegisteredStudentList(CourseHelper.parseRelationToCourse(course.getCourseId()));
        return course;
    }


    public Course deleteCourse(String courseId) {
        Course course = getCourse(courseId);
        if (course != null) {
//            for (String id : course.getRegisteredStudentList()) {
//                Student student = getStudent(id);
//                if (student == null)
//                    continue;
//                List<CourseRegisteredStudent> queryResult = CourseHelper.queryByBoth(student.getStudentId(), courseId);
//                for (CourseRegisteredStudent i : queryResult) {
//                    mapper.delete(i);
//                }
//            }
            List<CourseRegisteredStudent> result = CourseHelper.query(courseId, null);
            for (CourseRegisteredStudent i : result) {
                mapper.delete(i);
            }
            DeleteTopicRequest deleteTopicRequest = new DeleteTopicRequest(course.getTopicArn());
            snsClient.deleteTopic(deleteTopicRequest);
            mapper.delete(course);
            course.setRegisteredStudentList(CourseHelper.parseRelationToCourse(course.getCourseId()));
        }
        return course;
    }

    public Course editCourse(String courseId, Course course) {
        Course cour = getCourse(courseId);
        if (cour != null) {
            if (course.getCourseName() != null)
                cour.setCourseName(course.getCourseName());
            if (course.getBoardId() != null)
                cour.setBoardId(course.getBoardId());
            if (course.getDepartment() != null)
                cour.setDepartment(course.getDepartment());
            if (course.getProfessorId() != null && getProfessor(course.getProfessorId()) != null) {
                cour.setProfessorId(course.getProfessorId());
            }
            if (course.getTAId() != null && getStudent(course.getTAId()) != null) {
                cour.setTAId(course.getTAId());
            }
            cour.setRegisteredStudentList(CourseHelper.parseRelationToCourse(cour.getCourseId()));
            mapper.save(cour);
//            List<String> studentListTmp = course.getRegisteredStudentList();
//            List<CourseRegisteredStudent> queryResult = CourseHelper.queryByCourseId(courseId);
//
//            for (CourseRegisteredStudent i : queryResult) {
//                if (course.getRegisteredStudentList().contains(i.getStudentId())){
//                    studentListTmp.remove(i.getStudentId());
//                    continue;
//                }
//                mapper.delete(i);
//            }
//            for (String id : studentListTmp) {
//                CourseRegisteredStudent tmp = new CourseRegisteredStudent(courseId, id);
//                mapper.save(tmp);
//            }
//
//            cour.setRegisteredStudentList(course.getRegisteredStudentList());
        }
        return cour;
    }
//    public Course editCourse(String courseName, Integer professorId, Integer TAId, List<Integer> courseIdList) {
//        if (!courseDB.containsKey(courseName))
//            return null;
//        Course course = courseDB.get(courseName);
//        if (professorId != null && inMemoryDatabase.getProfesorDB().containsKey(professorId))
//            course.setProfessor(inMemoryDatabase.getProfesorDB().get(professorId));
//        if (TAId != null && inMemoryDatabase.getCourseDB().containsKey(TAId))
//            course.setTA(inMemoryDatabase.getCourseDB().get(TAId));
//        boolean isValidCourseList = true;
//
//        for (Integer i : courseIdList) {
//            if (!inMemoryDatabase.getCourseDB().containsKey(i)) {
//                isValidCourseList = false;
//                break;
//            }
//        }
//        if (isValidCourseList) {
//            removeCourseCourse(course);
//            course.setCourseList(courseIdList);
//        }
//        return course;
//    }


//
//    public List<Lecture> getAllLectures(String courseName) {
//        return courseDB.get(courseName).getLectureList();
//    }
//
//    public Lecture getLecture(String courseName, Integer lectureId) {
//        if (courseDB.get(courseName) == null)
//            return null;
//        Lecture lecture = courseDB.get(courseName).getLectureList().get(lectureId);
//        return lecture;
//    }
//
//    public void removeCourseCourse(Course course) {
//        for (Integer i : course.getCourseList()) {
//            Course course = inMemoryDatabase.getCourseDB().get(i);
//            Iterator iter = course.getCourseList().iterator();
//            while (iter.hasNext()) {
//                Course tmp = (Course)iter.next();
//                if (tmp.getCourseName().equals(course.getCourseName()))
//                    iter.remove();
//            }
//        }
//    }
}
