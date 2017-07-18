package com.szb.ARMODULE.model.retrofit;



/**
 * Created by cwh62 on 2017-05-02.
 */

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.szb.ARMODULE.model.database.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryDTO {
    @SerializedName("region_name")
    private String regionname;
    @SerializedName("question_code")
    private int questioncode;
    private String grade;
    private String question;
    private String answer;
    @SerializedName("player_code")
    private String playercode;
    @SerializedName("player_name")
    private String playername;
    private int point;
    private int hint;



    public int getQuestioncode() {return this.questioncode;}
    public String getRegionname() {return  this.regionname;}
    public String getQuestion() {return this.question;}
    public String getAnswer() {return this.answer;}
    public String getPlayercode() {return this.playercode;}
    public String getPlayername() {return this.playername;}
    public int getPoint() {return this.point;}
    public int getHint() {return this.hint;}
    public String getGrade() {return this.grade;}


    public void setRegionname(String regionname) {
        this.regionname = regionname;
    }
    public void setQuestioncode(int questioncode) {
        this.questioncode = questioncode;
    }
    public void setQuestion(String question) {this.question = question;}
    public void setAnswer(String answer) {this.answer = answer;}
    public void setPlayercode(String playercode) {this.playercode = playercode;}
    public void setPlayername(String playername) {this.playername = playername;}
    public void setPoint(int point) {this.point = point;}
    public void setHint(int hint) {this.hint = hint;}
    public void setGrade(String grade) {this.grade = grade;}

}
