package com.csye6225.lab.ziyao.program.resources;

import com.csye6225.lab.ziyao.program.DAO.Course;
import com.csye6225.lab.ziyao.program.DAO.Lecture;
import com.csye6225.lab.ziyao.program.service.CourseService;
import com.csye6225.lab.ziyao.program.service.ProgramService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("{programName}/courses")
public class CourseResource {
    @PathParam("programName") String programName;
    ProgramService programService = new ProgramService();
    CourseService courseService = null;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> getAllCourses() {
        courseService = programService.getCorrCourseService(programName);
        return courseService.getAllCourses();
    }

    @GET
    @Path("{courseName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Course getCourse(@PathParam("courseName") String courseName) {
        courseService = programService.getCorrCourseService(programName);
        return courseService.getCourse(courseName);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Course addCourse(Course course) {
        courseService = programService.getCorrCourseService(programName);
        return courseService.addCourse(course);
    }

    @PUT
    @Path("{courseName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Course editCourse(@PathParam("courseName")String courseName,
                             String jsonString) {
        try {
            JSONObject jObject = new JSONObject(jsonString);
            int professorId = jObject.getInt("professorId");
            int TAId = jObject.getInt("TAId");
            JSONArray studentIdJsonList = jObject.getJSONArray("StudentId");
            ArrayList<Integer> studentIdList = new ArrayList<>();
            for (int i = 0; i < studentIdJsonList.length(); i++) {
                studentIdList.add((Integer) studentIdJsonList.get(i));
            }
            courseService = programService.getCorrCourseService(programName);
            return courseService.editCourse(courseName, professorId, TAId, studentIdList);
        } catch (Exception e){
            return null;
        }
    }

    @DELETE
    @Path("{courseName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Course deleteCourse(@PathParam("courseName") String courseName) {
        courseService = programService.getCorrCourseService(programName);
        return courseService.deleteCourse(courseName);
    }

    @GET
    @Path("{courseName}/lecture")
    public List<Lecture> getAllLecture(@PathParam("courseName") String courseName) {
        courseService = programService.getCorrCourseService(programName);
        return courseService.getAllLectures(courseName);
    }
    @GET
    @Path("{courseName}/lecture/{lectureId}")
    public Lecture getLecture(@PathParam("courseName") String courseName,
                              @PathParam("lectureId") Integer lectureId) {
        courseService = programService.getCorrCourseService(programName);
        return courseService.getLecture(courseName, lectureId);
    }
}
