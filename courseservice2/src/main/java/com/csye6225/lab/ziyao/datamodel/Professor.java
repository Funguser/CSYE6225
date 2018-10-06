package com.csye6225.lab.ziyao.datamodel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@XmlRootElement
public class Professor {
    private String firstName;
    private String department;
    private long professorId;
    private Date joiningDate;

    public Professor(){

    }


    public Professor(long professorId, String firstName, String department, Date joingDate){
        this.professorId = professorId;
        this.firstName = firstName;
        this.department = department;
        this.joiningDate = joingDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(long professorId) {
        this.professorId = professorId;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }
}
