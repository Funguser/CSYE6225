package com.csye6225.lab.ziyao.program.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.lab.ziyao.DynamoDB.DynamoDBConnector;
import com.csye6225.lab.ziyao.people.DAO.Professor;
import com.csye6225.lab.ziyao.program.DAO.Course;
//import com.csye6225.lab.ziyao.resource.InMemoryDatabase;

import java.util.*;

public class CourseService {
    static DynamoDBConnector dynamoDB;
    DynamoDBMapper mapper;


    public CourseService() throws Exception {
        dynamoDB = new DynamoDBConnector();
        dynamoDB.init();
        mapper = new DynamoDBMapper(dynamoDB.getConnector());
    }

//    public CourseService(String programName) {
//        programName = programName.replaceAll("\\s*", "");
//        inMemoryDatabase = InMemoryDatabase.getInstance();
//        courseDB = inMemoryDatabase.getProgramDB().get(programName).getCourseList();
//    }


    public List<Course> getAllCourses() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Course> result = mapper.scan(Course.class, scanExpression);
        return result;
    }

    public Course getCourse(int courseId) {
        Course course = new Course();
        course.setCourseId(courseId);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put("v_id", new AttributeValue().withN(String.valueOf(courseId)));
        DynamoDBQueryExpression<Course> spec = new DynamoDBQueryExpression<Course>();
        spec.setHashKeyValues(course);
        spec.setIndexName("idx_courseId");
        spec.setConsistentRead(false);
        List<Course> list = mapper.query(Course.class, spec);

        if (list.size() != 0)
            return list.get(0);
        return null;
    }

    public Course addCourse(Course course) {
        if (course.getCourseId() == 0)
            return null;
        if (getCourse(course.getCourseId()) != null)
            return null;
        mapper.save(course);
        return course;
    }

    public Course editCourse(int courseId, Course course) {
        Course cour = getCourse(courseId);
        if (cour != null) {
            cour.setCourseName(course.getCourseName());
            cour.setBoardId(course.getBoardId());
            cour.setDepartment(course.getDepartment());
            cour.setProfessorId(course.getProfessorId());
            cour.setTAId(course.getTAId());
            mapper.save(cour);
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

    public Course deleteCourse(int courseId) {
        Course course = getCourse(courseId);
        if (course != null) {
            mapper.delete(course);
        }
        return course;
    }

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
