package com.csye6225.lab.ziyao.program.resources;

import com.csye6225.lab.ziyao.program.DAO.Course;
import com.csye6225.lab.ziyao.program.service.CourseService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("courses")
public class CourseResource {
    CourseService courseService = new CourseService();

    public CourseResource() throws Exception {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GET
    @Path("{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Course getCourse(@PathParam("courseId") int courseId) {
        return courseService.getCourse(courseId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Course addCourse(Course course) {
        return courseService.addCourse(course);
    }

    @PUT
    @Path("{courseId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Course editCourse(@PathParam("courseId")int courseId, Course course) {
        return courseService.editCourse(courseId, course);
//        try {
//            JSONObject jObject = new JSONObject(jsonString);
//            int professorId = jObject.getInt("professorId");
//            int TAId = jObject.getInt("TAId");
//            JSONArray studentIdJsonList = jObject.getJSONArray("StudentId");
//            ArrayList<Integer> studentIdList = new ArrayList<>();
//            for (int i = 0; i < studentIdJsonList.length(); i++) {
//                studentIdList.add((Integer) studentIdJsonList.get(i));
//            }
//            courseService = programService.getCorrCourseService(programName);
//            return courseService.editCourse(courseName, professorId, TAId, studentIdList);
//        } catch (Exception e){
//            return null;
//        }
    }

    @DELETE
    @Path("{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Course deleteCourse(@PathParam("courseId") int courseId) {
        return courseService.deleteCourse(courseId);
    }

//    @GET
//    @Path("{courseName}/lecture")
//    public List<Lecture> getAllLecture(@PathParam("courseName") String courseName) {
//        courseService = programService.getCorrCourseService(programName);
//        return courseService.getAllLectures(courseName);
//    }
//    @GET
//    @Path("{courseName}/lecture/{lectureId}")
//    public Lecture getLecture(@PathParam("courseName") String courseName,
//                              @PathParam("lectureId") Integer lectureId) {
//        courseService = programService.getCorrCourseService(programName);
//        return courseService.getLecture(courseName, lectureId);
//    }
}
