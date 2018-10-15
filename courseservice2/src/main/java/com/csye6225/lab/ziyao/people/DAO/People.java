package com.csye6225.lab.ziyao.people.DAO;

import java.util.Date;

public class People {
    String firstName;
    String lastName;
    Date registerDate;
    int id;

    public People(String firstName, String lastName, Date registerDate, int id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.registerDate = registerDate;
        this.id = id;
    }

    public People(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
