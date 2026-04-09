package com.chatapp;

public class Account {

    // non-static fields because we intend for each account instance to have it's own data
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;
    protected String cellnumber;

    public Account(String firstName, String lastName, String username, String password, String cellnumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellnumber = cellnumber;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellnumber() {
        return this.cellnumber;
    }

    public void setCellnumber(String cellnumber) {
        this.cellnumber = cellnumber;
    }
}
