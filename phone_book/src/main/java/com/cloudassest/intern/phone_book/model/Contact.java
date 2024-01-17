package com.cloudassest.intern.phone_book.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Contact {
    public Contact(){

    }
    public Contact(String first_name,String phoneNum){
        this.first_name = first_name;
        this.phoneNum = phoneNum;
    }
    @Id
    private String id;
    private String name;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String nick;
    private String phoneNum;
    private String phoneNumType;
    private String email;
    private String emailType;
    private String Notes;
    @DBRef
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public void setName(String middle_name, String last_name) {
        this.name = this.first_name;
        if(middle_name!=null) name=name+" "+middle_name;
        if(last_name!=null ) name= name +" "+last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNumType() {
        return phoneNumType;
    }

    public void setPhoneNumType(String phoneNumType) {
        this.phoneNumType = phoneNumType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }
}
