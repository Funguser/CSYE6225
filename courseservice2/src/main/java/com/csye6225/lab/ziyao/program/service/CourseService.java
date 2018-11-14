package com.csye6225.lab.ziyao.program.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.lab.ziyao.DynamoDB.DynamoDBConnector;
import com.csye6225.lab.ziyao.people.DAO.Professor;
import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.people.service.ProfessorService;
import com.csye6225.lab.ziyao.people.service.StudentService;
import com.csye6225.lab.ziyao.program.DAO.Course;
//import com.csye6225.lab.ziyao.resource.InMemoryDatabase;

import java.util.*;

public class CourseService {
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

    public static Course getCourse(String courseId) {
        if (courseId == null)
            return null;
        Course course = new Course();
        course.setCourseId(courseId);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put("v_id", new AttributeValue().withS(courseId));
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
        if (course.getCourseId() == null)
            return null;
        if (getCourse(course.getCourseId()) != null)
            return null;
        if (course.getProfessorId() != null && ProfessorService.getProfessor(course.getProfessorId()) == null)
            return null;
        if (course.getTAId() != null && StudentService.getStudent(course.getTAId()) == null)
            return null;

        if (course.getRegisteredStudentList().size() != 0) {
            for (String studentId : course.getRegisteredStudentList()) {
                Student student = StudentService.getStudent(studentId);
                if (student == null)
                    continue;
                if (isExist(student, course.getCourseId()))
                    continue;
                student.getCourseList().add(course.getCourseId());
                mapper.save(student);

            }
        }

        mapper.save(course);
        return course;
    }

    public boolean isExist(Student student, String courseId) {
        for (String id : student.getCourseList()) {
            if (id.equals(courseId))
                return true;
        }
        return false;
    }

    public Course deleteCourse(String courseId) {
        Course course = getCourse(courseId);
        if (course != null) {
            for (String id : course.getRegisteredStudentList()) {
                Student student = StudentService.getStudent(id);
                if (student == null)
                    continue;
                if (isExist(student, courseId)) {
                    student.getCourseList().remove(courseId);
                    mapper.save(student);
                }
            }

            mapper.delete(course);
        }
        return course;
    }

    public Course editCourse(String courseId, Course course) {
        Course cour = getCourse(courseId);
        if (cour != null) {
            deleteCourse(courseId);
            cour.setCourseName(course.getCourseName());
            cour.setBoardId(course.getBoardId());
            cour.setDepartment(course.getDepartment());
            cour.setProfessorId(course.getProfessorId());
            cour.setTAId(course.getTAId());
            cour.setRegisteredStudentList(course.getRegisteredStudentList());
            addCourse(cour);
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
