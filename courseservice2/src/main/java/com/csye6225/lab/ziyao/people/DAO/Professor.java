package com.csye6225.lab.ziyao.people.DAO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


public class Professor extends People{
    private String department;

    public Professor(){

    }


    public Professor(int professorId, String firstName, String lastName, String department, Date joingDate){
        super(firstName, lastName, joingDate, professorId);
        this.firstName = firstName;
        this.department = department;
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

}
