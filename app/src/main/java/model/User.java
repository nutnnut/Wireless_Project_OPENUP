package model;

import java.util.Collection;

/**
 * Created by BAMBOOK on 4/6/2018.
 */

public class User {

    private Integer ID;
    private String email;
    private String password;
    private Information info;
    private Collection<Chatmessage> Chat;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Information getInfo() {
        return info;
    }

    public void setInfo(Information info) {
        this.info = info;
    }

    public Collection<? extends Chatmessage> getChat(Consultant c) {
        Collection<Chatmessage> chatwithconsultant=null;
        for (Chatmessage e: Chat)
        {
            if(e.getConsultantID().equals(c.getId())){
                chatwithconsultant.add(e);
            }
        }
        return chatwithconsultant;
    }
}
