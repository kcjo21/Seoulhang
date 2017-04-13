package com.szb.ARMODULE.loginpackage;

/**
 * Created by cwh62 on 2017-03-14.
 */

import android.app.Activity;
import android.util.Log;

import com.szb.ARMODULE.model.database.Player;
import com.szb.ARMODULE.model.retrofit.PlayerDTO;

import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class LoginManager  extends Activity{
    private String id;
    private String name;

    private String gender;
    private int age;
    private int tel;

    private int solvequestioncount;

    private int createtime;

    Realm realm = null;


    private static LoginManager instance = null;

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }


    public LoginManager(){

        realm = Realm.getDefaultInstance();

        Player player = getPlayer();
        if(player != null){
            setPlayer(player);
        }
        Log.e("ACC","TEAM MANAGER PROPERTY !!!!!             "+this.toString());
    }

    public static LoginManager getInstance(){
        if(instance == null){
            instance = new LoginManager();
        }
        return instance;
    }

    public void setPlayer(Player player){
        this.id = player.getId();
    }

    public Player getPlayer(){
        Player player = realm.where(Player.class).findFirst();
        Log.e("ACC","TEAM INFORMATION IS !!! "+player);
        return player;
    }


    public void create(final PlayerDTO playerDTO){
        final Player player = new Player();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm tRealm) {
                try {
                    player.setId(playerDTO.getId());
                    realm.copyToRealm(player);
                } catch (RealmPrimaryKeyConstraintException e){
                    Log.e("debug", e.toString());
                }
            }
        });
        setPlayer(player);
    }

    public void deletePlayer(){
        id = "";

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Player.class);
            }
        });
    }

    @Override
    public String toString() {
        String content = "";
        content += "team Id ??? "+ id + "\n";
        return content;
    }
}
