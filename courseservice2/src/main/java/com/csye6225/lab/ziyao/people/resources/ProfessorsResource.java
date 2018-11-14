package com.csye6225.lab.ziyao.people.resources;

import com.csye6225.lab.ziyao.people.DAO.Professor;
import com.csye6225.lab.ziyao.people.service.ProfessorService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Path("professors")
public class ProfessorsResource {
    ProfessorService professorService;

    public ProfessorsResource() throws Exception {
        professorService = new ProfessorService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{professorId}")
    public Professor getProfessor(@PathParam("professorId") String professorId){
        return professorService.getProfessor(professorId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Professor addProfessor(Professor prof){
        return professorService.addProfessor(prof);
    }

    @PUT
    @Path("/{professorId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Professor updateProfessor(@PathParam("professorId") String professorId, Professor prof){
        return professorService.updateProfessorInformation(professorId, prof);
    }


    @DELETE
    @Path("/{professorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Professor deleteProfessor(@PathParam("professorId") String profID){
        return professorService.deleteProfessor(profID);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Professor> getProfessorByDepartment(@QueryParam("department") String department){
        if (department == null)
            return professorService.getAllProfessors();
        return professorService.getProfessorByDepartment(department);
    }

}
