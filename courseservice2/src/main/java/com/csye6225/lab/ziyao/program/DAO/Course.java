package com.csye6225.lab.ziyao.program.DAO;

import com.csye6225.lab.ziyao.people.DAO.Professor;
import com.csye6225.lab.ziyao.people.DAO.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Course {
    String courseName;
    List<Lecture> lectureList;
    Board board;
    List<Student> studentList;
    Roster roster;
    Professor professor;
    List<Student> TA;

    public Course(String courseName){
        this.courseName = courseName;
        lectureList = new ArrayList<>();
        board = new Board();
        studentList = new ArrayList<>();
        roster = new Roster();
        professor = new Professor();
        TA = new ArrayList<>();
    }
}
