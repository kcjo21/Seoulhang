package com.szb.ARMODULE.model.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cwh62 on 2017-05-02.
 */

public class InventoryDTO {
    private int index;
    @SerializedName("player_code")
    private int playercode;
    @SerializedName("question_code")
    private int questioncode;
    private String status;
    @SerializedName("start_time")
    private String starttime;
    @SerializedName("finish_time")
    private String finishtime;

    public int getIndex() {return this.index;}
    public int getPlayercode() {return this.playercode;}
    public int getQuestioncode() {return this.questioncode;}
    public String getStatus() {return this.status;}
    public String getStarttime() {return this.starttime;}
    public String getFinishtime() {return this.finishtime;}


    public void setIndex(int index) {
        this.index = index;
    }
    public void setPlayercode(int playercode) {
        this.playercode = playercode;
    }
    public void setQuestioncode(int questioncode) {
        this.questioncode = questioncode;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }
}
