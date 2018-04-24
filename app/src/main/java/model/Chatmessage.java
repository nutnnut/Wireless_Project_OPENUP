package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Chatmessage
{
    private String messageText;
    private Integer UserID;
    private Integer ConsultantID;
    private Boolean sender;
    private String messageTime;
    private Boolean isread;

    public Chatmessage(String messageText,Integer User,Integer Consultant,Boolean sender){
        this.messageText=messageText;
        this.UserID=User;
        this.ConsultantID=Consultant;
        Long date=new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        messageTime =dateFormat.format(date);
        this.sender = sender;
    }

    public Chatmessage(){
    }

    public String getMessageText() {
        return messageText;
    }

    public Integer getUserID() {
        return UserID;
    }

    public Integer getConsultantID() {
        return ConsultantID;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public Boolean getSender(){
        return this.sender;
    }

    public Boolean IsRead() {
        return isread;
    }


    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setUserID(Integer User) {
        this.UserID = User;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void setConsultantID(Integer consultantID) {
        ConsultantID = consultantID;
    }

    public void setIsread(Boolean isread) {
        this.isread = isread;
    }

    public void setSender(Boolean sender) {
        this.sender = sender;
    }
    
}
