package com.szb.ARMODULE.model.retrofit;

/**
 * Created by cwh62 on 2017-07-19.
 */

public class JoinDTO {
    private String id;
    private String password;
    private String name;
    private String gender;
    private int age;
    private String phone;
    private String email;


    public String getId() { return id; }
    public String getpassword() {return password;}
    public String getname() {return name;}
    public String getgender() {return gender;}
    public int getage() {return age;}
    public String getphone() {return phone;}
    public String getemail() {return email;}

    public void setId(String id) {
        this.id = id;
    }
    public void setPassword(String password) { this.password = password;}
    public void setName(String name) { this.name = name;}
    public void setGender(String gender) { this.gender = gender;}
    public void setAge(Integer age) { this.age = age;}
    public void setPhone(String phone) { this.phone = phone;}
    public void setemail(String email) { this.email = email;}

}
