package com.szb.szb.model.retrofit;

/**
 * Created by cwh62 on 2017-03-14.
 */

public class PlayerDTO {
    private String id;
    private int language;





    public String getId() { return id; }
    public int getLanguage() {return language;}

    public void setId(String id) {
        this.id = id;
    }
    public void setLanguage(int language) {this.language = language;}

    @Override
    public String toString(){
        return "GameDTO - id:" + this.id + "/pass:";
    }
}
