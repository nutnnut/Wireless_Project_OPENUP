package model;

/**
 * Created by BAMBOOK on 4/15/2018.
 */

public class ConsultantInfo {
    private Integer conInfoID;
    private Integer consultantID;
    private String gender;
    private String birthdate;
    private String expertise;
    private ConsultantRating rating;

    public ConsultantRating getRating() {
        return rating;
    }

    public void setRating(ConsultantRating rating) {
        this.rating = rating;
    }

    public Integer getConInfoID() {
        return conInfoID;
    }

    public void setConInfoID(Integer conInfoID) {
        this.conInfoID = conInfoID;
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

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

}
