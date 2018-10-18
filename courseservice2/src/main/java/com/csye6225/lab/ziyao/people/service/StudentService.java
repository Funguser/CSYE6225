package com.csye6225.lab.ziyao.people.service;

import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.program.DAO.Course;
import com.csye6225.lab.ziyao.resource.InMemoryDatabase;

import java.util.*;

public class StudentService {
    InMemoryDatabase database;
    HashMap<Integer, Student> allStudent;


    public StudentService(){
        database = InMemoryDatabase.getInstance();
        allStudent = database.getStudentDB();
    }

    public Student getStudent(int id) {
        if (!allStudent.containsKey(id))
            return null;
        return allStudent.get(id);
    }

    public Student addStudent(Student student){
        student.setId(database.getStudentId());
        student.setRegisterDate(new Date());
        allStudent.put(student.getId(), student);
        database.setStudentId(database.getStudentId() + 1);
        return student;
    }

    public Student deleteStudent(int id) {
        if (!allStudent.containsKey(id))
            return null;
        Student student = allStudent.get(id);
        allStudent.remove(id);
        return student;
    }

    public Student editStudent(int id, Student student) {
        if (!allStudent.containsKey(id))
            return null;
        allStudent.put(id, student);
        return student;
    }

    public List<Student> getAllStudent() {
        return new ArrayList<>(allStudent.values());
    }

    public List<Course> getCourseList(int id) {
        if (!allStudent.containsKey(id))
            return null;
        return allStudent.get(id).getCourseList();
    }

    public List<Course> registerCourse(int id, String courseName) {
        if (!allStudent.containsKey(id))
            return null;
        if (!database.getCourseDB().containsKey(courseName))
            return null;
        List<Course> courseList = allStudent.get(id).getCourseList();
        for (Course course : courseList) {
            if (course != null && course.getCourseName().equals(courseName))
                return courseList;
        }
        courseList.add(database.getCourseDB().get(courseName));
        database.getCourseDB().get(courseName).getStudentList().add(id);
        return courseList;
    }

    public List<Course> dropCourse(int id, String courseName) {
        if (!allStudent.containsKey(id))
            return null;
        if (!database.getCourseDB().containsKey(courseName))
            return null;
        List<Course> courseList = allStudent.get(id).getCourseList();
        Iterator iter = courseList.iterator();
        while (iter.hasNext()) {
            Course tmp = (Course)iter.next();
            if (tmp.getCourseName().equals(courseName))
                iter.remove();
        }
        List<Integer> courseEnroll = database.getCourseDB().get(courseName).getStudentList();
        iter = courseEnroll.iterator();
        while (iter.hasNext()) {
            int tmp = (int)iter.next();
            if (tmp == id)
                iter.remove();
        }
        return courseList;
    }
}
