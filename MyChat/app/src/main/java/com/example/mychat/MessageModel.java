package com.example.mychat;

public class MessageModel {
private String msgId;
private String senderId;
private String message;
private String dateTime;



    public MessageModel(String msgId, String senderId, String message, String dateTime ) {
        this.msgId = msgId;
        this.senderId = senderId;
        this.message = message;
        this.dateTime=dateTime;

    }
    public MessageModel() {
    }
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
