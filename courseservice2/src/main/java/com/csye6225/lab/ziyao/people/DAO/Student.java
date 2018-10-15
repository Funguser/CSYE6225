package com.csye6225.lab.ziyao.people.DAO;

import com.csye6225.lab.ziyao.program.DAO.Course;
import com.csye6225.lab.ziyao.program.DAO.Program;
import com.csye6225.lab.ziyao.resource.Image;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student extends People {
    Image image;
    List<Course> courseList;
    Program program;

    public Student(String firstName, String lastName, Date registerDate, long id, Program program) {
        super(firstName, lastName, registerDate, id);
        this.image = new Image();
        this.courseList = new ArrayList<>();
        this.program = program;
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
