package com.csye6225.lab.ziyao.program.service;

import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.program.DAO.Program;
import com.csye6225.lab.ziyao.resource.InMemoryDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramService {
    InMemoryDatabase database;
    HashMap<String, Program> programDB;


    public ProgramService(){
        database = InMemoryDatabase.getInstance();
        programDB = database.getProgramDB();
    }

    public CourseService getCorrCourseService(String programName) {
        if (getProgram(programName) == null)
            return new CourseService();
        return new CourseService(programName);
    }

    public List<Program> getAllProgram() {
        return new ArrayList<>(programDB.values());
    }

    public Program getProgram(String id) {
        String name = id.replaceAll("\\s*", "");
        if (!programDB.containsKey(name))
            return null;
        return programDB.get(name);
    }

    public Program addProgram(Program program) {
        String name = program.getName().replaceAll("\\s*", "");
        if (programDB.containsKey(name))
            return null;
        programDB.put(name, program);
        return program;
    }

    public Program deleteProgram(String id) {
        String name = id.replaceAll("\\s*", "");
        if (!programDB.containsKey(name))
            return null;
        Program program = programDB.get(name);
        programDB.remove(name);
        return program;
    }

    public Program editProgram(String id, Program program) {
        String name = id.replaceAll("\\s*", "");
        if (!programDB.containsKey(name))
            return null;
        programDB.put(name, program);
        return program;
    }
}
