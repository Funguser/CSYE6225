package com.csye6225.lab.ziyao.resource;

import com.csye6225.lab.ziyao.people.DAO.Professor;
import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.program.DAO.Course;
import com.csye6225.lab.ziyao.program.DAO.Program;
import com.csye6225.lab.ziyao.program.service.ProgramService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class InMemoryDatabase {
    private HashMap<Integer, Professor> profesorDB = new HashMap<>();
    private HashMap<Integer, Student> studentDB = new HashMap<>();
    private HashMap<String, Program> programDB = new HashMap<>();
    private HashMap<String, Course> courseDB = new HashMap<>();


    private int professorId = 1;
    private int studentId = 1;

    static InMemoryDatabase instance = null;


    public static InMemoryDatabase getInstance() {
        if (instance == null)
            instance = new InMemoryDatabase();
        return instance;
    }

    private InMemoryDatabase(){
        Program program = new Program("Information System");
        Course course1 = new Course("course1");
        Course course2 = new Course("course2");
        program.getCourseList().put("course1", course1);
        program.getCourseList().put("course2", course2);
        programDB.put("InformationSystem", program);

        program = new Program("Computer Science");
        program.getCourseList().put("course1", course1);
        Course course3 = new Course("course3");
        program.getCourseList().put("course3", course3);
        programDB.put("ComputerScience", program);

        courseDB.put(course1.getCourseName(), course1);
        courseDB.put(course2.getCourseName(), course2);
        courseDB.put(course3.getCourseName(), course3);

        Professor professor = new Professor(professorId, "Tom", "Tom", "InfoSystems", new Date());
        profesorDB.put(professorId++, professor);
        professor = new Professor(professorId, "Cat", "Cat", "InfoSystems", new Date());
        profesorDB.put(professorId++, professor);

        Student student = new Student("A", "AA", new Date(), studentId, programDB.get("Computer Science"));
        studentDB.put(studentId++, student);
        student = new Student("B", "BB", new Date(), studentId, programDB.get("Information System"));
        studentDB.put(studentId++, student);

    }

    public HashMap<Integer, Professor> getProfesorDB() {
        return profesorDB;
    }

    public HashMap<Integer, Student> getStudentDB() {
        return studentDB;
    }

    public HashMap<String, Program> getProgramDB() {
        return programDB;
    }

    public HashMap<String, Course> getCourseDB() {
        return courseDB;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

}
