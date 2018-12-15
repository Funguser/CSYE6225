package com.csye6225.lab.ziyao.program.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.lab.ziyao.AWSService.DynamoDBConnector;
import com.csye6225.lab.ziyao.program.DAO.Board;
import com.csye6225.lab.ziyao.program.DAO.Course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService {
    static DynamoDBConnector dynamoDB;
    static DynamoDBMapper mapper;

    static {
        try {
            dynamoDB = new DynamoDBConnector();
            dynamoDB.init();
            mapper = new DynamoDBMapper(dynamoDB.getConnector());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public BoardService() throws Exception {
        dynamoDB = new DynamoDBConnector();
        dynamoDB.init();
        mapper = new DynamoDBMapper(dynamoDB.getConnector());
    }

    public static Board getBoard(String boardId) {
        if (boardId == null)
            return null;
        Board board = new Board();
        board.setBoardId(boardId);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put("v_id", new AttributeValue().withS(boardId));
        DynamoDBQueryExpression<Board> spec = new DynamoDBQueryExpression<Board>();
        spec.setHashKeyValues(board);
        spec.setIndexName("idx_boardId");
        spec.setConsistentRead(false);
        List<Board> list = mapper.query(Board.class, spec);

        if (list.size() != 0)
            return list.get(0);
        return null;
    }

    public Board addBoard(Board board) {
        if (board.getBoardId() == null || board.getCourseId() == null)
            return null;
        String courseId = board.getCourseId();
        Course course = CourseService.getCourse(courseId);
        if (course == null)
            return null;
        if (course.getBoardId() != null && !course.getBoardId().equals(board.getBoardId()))
            return null;

        Board duBoard = getBoard(board.getBoardId());
        if (duBoard != null)
            return null;
        course.setBoardId(board.getBoardId());
        mapper.save(course);
        mapper.save(board);
        return board;
    }

    public Board updateBoardInformation(String boardId, Board board) {
        Board bor = getBoard(boardId);
        if (bor == null || board.getCourseId() == null)
            return null;


        Course newCourse = CourseService.getCourse(board.getCourseId());
        if (newCourse == null || (newCourse.getBoardId() != null && !boardId.equals(newCourse.getBoardId())))
            return null;
        deleteBoard(boardId);
        Course course = CourseService.getCourse(bor.getCourseId());
        newCourse.setBoardId(boardId);
        course.setBoardId("");
        mapper.save(course);
        mapper.save(newCourse);

        bor.setCourseId(board.getCourseId());

        mapper.save(bor);
        return bor;
    }

    public Board deleteBoard(String boardID) {
        Board board = getBoard(boardID);
        if (board == null)
            return null;
        Course course = CourseService.getCourse(board.getCourseId());
        if (course != null) {
            course.setBoardId("");
            mapper.save(course);
        }
        mapper.delete(board);
        return board;
    }

    public List<Board> getAllBoards() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Board> result = mapper.scan(Board.class, scanExpression);
        return result;
    }
}
