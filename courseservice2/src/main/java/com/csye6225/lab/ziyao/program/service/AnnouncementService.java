package com.csye6225.lab.ziyao.program.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.lab.ziyao.AWSService.DynamoDBConnector;
import com.csye6225.lab.ziyao.program.DAO.Announcement;

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


    public static Announcement getAnnouncement(String announcementId, String boardId) {
        if (announcementId == null || boardId == null)
            return null;
//        Announcement announcement = new Announcement();
//        announcement.setAnnouncementId(announcementId);
//        announcement.setBoardId(boardId);
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v_ann", new AttributeValue().withS(announcementId));
        eav.put(":v_bor", new AttributeValue().withS(boardId));
        DynamoDBQueryExpression<Announcement> spec = new DynamoDBQueryExpression<Announcement>()
                .withKeyConditionExpression("announcementId = :v_ann and boardId = :v_bor")
                .withIndexName("idx_announcementId")
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false);
//        spec.setHashKeyValues(announcement);
//        spec.setIndexName("idx_announcementId");
//        spec.setRangeKeyConditions(eav);
//        spec.setConsistentRead(false);
        List<Announcement> list = mapper.query(Announcement.class, spec);

        if (list.size() != 0)
            return list.get(0);
        return null;
    }

    public Announcement addAnnouncement(Announcement annoucenment) {
        if (annoucenment.getAnnouncementId() == null)
            return null;
        Announcement ann = getAnnouncement(annoucenment.getAnnouncementId(), annoucenment.getBoardId());
        if (ann != null)
            return null;
        if (BoardService.getBoard(annoucenment.getBoardId())== null)
            return null;
        mapper.save(annoucenment);
        return annoucenment;
    }

    public Announcement updateAnnouncementInformation(String announcementId, String boardId, Announcement announcement) {
        Announcement ann = getAnnouncement(announcementId, boardId);
        if (ann == null)
            return null;
        if (BoardService.getBoard(announcement.getBoardId()) == null)
            return null;
        deleteAnnouncement(announcementId, boardId);
        addAnnouncement(announcement);
        return ann;
    }

    public Announcement deleteAnnouncement(String announcementId, String boardId) {
        Announcement announcement = getAnnouncement(announcementId, boardId);
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
