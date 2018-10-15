package com.csye6225.lab.ziyao.program.DAO;

import com.csye6225.lab.ziyao.resource.Material;

import java.util.ArrayList;
import java.util.List;

public class Lecture {
    String name;
    List<String> notes;
    List<Material> associatedMaterial;

    public Lecture() {
        notes = new ArrayList<>();
        associatedMaterial = new ArrayList<>();
    }

    public Lecture(String name) {
        this.name = name;
        notes = new ArrayList<>();
        associatedMaterial = new ArrayList<>();
    }
}
