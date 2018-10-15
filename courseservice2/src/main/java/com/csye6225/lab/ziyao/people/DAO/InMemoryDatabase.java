package com.csye6225.lab.ziyao.people.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class InMemoryDatabase {
    private static HashMap<Long, Professor> profesorDB = new HashMap<>();

    public static HashMap<Long, Professor> getProfesorDB() {
        Professor prof1 = new Professor(1l, "Tom", "InfoSystems", new Date());
        Professor prof2 = new Professor(2l, "Cat", "InfoSystems", new Date());
        ArrayList<Professor> professorList = new ArrayList<>();
        professorList.add(prof1);
        professorList.add(prof2);

        return profesorDB;
    }
}
