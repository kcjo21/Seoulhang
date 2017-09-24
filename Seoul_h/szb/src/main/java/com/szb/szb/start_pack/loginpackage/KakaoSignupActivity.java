package com.szb.szb.start_pack.loginpackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;
import com.szb.szb.R;
import com.szb.szb.start_pack.MainActivity;

import java.util.ArrayList;

/**
 * Created by cwh62 on 2017-09-22.
 */

public class KakaoSignupActivity extends Activity {
    UserProfileData userProfileData;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("카카오","oncreate");
        requestMe();
        userProfileData = new UserProfileData();
    }

    protected void requestMe(){
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult){
                String msg = "유저정보를 획득하는데 실패하였습니다 :" +errorResult;
                Logger.d(msg);
                Log.v("실패","실패");
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.계정연동실패), Toast.LENGTH_LONG);
                toast.show();

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if(result == ErrorCode.CLIENT_ERROR_CODE){
                    finish();
                }
                else{
                    redirectLoginActivity();
                }
            }
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                showSignup();
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.d("카카오","성공");
                Logger.d("UserProfile : " +userProfile);
                Log.v("userprofile", userProfile.toString());
                userProfileData.setId(Long.toString(userProfile.getId()));
                userProfileData.setEmail(userProfile.getEmail());
                userProfileData.setName(userProfile.getNickname());
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsuccess,userProfileData.getName()), Toast.LENGTH_LONG);
                toast.show();

                Log.d("카카오",userProfileData.getId());
                redirectMainActivity();
            }
        });
    }

    protected void showSignup(){
        redirectLoginActivity();
    }
    private void redirectMainActivity(){
        finish();
    }
    protected void redirectLoginActivity(){
        finish();
    }

}
