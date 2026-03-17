package com.example.chatapp;

public class MessageModel {

    String senderId;
    String message;

    public MessageModel(){}

    public MessageModel(String senderId, String message){
        this.senderId = senderId;
        this.message = message;
    }

    public String getSenderId(){ return senderId; }
    public String getMessage(){ return message; }
}