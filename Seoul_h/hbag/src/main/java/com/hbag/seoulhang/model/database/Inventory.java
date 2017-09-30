package com.hbag.seoulhang.model.database;



/**
 * Created by cwh62 on 2017-05-04.
 */


import io.realm.RealmObject;

public class Inventory extends RealmObject {

    private String regionname;
    private int questioncode;
    private String question;
    private String answer;
    private String playercode;
    private String playername;
    private int point;
    private int hint;
    private String grade;


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
