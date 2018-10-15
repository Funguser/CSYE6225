package com.csye6225.lab.ziyao.people.service;

import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.resource.InMemoryDatabase;

import java.util.HashMap;

public class StudentService {
    InMemoryDatabase database;
    HashMap<Integer, Student> allStudent;


    public StudentService(){
        database = InMemoryDatabase.getInstance();
        allStudent = database.getStudentDB();
    }

    public Student getStudent(int id) {
        if (allStudent.keySet().contains(id))
            return allStudent.get(id);
        return null;
    }

    public Student addStudent(Student student){
        student.setId(database.getStudentId());
        allStudent.put(student.getId(), student);
        database.setStudentId(database.getStudentId() + 1);
        return student;
    }

    public Student deleteStudent(int id) {
        if (!allStudent.keySet().contains(id))
            return null;
        Student student = allStudent.get(id);
        allStudent.remove(id);
        return student;
    }

    public Student editStudent(int id, Student student) {
        if (!allStudent.keySet().contains(id))
            return null;
        allStudent.put(id, student);
        return student;
    }
}
