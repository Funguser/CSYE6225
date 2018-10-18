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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public List<Material> getAssociatedMaterial() {
        return associatedMaterial;
    }

    public void setAssociatedMaterial(List<Material> associatedMaterial) {
        this.associatedMaterial = associatedMaterial;
    }

}
