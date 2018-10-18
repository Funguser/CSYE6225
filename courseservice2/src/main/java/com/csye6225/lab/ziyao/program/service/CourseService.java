package com.csye6225.lab.ziyao.program.service;

import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.program.DAO.Course;
import com.csye6225.lab.ziyao.program.DAO.Lecture;
import com.csye6225.lab.ziyao.resource.InMemoryDatabase;

import java.util.*;

public class CourseService {
    InMemoryDatabase inMemoryDatabase;
    Map<String, Course> courseDB;

    public CourseService(String programName) {
        programName = programName.replaceAll("\\s*", "");
        inMemoryDatabase = InMemoryDatabase.getInstance();
        courseDB = inMemoryDatabase.getProgramDB().get(programName).getCourseList();
    }

    public CourseService() {
        inMemoryDatabase = InMemoryDatabase.getInstance();
        courseDB = new HashMap<>();
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courseDB.values());
    }

    public Course getCourse(String courseName) {
        if (!courseDB.containsKey(courseName))
            return null;
        return courseDB.get(courseName);
    }

    public Course addCourse(Course course) {
        courseDB.put(course.getCourseName(), course);
        return course;
    }

    public Course editCourse(String courseName, Integer professorId, Integer TAId, List<Integer> studentIdList) {
        if (!courseDB.containsKey(courseName))
            return null;
        Course course = courseDB.get(courseName);
        if (professorId != null && inMemoryDatabase.getProfesorDB().containsKey(professorId))
            course.setProfessor(inMemoryDatabase.getProfesorDB().get(professorId));
        if (TAId != null && inMemoryDatabase.getStudentDB().containsKey(TAId))
            course.setTA(inMemoryDatabase.getStudentDB().get(TAId));
        boolean isValidStudentList = true;

        for (Integer i : studentIdList) {
            if (!inMemoryDatabase.getStudentDB().containsKey(i)) {
                isValidStudentList = false;
                break;
            }
        }
        if (isValidStudentList) {
            removeStudentCourse(course);
            course.setStudentList(studentIdList);
        }
        return course;
    }

    public Course deleteCourse(String courseName) {
        if (!courseDB.containsKey(courseName))
            return null;
        Course course = courseDB.get(courseName);
        removeStudentCourse(course);
        courseDB.remove(courseName);
        return course;
    }


    public List<Lecture> getAllLectures(String courseName) {
        return courseDB.get(courseName).getLectureList();
    }

    public Lecture getLecture(String courseName, Integer lectureId) {
        if (courseDB.get(courseName) == null)
            return null;
        Lecture lecture = courseDB.get(courseName).getLectureList().get(lectureId);
        return lecture;
    }

    public void removeStudentCourse(Course course) {
        for (Integer i : course.getStudentList()) {
            Student student = inMemoryDatabase.getStudentDB().get(i);
            Iterator iter = student.getCourseList().iterator();
            while (iter.hasNext()) {
                Course tmp = (Course)iter.next();
                if (tmp.getCourseName().equals(course.getCourseName()))
                    iter.remove();
            }
        }
    }
}
