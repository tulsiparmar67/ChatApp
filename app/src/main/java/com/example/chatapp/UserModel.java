package com.example.chatapp;

public class UserModel {

    String name;
    String email;
    String uid;

    public UserModel() {
        // empty constructor (Firebase mate jaruri)
    }

    public UserModel(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}