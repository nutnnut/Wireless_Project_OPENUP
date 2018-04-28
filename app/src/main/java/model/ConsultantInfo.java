package model;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import java.text.ParseException;
import java.util.Date;

/**
 * This class is an object class for storing consultant profile information
 */

public class ConsultantInfo {
    private Integer conInfoID;
    private Integer consultantID;
    private String name;
    private String gender;
    private String birthdate;
    private Integer age;
    private String expertise;

    //Getters and Setters
    public Integer getConInfoID() {
        return conInfoID;
    }

    public void setConInfoID(Integer conInfoID) {
        this.conInfoID = conInfoID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getConsultantID() {
        return consultantID;
    }

    public void setConsultantID(Integer consultantID) {
        this.consultantID = consultantID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public Integer getAge() {
        return age;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;

        try {
            //Calculate age from birthdate and current date
            Calendar currentDate = Calendar.getInstance();
            int year = currentDate.get(Calendar.YEAR);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar birthDate = Calendar.getInstance();
            birthDate.setTime(dateFormat.parse(birthdate));
            int birthYear = birthDate.get(Calendar.YEAR);
            this.age = year - birthYear;

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
