package com.csye6225.lab.ziyao.people.DAO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@XmlRootElement
public class Professor extends People{
    private String department;
    private Date joiningDate;

    public Professor(){

    }


    public Professor(int professorId, String firstName, String lastName, String department, Date joingDate){
        super(firstName, lastName, joingDate, professorId);
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

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }
}
