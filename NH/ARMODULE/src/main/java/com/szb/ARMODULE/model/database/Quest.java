package com.szb.ARMODULE.model.database;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cwh62 on 2017-05-02.
 */

public class Quest {

    private String question;
    private String answer;
    private int questioncode;
    private int regioncode;
    private String contenttype;

    public String getContenttype() {
        return this.contenttype;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public int getQuestioncode() {
        return this.questioncode;
    }

    public int getRegioncode() {
        return this.regioncode;
    }


    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestioncode(int questioncode) {
        this.questioncode = questioncode;
    }

    public void setRegioncode(int regioncode) {
        this.regioncode = regioncode;
    }
}
