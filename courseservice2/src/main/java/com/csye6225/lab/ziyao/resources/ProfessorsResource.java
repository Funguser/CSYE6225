package com.csye6225.lab.ziyao.resources;

import com.csye6225.lab.ziyao.datamodel.Professor;
import com.csye6225.lab.ziyao.service.ProfessorService;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Path("professors")
public class ProfessorsResource {
    ProfessorService professorService = new ProfessorService();


//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Professor> getProfessorList(){
//
//        return professorService.getAllProfessors();
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{professorId}")
    public Professor getProfessor(@PathParam("professorId") Long professorId){
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
    public Professor updateProfessor(@PathParam("professorId") Long professorId, Professor prof){
        return professorService.updateProfessorInformation(professorId, prof);
    }


    @DELETE
    @Path("/{professorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Professor deleteProfessor(@PathParam("professorId") Long profID){
        return professorService.deleteProfessor(profID);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Professor> getProfessorByDepartment(@QueryParam("department") String department){
        if (department == null)
            return professorService.getAllProfessors();
        return professorService.getProfessorByDepartment(department);
    }

    public void addProfessor(String name, String department, Date joingDate){
        professorService.addProfessor(name, department, joingDate);
    }
}
