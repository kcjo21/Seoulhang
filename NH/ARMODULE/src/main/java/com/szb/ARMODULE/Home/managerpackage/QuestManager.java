package com.szb.ARMODULE.Home.managerpackage;

import android.util.Log;
import android.app.Activity;
import com.szb.ARMODULE.model.database.Quest;
import com.szb.ARMODULE.model.retrofit.QuestDTO;
import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by cwh62 on 2017-05-02.
 */

public class QuestManager extends Activity {
    private int questioncode;
    private int regioncode;
    private  String question;
    private  String answer;
    private  String contenttype;
    private  int qresult;

    Realm realm = null;


    private static QuestManager instance = null;

    public int getQuestioncode() { return questioncode; }
    public int getRegioncode() { return regioncode; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public String getContenttype() { return contenttype; }
    public int getQresult() {return qresult;}

    public void setQuestioncode(int questioncode) {this.questioncode = questioncode;}
    public void setRegioncode(int regioncode) {this.regioncode = regioncode;}
    public void setQuestion(String question) {this.question = question;}
    public void setAnswer(String answer) {this.answer = answer;}
    public void setContenttype(String contenttype) {this.contenttype = contenttype;}
    public void setQresult(int qresult) {this.qresult = qresult;}


    public QuestManager(){

        realm = Realm.getDefaultInstance();
        Quest quest = getQuest();
        if(quest != null){
            setQuest(quest);
        }
        Log.e("ACC","TEAM MANAGER PROPERTY !!!!! "+this.toString());
    }

    public static QuestManager getInstance(){
        if(instance == null){
            instance = new QuestManager();
        }
        return instance;
    }


    public void setQuest(Quest quest){
        this.questioncode = quest.getQuestioncode();
        this.regioncode = quest.getRegioncode();
        this.question = quest.getQuestion();
        this.answer = quest.getAnswer();
        this.contenttype = quest.getContenttype();
        this.qresult = quest.getQresult();
    }

    public Quest getQuest(){
        Quest quest = realm.where(Quest.class).findFirst();
        Log.e("ACC","TEAM INFORMATION IS !!! "+quest);
        return quest;
    }

  /*  public int getQuestResult() {
        Quest quest = realm.where(Quest.class).findFirst();
        int f = quest.getResult();
        Log.e("확인","23"+f);

        return f;
    }*/


    public void create(final QuestDTO questDTO){
        final Quest quest = new Quest();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm tRealm) {
                try {
                    quest.setQuestioncode(questDTO.getQuestioncode());
                    quest.setRegioncode(questDTO.getRegioncode());
                    quest.setQuestion(questDTO.getQuestion());
                    quest.setAnswer(questDTO.getAnswer());
                    quest.setContenttype(questDTO.getContenttype());
                    quest.setQresult(questDTO.getQresult());
                    realm.copyToRealm(quest);
                } catch (RealmPrimaryKeyConstraintException e){
                    Log.e("퀘스트 생성","생성");
                }
            }
        });
        setQuest(quest);
    }

    public void deleteQuest(){
        questioncode=-1;
        regioncode=-1;
        question="";
        answer="";
        contenttype="";

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Quest.class);
            }
        });
    }


}
