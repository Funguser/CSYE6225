package com.csye6225.lab.ziyao.people.resources;

import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.people.service.StudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("students")
public class StudentResource {
    StudentService studentService = new StudentService();
    @GET
    @Path("/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent(@PathParam("studentId") int id) {
        return studentService.getStudent(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Student addStudent(Student student) {
        return studentService.addStudent(student);
    }

    @DELETE
    @Path("/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student deleteStudent(@PathParam("studentId") int id){
        return studentService.deleteStudent(id);
    }

    @PUT
    @Path("/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Student editStudent(@PathParam("studentId") int id, Student student) {
        return studentService.editStudent(id, student);
    }
}

