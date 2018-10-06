package com.csye6225.lab.ziyao.service;

import com.csye6225.lab.ziyao.datamodel.InMemoryDatabase;
import com.csye6225.lab.ziyao.datamodel.Professor;

import javax.ws.rs.Produces;
import java.util.*;

public class ProfessorService {
    HashMap<Long, Professor> profMap = InMemoryDatabase.getProfesorDB();

    public List<Professor> getAllProfessors() {
        Professor prof1 = new Professor(1l, "Tom", "InfoSystems", new Date());
        Professor prof2 = new Professor(2l, "Cat", "InfoSystems", new Date());

        profMap.put(1l, prof1);
        profMap.put(2l, prof2);

        return new ArrayList<Professor>(profMap.values());
    }

    public Professor getProfessor(Long profId) {
        return profMap.get(profId);
    }

    public Professor addProfessor(Professor prof) {
        long nextAvailableId = profMap.size() + 1;
        prof.setProfessorId(nextAvailableId);
        profMap.put(nextAvailableId, prof);
        return profMap.get(nextAvailableId);
    }

    public void addProfessor(String name, String department, Date joiningDate) {
        long nextAvailableId = profMap.size() + 1;
        Professor prof = new Professor(nextAvailableId, name, department, joiningDate);
        profMap.put(nextAvailableId, prof);
    }


    public Professor deleteProfessor(Long profId) {
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

    public Professor updateProfessorInformation(Long profId, Professor prof) {
        Professor oldProf = profMap.get(profId);
        profId = oldProf.getProfessorId();

        profMap.put(profId, prof);
        return prof;
    }
}
