package com.csye6225.lab.ziyao.program.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.lab.ziyao.DynamoDB.DynamoDBConnector;
import com.csye6225.lab.ziyao.people.DAO.Student;
import com.csye6225.lab.ziyao.program.DAO.Announcement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnouncementService {
    static DynamoDBConnector dynamoDB;
    DynamoDBMapper mapper;


    public AnnouncementService() throws Exception {
        dynamoDB = new DynamoDBConnector();
        dynamoDB.init();
        mapper = new DynamoDBMapper(dynamoDB.getConnector());
    }


    public Announcement getAnnouncement(int announcementId) {
        Announcement announcement = new Announcement();
        announcement.setAnnoucementId(announcementId);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put("v_id", new AttributeValue().withN(String.valueOf(announcementId)));
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
        if (annoucenment.getAnnoucementId() == 0)
            return null;
        Announcement ann = getAnnouncement(annoucenment.getAnnoucementId());
        if (ann != null)
            return null;
        mapper.save(annoucenment);
        return annoucenment;
    }

    public Announcement updateAnnouncementInformation(int announcementId, Announcement annoucenment) {
        Announcement ann = getAnnouncement(announcementId);
        if (ann == null)
            return null;
        ann.setAnnoucementText(annoucenment.getAnnoucementText());
        ann.setBoardId(annoucenment.getBoardId());
        mapper.save(ann);
        return ann;
    }

    public Announcement deleteAnnouncement(int announcementId) {
        Announcement announcement = getAnnouncement(announcementId);
        if (announcement == null)
            return null;
        mapper.delete(announcement);
        return announcement;
    }

    public List<Announcement> getAllAnnouncement(Integer boardId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withN(boardId.toString()));
        DynamoDBQueryExpression<Announcement> dynamoDBQueryExpression = new DynamoDBQueryExpression<Announcement>().withKeyConditionExpression("boardId = :val1").withExpressionAttributeValues(eav);
        List<Announcement> result = mapper.query(Announcement.class, dynamoDBQueryExpression);
        return result;
    }
}
