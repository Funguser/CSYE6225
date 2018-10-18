package com.csye6225.lab.ziyao.program.resources;

import com.csye6225.lab.ziyao.program.DAO.Program;
import com.csye6225.lab.ziyao.program.service.ProgramService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("programs")
public class ProgramResource {
    ProgramService programService = new ProgramService();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Program> getAllProgram() {
        return programService.getAllProgram();
    }


    @GET
    @Path("{programId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Program getProgram(@PathParam("programId") String id) {
        return programService.getProgram(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Program addProgram(Program program) {
        return programService.addProgram(program);
    }

    @DELETE
    @Path("{programId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Program deleteProgram(@PathParam("programId") String id) {
        return programService.deleteProgram(id);
    }

    @PUT
    @Path("{programId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Program editProgram(@PathParam("programId")String id, Program program) {
        return programService.editProgram(id, program);
    }

}
