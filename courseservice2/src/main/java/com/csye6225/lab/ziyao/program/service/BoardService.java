package com.csye6225.lab.ziyao.program.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.lab.ziyao.DynamoDB.DynamoDBConnector;
import com.csye6225.lab.ziyao.program.DAO.Board;
import com.csye6225.lab.ziyao.program.DAO.Course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService {
    static DynamoDBConnector dynamoDB;
    DynamoDBMapper mapper;
    CourseService courseService;

    public BoardService() throws Exception {
        dynamoDB = new DynamoDBConnector();
        dynamoDB.init();
        mapper = new DynamoDBMapper(dynamoDB.getConnector());
        courseService = new CourseService();
    }

    public Board getBoard(int boardId) {
        Board board = new Board();
        board.setBoardId(boardId);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put("v_id", new AttributeValue().withN(String.valueOf(boardId)));
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
        if (board.getBoardId() == 0)
            return null;
        int courseId = board.getCourseId();
        Course course = courseService.getCourse(courseId);
        if (course == null)
            return null;
        Board duBoard = getBoard(board.getBoardId());
        if (duBoard != null)
            return null;
        mapper.save(board);
        return board;
    }

    public Board updateBoardInformation(int boardId, Board board) {
        Board bor = getBoard(boardId);
        if (bor == null)
            return null;
        bor.setCourseId(board.getCourseId());
        mapper.save(bor);
        return bor;
    }

    public Board deleteBoard(int boardID) {
        Board board = getBoard(boardID);
        if (board == null)
            return null;
        mapper.delete(board);
        return board;
    }

    public List<Board> getAllBoards() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Board> result = mapper.scan(Board.class, scanExpression);
        return result;
    }
}
