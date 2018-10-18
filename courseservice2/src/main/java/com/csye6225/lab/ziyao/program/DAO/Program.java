package com.csye6225.lab.ziyao.program.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {
    String name;
    HashMap<String, Course> courseList;

    public Program(){
        courseList = new HashMap<>();
    }

    public Program(String name) {
        this.name = name;
        courseList = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(HashMap<String, Course> courseList) {
        this.courseList = courseList;
    }


}
