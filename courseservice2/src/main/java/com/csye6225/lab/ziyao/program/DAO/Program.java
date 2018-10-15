package com.csye6225.lab.ziyao.program.DAO;

import java.util.ArrayList;
import java.util.List;

public class Program {
    String name;
    List<Course> courseList;

    public Program(){
        courseList = new ArrayList<>();
    }

    public Program(String name) {
        this.name = name;
    }
}
