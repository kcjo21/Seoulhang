package com.szb.ARMODULE;

import android.app.Application;
import android.util.Log;

import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by cwh62 on 2017-03-14.
 */

public class RealmInit extends Application {
    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("debug","Application created!");
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
//        configureRealmDatabase();
    }

    public Realm getRealm() {
        if(realm == null){
            realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    public int setPrimaryKeyId(Class c){
        try{
            AtomicLong primaryKeyValue = new AtomicLong(realm.where(c).max("id").longValue());
            return (int)primaryKeyValue.incrementAndGet();
        }catch (NullPointerException e){
            return 1;
        }

    }

    private void configureRealmDatabase(){
        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }
}
