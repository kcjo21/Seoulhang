package com.szb.szb;

import com.szb.szb.model.database.Quest;
import com.szb.szb.model.retrofit.QuestDTO;
import com.szb.szb.start_pack.loginpackage.LoginManager;
import com.szb.szb.model.retrofit.PlayerDTO;
import com.szb.szb.network.NetworkClient;
import com.unity3d.player.*;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnityPlayerActivity extends Activity
{
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    // Setup activity layout
    @Override protected void onCreate (Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

        mUnityPlayer = new UnityPlayer(this);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
    }
    public void onTouch(String ObjName){
        int region = Integer.parseInt(ObjName);
        Bundle extras = getIntent().getExtras();
        String playerid=extras.getString("playerid");

        NetworkClient networkClient = NetworkClient.getInstance("http://192.168.129.129:5000");
        networkClient.getquestioncode(playerid, region, new Callback <QuestDTO>() {
            @Override
            public void onResponse(Call<QuestDTO> call, Response<QuestDTO> response) {
                switch (response.code()){
                    case 200:
                        //json 데이터를 파싱하는 것을 수월하게 해준다.
                        QuestDTO questDTO = response.body();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<QuestDTO> call, Throwable t) {
                Log.e("ACC","s?? " + t.getMessage());

            }
        });

        Log.e("HIHI: ",ObjName);

    }
    public void onQuit(){
        finish();
    }

    // Quit Unity
    @Override protected void onDestroy ()
    {

        mUnityPlayer.quit();
        super.onDestroy();

    }

    // Pause Unity
    @Override protected void onPause()
    {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override protected void onResume()
    {
        super.onResume();
        mUnityPlayer.resume();
    }

    // This ensures the layout will be correct.
    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}