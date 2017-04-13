package com.szb.ARMODULE.model.database;

/**
 * Created by cwh62 on 2017-03-14.
 */


import io.realm.RealmObject;

public class Player extends RealmObject {
    private String id;
    private String name;

    private String gender;
    private int age;
    private int tel;
    private int solvequestioncount;
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
