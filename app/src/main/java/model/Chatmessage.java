package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Chatmessage
{
    private String messageText;
    private String messageUser;
    private String messageTime;

    public Chatmessage(String messageText,String messageUser){
        this.messageText=messageText;
        this.messageUser=messageUser;
        Long date=new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        messageTime =dateFormat.format(date);
    }

    public Chatmessage(){
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
