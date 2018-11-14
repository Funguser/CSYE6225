package com.csye6225.lab.ziyao.people.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.csye6225.lab.ziyao.DynamoDB.DynamoDBConnector;
import com.csye6225.lab.ziyao.program.DAO.Course;
import com.csye6225.lab.ziyao.resource.InMemoryDatabase;
import com.csye6225.lab.ziyao.people.DAO.Professor;

import javax.management.Attribute;
import javax.management.AttributeValueExp;
import java.util.*;

public class ProfessorService {
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

    public ProfessorService() throws Exception {
        dynamoDB = new DynamoDBConnector();
        dynamoDB.init();
        mapper = new DynamoDBMapper(dynamoDB.getConnector());

    }

    public List<Professor> getAllProfessors() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Professor> result = mapper.scan(Professor.class, scanExpression);
        return result;
    }

    public static Professor getProfessor(String profId) {
        if (profId == null)
            return null;
        Professor prof = new Professor();
        prof.setProfessorId(profId);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put("v_id", new AttributeValue().withN(String.valueOf(profId)));
        DynamoDBQueryExpression<Professor> spec = new DynamoDBQueryExpression<Professor>();
        spec.setHashKeyValues(prof);
        spec.setIndexName("idx_professorId");
        spec.setConsistentRead(false);
        List<Professor> profList = mapper.query(Professor.class, spec);

        if (profList.size() != 0)
            return profList.get(0);
        return null;
    }

    public Professor addProfessor(Professor prof) {
        if (prof.getProfessorId() == null)
            return null;
        if (getProfessor(prof.getProfessorId()) != null)
            return null;
        prof.setRegisterDate(new Date());
        mapper.save(prof);
        return prof;
    }

    public Professor deleteProfessor(String profId) {
        Professor prof = getProfessor(profId);
        if (prof != null) {
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<Course> courseList = mapper.scan(Course.class, scanExpression);
            for (Course course : courseList) {
                if (profId.equals(course.getProfessorId())) {
                    course.setProfessorId("");
                    mapper.save(course);
                }
            }
            mapper.delete(prof);
            return prof;
        }
        return null;
    }

    public List<Professor> getProfessorByDepartment(String department) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(department));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("department = :val1").withExpressionAttributeValues(eav);
        List<Professor> result = mapper.scan(Professor.class, scanExpression);

        return result;
    }

    public Professor updateProfessorInformation(String profId, Professor prof) {
        Professor professor = getProfessor(profId);
        if (prof != null) {
            deleteProfessor(profId);
            professor.setDepartment(prof.getDepartment());
            professor.setFirstName(prof.getFirstName());
            professor.setLastName(prof.getLastName());
            addProfessor(professor);
            return professor;
        }
        return null;
    }
}
