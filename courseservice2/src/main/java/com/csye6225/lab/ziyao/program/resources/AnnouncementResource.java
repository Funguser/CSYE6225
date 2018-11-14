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
    @Path("/{annoucenmentId}")
    public Announcement getAnnouncement(@PathParam("annoucenmentId") String AnnouncementId){
        return announcementService.getAnnouncement(AnnouncementId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Announcement addAnnouncement(Announcement annoucenment){
        return announcementService.addAnnouncement(annoucenment);
    }

    @PUT
    @Path("/{AnnouncementId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Announcement updateAnnouncement(@PathParam("AnnouncementId") String AnnouncementId, Announcement annoucenment){
        return announcementService.updateAnnouncementInformation(AnnouncementId, annoucenment);
    }


    @DELETE
    @Path("/{AnnouncementId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Announcement deleteAnnouncement(@PathParam("AnnouncementId") String announcementId){
        return announcementService.deleteAnnouncement(announcementId);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Announcement> getAllAnnouncement(@QueryParam("boardId") String boardId){
        if (boardId != null)
            return announcementService.getAllAnnouncement(boardId);
        return null;
    }
}
