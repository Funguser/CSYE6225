package com.csye6225.lab.ziyao.program.resources;

import com.csye6225.lab.ziyao.program.DAO.Announcement;
import com.csye6225.lab.ziyao.program.service.AnnouncementService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("announcements")
public class AnnouncementResource {
    AnnouncementService announcementService;

    public AnnouncementResource() throws Exception {
        announcementService = new AnnouncementService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{boardId}_{announcementId}")
    public Announcement getAnnouncement(@PathParam("announcementId") String announcementId, @PathParam("boardId") String boardId){
        return announcementService.getAnnouncement(announcementId, boardId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Announcement addAnnouncement(Announcement annoucenment){
        return announcementService.addAnnouncement(annoucenment);
    }

    @PUT
    @Path("/{boardId}_{announcementId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Announcement updateAnnouncement(@PathParam("announcementId") String announcementId, @PathParam("boardId") String boardId,  Announcement annoucenment){
        return announcementService.updateAnnouncementInformation(announcementId, boardId, annoucenment);
    }


    @DELETE
    @Path("/{boardId}_{announcementId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Announcement deleteAnnouncement(@PathParam("announcementId") String announcementId, @PathParam("boardId") String boardId){
        return announcementService.deleteAnnouncement(announcementId, boardId);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Announcement> getAllAnnouncement(@QueryParam("boardId") String boardId){
        if (boardId != null)
            return announcementService.getAllAnnouncement(boardId);
        return null;
    }
}
