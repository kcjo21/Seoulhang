package com.szb.ARMODULE.model.retrofit;

/**
 * Created by cwh62 on 2017-03-14.
 */

import com.google.gson.annotations.SerializedName;

public class PlayerDTO {
    private String id;
    private String name;

    private String gender;
    private int age;
    private int tel;

    @SerializedName("solve_question_count")
    private int solvequestioncount;

    @SerializedName("create_time")
    private int createtime;


    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "GameDTO - id:" + this.id + "/pass:";
    }
}
