package com.csye6225.lab.ziyao.resource;

import com.csye6225.lab.ziyao.people.DAO.Professor;
import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.program.DAO.Program;
import com.csye6225.lab.ziyao.program.service.ProgramService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class InMemoryDatabase {
    private HashMap<Integer, Professor> profesorDB = new HashMap<>();
    private HashMap<Integer, Student> studentDB = new HashMap<>();
    private List<Program> programList = new ArrayList<>();

    private int professorId = 1;
    private int studentId = 1;

    static InMemoryDatabase instance = null;


    public static InMemoryDatabase getInstance() {
        if (instance == null)
            instance = new InMemoryDatabase();
        return instance;
    }

    private InMemoryDatabase(){
        programList.add(new Program("Information System"));
        programList.add(new Program("Computer Science"));

        Professor professor = new Professor(professorId, "Tom", "Tom", "InfoSystems", new Date());
        profesorDB.put(professorId++, professor);
        professor = new Professor(professorId, "Cat", "Cat", "InfoSystems", new Date());
        profesorDB.put(professorId++, professor);

        Student student = new Student("A", "AA", new Date(), studentId, programList.get(0));
        studentDB.put(studentId++, student);
        student = new Student("B", "BB", new Date(), studentId, programList.get(1));
        studentDB.put(studentId++, student);

    }

    public HashMap<Integer, Professor> getProfesorDB() {
        return profesorDB;
    }

    public HashMap<Integer, Student> getStudentDB() {
        return studentDB;
    }

    public List<Program> getProgramList() {
        return programList;
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
