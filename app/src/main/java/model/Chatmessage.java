package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Chatmessage
{
    private String messageText;
    private Integer UserID;
    private Integer ConsultantID;
    private Boolean send;
    private String messageTime;
    private Boolean isread;

    public Chatmessage(String messageText,Integer User,Integer Consultant,Integer sender){
        this.messageText=messageText;
        this.UserID=User;
        this.ConsultantID=Consultant;
        Long date=new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        messageTime =dateFormat.format(date);

        if(User.equals(sender))
        {
            this.isread=true;
            this.send=true;
        }
        else if(Consultant.equals(sender)){
            this.isread=true;
            this.send=true;
        }
        else {
            this.isread=false;
            this.send=false;
        }
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

    public Boolean IsRead() {
        return isread;
    }

    public Boolean IsSend() {
        return send;
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

    public void setSend(Boolean send) {
        this.send = send;
    }
    
}
