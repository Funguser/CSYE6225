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
    List<Integer> studentList;
    Roster roster;
    Professor professor;
    Student TA;

    public Course(String courseName){
        this.courseName = courseName;
        lectureList = new ArrayList<>();
        board = new Board();
        studentList = new ArrayList<>();
        roster = new Roster();
        professor = new Professor();
        TA = new Student();
    }
    public Course() {
        lectureList = new ArrayList<>();
        board = new Board();
        studentList = new ArrayList<>();
        roster = new Roster();
        professor = new Professor();
        TA = new Student();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Lecture> getLectureList() {
        return lectureList;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Integer> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Integer> studentList) {
        this.studentList = studentList;
    }

    public Roster getRoster() {
        return roster;
    }

    public void setRoster(Roster roster) {
        this.roster = roster;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Student getTA() {
        return TA;
    }

    public void setTA(Student student) {
        this.TA = student;
    }

    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }

}
