package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is object class for storing chat message information
 */
public class Chatmessage
{
    private String messageText;
    private Integer UserID;
    private Integer ConsultantID;
    private Boolean sender; //identifies whether the sender is user(true) or consultant(false)
    private String messageTime;
    private Boolean isread;

    /**
     * Constructor for creating chatmessage with parameters
     * @param messageText
     * @param User
     * @param Consultant
     * @param sender
     */
    public Chatmessage(String messageText,Integer User,Integer Consultant,Boolean sender){
        this.messageText=messageText;
        this.UserID=User;
        this.ConsultantID=Consultant;
        Long date=new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        messageTime =dateFormat.format(date);
        this.sender = sender;
    }

    /**
     * Constructor for creating a blank chatmessage
     */
    public Chatmessage(){
    }

    //Getters and Setters

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
