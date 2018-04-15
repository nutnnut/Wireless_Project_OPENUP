package model;

import android.media.Rating;

public class Consultant {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private ConsultantRating rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ConsultantRating getRating() {
        return rating;
    }

    public void setRating(ConsultantRating rating) {
        this.rating = rating;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
