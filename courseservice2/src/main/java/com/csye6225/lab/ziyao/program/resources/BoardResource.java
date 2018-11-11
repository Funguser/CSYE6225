package com.csye6225.lab.ziyao.program.resources;

import com.csye6225.lab.ziyao.program.DAO.Board;
import com.csye6225.lab.ziyao.program.service.BoardService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("board")
public class BoardResource {
    BoardService boardService;

    public BoardResource() throws Exception {
        boardService = new BoardService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{boardId}")
    public Board getBoard(@PathParam("boardId") int boardId){
        return boardService.getBoard(boardId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Board addBoard(Board board){
        return boardService.addBoard(board);
    }

    @PUT
    @Path("/{boardId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Board updateBoard(@PathParam("boardId") int boardId, Board board){
        return boardService.updateBoardInformation(boardId, board);
    }


    @DELETE
    @Path("/{boardId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Board deleteBoard(@PathParam("boardId") int boardID){
        return boardService.deleteBoard(boardID);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Board> getAllBoard(){
        return boardService.getAllBoards();
    }
}
