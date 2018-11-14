package com.csye6225.lab.ziyao.program.DAO;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "announcement")
public class Announcement {
    String id;
    String announcementId;
    String boardId;
    String announcementText;

    public Announcement() {
    }

    @DynamoDBAutoGeneratedKey
    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBIndexHashKey(attributeName = "announcementId", globalSecondaryIndexName = "idx_announcementId")
    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    @DynamoDBRangeKey(attributeName = "boardId")
    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    @DynamoDBAttribute(attributeName = "text")
    public String getAnnouncementText() {
        return announcementText;
    }

    public void setAnnouncementText(String announcementText) {
        this.announcementText = announcementText;
    }
}
