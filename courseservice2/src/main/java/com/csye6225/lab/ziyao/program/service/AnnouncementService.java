package com.csye6225.lab.ziyao.program.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.csye6225.lab.ziyao.DynamoDB.DynamoDBConnector;
import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.program.DAO.Announcement;
import com.csye6225.lab.ziyao.program.DAO.Board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnouncementService {
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


    public AnnouncementService() throws Exception {
        dynamoDB = new DynamoDBConnector();
        dynamoDB.init();
        mapper = new DynamoDBMapper(dynamoDB.getConnector());
    }


    public static Announcement getAnnouncement(String announcementId) {
        if (announcementId == null)
            return null;
        Announcement announcement = new Announcement();
        announcement.setAnnouncementId(announcementId);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put("v_id", new AttributeValue().withS(announcementId));
        DynamoDBQueryExpression<Announcement> spec = new DynamoDBQueryExpression<Announcement>();
        spec.setHashKeyValues(announcement);
        spec.setIndexName("idx_announcementId");
        spec.setConsistentRead(false);
        List<Announcement> list = mapper.query(Announcement.class, spec);

        if (list.size() != 0)
            return list.get(0);
        return null;
    }

    public Announcement addAnnouncement(Announcement annoucenment) {
        if (annoucenment.getAnnouncementId() == null)
            return null;
        Announcement ann = getAnnouncement(annoucenment.getAnnouncementId());
        if (ann != null)
            return null;
        if (BoardService.getBoard(annoucenment.getBoardId())== null)
            return null;
        mapper.save(annoucenment);
        return annoucenment;
    }

    public Announcement updateAnnouncementInformation(String announcementId, Announcement announcement) {
        Announcement ann = getAnnouncement(announcementId);
        if (ann == null)
            return null;
        if (BoardService.getBoard(announcement.getBoardId()) == null)
            return null;
        deleteAnnouncement(announcementId);
        addAnnouncement(announcement);
        return ann;
    }

    public Announcement deleteAnnouncement(String announcementId) {
        Announcement announcement = getAnnouncement(announcementId);
        if (announcement == null)
            return null;
        mapper.delete(announcement);
        return announcement;
    }

    public List<Announcement> getAllAnnouncement(String boardId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(boardId));
//        DynamoDBQueryExpression<Announcement> queryExpression = new DynamoDBQueryExpression<Announcement>()
//                .withRangeKeyCondition("boardId", new Condition()).withExpressionAttributeValues(eav);
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("boardId = :val1").withExpressionAttributeValues(eav);
        List<Announcement> result = mapper.scan(Announcement.class, scanExpression);
        return result;
    }
}
