package com.cloudassest.intern.phone_book.model;

import org.springframework.data.annotation.Id;

public class User {
    public User(){

    }
    public User(String userName,String password,String phoneNum){
        this.userName=userName;
        this.password=password;
        this.phoneNum=phoneNum;
    }
    @Id
    private String id;
    private String userName;
    private String phoneNum;
    private String email;

    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
