package com.csye6225.lab.ziyao.people.resources;

import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.people.service.StudentService;
import com.csye6225.lab.ziyao.program.DAO.Course;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("students")
public class StudentResource {
    StudentService studentService = new StudentService();

    public StudentResource() throws Exception {
    }

    @GET
    @Path("/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent(@PathParam("studentId") String id) {
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
    public Student deleteStudent(@PathParam("studentId") String id){
        return studentService.deleteStudent(id);
    }

    @PUT
    @Path("/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Student editStudent(@PathParam("studentId") String id, Student student) {
        return studentService.editStudent(id, student);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getAllStudent(@QueryParam("department") String department) {
        if (department == null)
            return studentService.getAllStudent();
        return studentService.getStudentByDepartment(department);
    }

    @POST
    @Path("/{studentId}/register/{courseId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Student registerCourse(@PathParam("studentId") String id, @PathParam("courseId") String courseId) {
        if (id == null || courseId == null)
            return null;
        return studentService.registerCourse(id, courseId);
    }

    @POST
    @Path("/{studentId}/drop/{courseId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Student dropCourse(@PathParam("studentId") String id, @PathParam("courseId") String courseId) {
        if (id == null || courseId == null)
            return null;
        return studentService.dropCourse(id, courseId);
    }

//    @GET
//    @Path("/{studentId}/courses")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Course> getCourseList(@PathParam("studentId") int id) {
//        return studentService.getCourseList(id);
//    }

//    @PUT
//    @Path("/{studentId}/courses")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Course> registerCourse(@PathParam("studentId") int id,
//                                       @QueryParam("courseName") String courseName) {
//        return studentService.registerCourse(id, courseName);
//    }
//
//    @DELETE
//    @Path("/{studentId}/courses")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Course> dropCourse(@PathParam("studentId") int id,
//                                   @QueryParam("courseName") String courseName) {
//        return studentService.dropCourse(id, courseName);
//    }

}

