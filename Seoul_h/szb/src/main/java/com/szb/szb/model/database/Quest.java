package com.szb.szb.model.database;



/**
 * Created by cwh62 on 2017-05-02.
 */
import io.realm.RealmObject;

public class Quest extends RealmObject{

    private String question;
    private String answer;
    private int questioncode;
    private int regioncode;
    private String contenttype;
    private int qresult;

    public int getQresult() { return this.qresult;}

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


    public void setQresult(int result) { this.qresult = result;}

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
