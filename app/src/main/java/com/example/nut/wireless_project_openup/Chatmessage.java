package com.example.nut.wireless_project_openup;

import java.util.Date;

public class Chatmessage
{
    private String messageText;
    private String messageUser;
    private long messageTime;

    public Chatmessage(String messageText,String messageUser){
        this.messageText=messageText;
        this.messageUser=messageUser;
        messageTime=new Date().getTime();
    }

    public Chatmessage(){
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
