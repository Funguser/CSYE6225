package com.csye6225.lab.ziyao.people.service;

import com.csye6225.lab.ziyao.resource.InMemoryDatabase;
import com.csye6225.lab.ziyao.people.DAO.Professor;

import java.util.*;

public class ProfessorService {
    HashMap<Integer, Professor> profMap = InMemoryDatabase.getInstance().getProfesorDB();

    public List<Professor> getAllProfessors() {
        return new ArrayList<Professor>(profMap.values());
    }

    public Professor getProfessor(int profId) {
        if (!profMap.containsKey(profId))
            return null;
        return profMap.get(profId);
    }

    public Professor addProfessor(Professor prof) {
        int nextAvailableId = profMap.size() + 1;
        prof.setId(nextAvailableId);
        profMap.put(nextAvailableId, prof);
        return profMap.get(nextAvailableId);
    }

    public Professor deleteProfessor(int profId) {
        if (!profMap.containsKey(profId))
            return null;
        Professor deletedProfDetails = profMap.get(profId);
        profMap.remove(profId);
        return deletedProfDetails;
    }

    public List<Professor> getProfessorByDepartment(String department) {
        ArrayList<Professor> profList = new ArrayList<>();
        for (Professor prof : profMap.values()){
            if (prof.getDepartment().equals(department))
                profList.add(prof);
        }
        return profList;
    }

    public Professor updateProfessorInformation(int profId, Professor prof) {
        if (!profMap.containsKey(profId))
            return null;
        Professor oldProf = profMap.get(profId);
        profId = oldProf.getId();

        profMap.put(profId, prof);
        return prof;
    }
}
