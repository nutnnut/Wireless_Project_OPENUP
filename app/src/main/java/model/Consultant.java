package model;

/**
 * This class is an object class for storing consultant account information
 */
public class Consultant {
    private Integer id;
    private String email;
    private String password;
    private ConsultantInfo info;

    //Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ConsultantInfo getInfo() {
        return info;
    }

    public void setInfo(ConsultantInfo info) {
        this.info = info;
    }



}
