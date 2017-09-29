package com.szb.szb.start_pack.registerpack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.szb.szb.BaseActivity;
import com.szb.szb.Home.Home_Main;
import com.szb.szb.R;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;
import com.szb.szb.start_pack.MainActivity;
import com.szb.szb.start_pack.loginpackage.Logm;
import com.szb.szb.start_pack.loginpackage.UserProfileData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditprofileActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener {


    private GoogleApiClient mGoogleApiClient;

    Button Withdrawal;
    Button Change_pass;
    Button Commit;
    Button Cancel;
    EditText Password;
    EditText Email;
    EditText Name;
    InputMethodManager keyboard;
    TextView id;
    TextView title;
    NetworkClient networkClient;
    Ipm ipm;
    Logm logm;
    UserProfileData userProfileData;
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SharedPreferences loginPref = getSharedPreferences("log",MODE_PRIVATE);
        final String loginType = loginPref.getString("logintype","");
        final SharedPreferences.Editor editor = loginPref.edit();
        ipm = new Ipm();
        logm = new Logm();
        final String sid = logm.getPlayerid();
        final String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);
        if(!loginType.equals("normal")){
            setContentView(R.layout.activity_editprofile_social);
            Commit = (Button) findViewById(R.id.commit_social);
            Cancel = (Button) findViewById(R.id.cancel_social);
            Name = (EditText) findViewById(R.id.name_t_social);
            title = (TextView) findViewById(R.id.title_social);
            Withdrawal = (Button) findViewById(R.id.bt_Withdrawal_social);
            title.setText(R.string.facebook_with);
            userProfileData = new UserProfileData();


            Withdrawal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(EditprofileActivity.this);
                    alert.setCancelable(false);
                    alert.setTitle(R.string.탈퇴재확인);
                    alert.setMessage(R.string.facebook_leave_msg);

                    alert.setNegativeButton(R.string.취소, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
                    alert.setPositiveButton(R.string.확인, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
                    final AlertDialog dialog = alert.create();
                    dialog.show();
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            networkClient.realwithdrawal(sid, new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    switch (response.code()){
                                        case 200:

                                            if(loginType.equals("facebook")) {                          //페이스북 회원탈퇴시 퍼미션해제 및 로그아웃 및 DB내 정보삭제
                                                if(AccessToken.getCurrentAccessToken() == null){
                                                    return; // 이미 로그아웃 되어 있음.
                                                }
                                                new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                                                        null, HttpMethod.DELETE, new GraphRequest.Callback() {
                                                    @Override
                                                    public void onCompleted(GraphResponse response) {
                                                        LoginManager.getInstance().logOut();
                                                        editor.putString("logintype", "logout");
                                                        editor.apply();
                                                        Log.d("페이스북 퍼미션 삭제", "성공");
                                                        Intent intent = new Intent(EditprofileActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }).executeAsync();
                                            }
                                            else if(loginType.equals("google")){                        //구글 회원탈퇴시 퍼미션해제 및 로그아웃 및 DB내 정보삭제
                                                Log.d("로그인타입",loginType);
                                                Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                                                        new ResultCallback<Status>() {
                                                            @Override
                                                            public void onResult(@NonNull Status status) {
                                                                Log.d("구글 탈퇴","성공");
                                                            }
                                                        });
                                                editor.putString("logintype", "logout");
                                                editor.apply();
                                                Log.d("구글퍼미션 삭제", "성공");
                                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.탈퇴성공), Toast.LENGTH_LONG);
                                                toast.show();
                                                Intent intent = new Intent(EditprofileActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            break;
                                        default:
                                            break;
                                    }

                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            });

                        }
                    });


                }
            });
            Commit.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    String sname = Name.getText().toString();
                    String smail = userProfileData.getEmail();
                    Log.d("회원정보수정", sname);
                    networkClient.editprofile(sid, "", sname, smail, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.회원정보수정완료), Toast.LENGTH_LONG);
                                    toast.show();
                                    Intent intent = new Intent(EditprofileActivity.this, Home_Main.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                default:
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

                }
            });
            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EditprofileActivity.this, Home_Main.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
        else {
            setContentView(R.layout.activity_editprofile);
            title = (TextView) findViewById(R.id.title_normal);
            Withdrawal = (Button) findViewById(R.id.bt_Withdrawal);
            Change_pass = (Button) findViewById(R.id.bt_change_pass);
            Commit = (Button) findViewById(R.id.commit);
            Cancel = (Button) findViewById(R.id.cancel);
            keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            Name = (EditText) findViewById(R.id.name_t);
            Password = (EditText) findViewById(R.id.password_t);
            Email = (EditText) findViewById(R.id.email_t);
            id = (TextView) findViewById(R.id.id_t);
            Log.e("아이디", sid);
            id.setText(sid); //아이디는 로그인값으로 기본설정.
            Withdrawal.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    final AlertDialog.Builder alert = new AlertDialog.Builder(EditprofileActivity.this);
                    alert.setCancelable(false);
                    alert.setMessage(R.string.회원탈퇴비밀번호);

                    final EditText password = new EditText(EditprofileActivity.this);

                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setMaxLines(1);

                    alert.setView(password);

                    alert.setNegativeButton(R.string.취소, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
                    alert.setPositiveButton(R.string.확인, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
                    final AlertDialog dialog = alert.create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() { //버튼 눌러도 자동으로 꺼지지 않게 버튼클릭이벤트 오버라이드
                        @Override
                        public void onClick(View v) {
                            if (password.getText().length() > 0) {        //비밀번호 미입력 체크
                                String spass = password.getText().toString();
                                keyboard.hideSoftInputFromWindow(password.getWindowToken(), 0);
                                Log.d("탈퇴:id", sid);
                                Log.d("탈퇴:pass", spass);
                                networkClient.withdrawal(sid, spass, new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Log.d("탈퇴", "ㅇㅇ");

                                        switch (response.code()) {
                                            case 200:
                                                dialog.dismiss();
                                                AlertDialog.Builder alert = new AlertDialog.Builder(EditprofileActivity.this);
                                                alert.setCancelable(false);
                                                alert.setTitle(R.string.탈퇴재확인);

                                                alert.setNegativeButton(R.string.취소, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                alert.setPositiveButton(R.string.회원탈퇴, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        networkClient.realwithdrawal(sid, new Callback<String>() {
                                                            @Override
                                                            public void onResponse(Call<String> call, Response<String> response) {
                                                                switch (response.code()) {
                                                                    case 200:
                                                                        editor.putString("logintype","logout");
                                                                        editor.apply();
                                                                        Intent intent = new Intent(EditprofileActivity.this, MainActivity.class);
                                                                        startActivity(intent);
                                                                        finish();
                                                                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.회원탈퇴), Toast.LENGTH_LONG);
                                                                        toast.show();
                                                                        break;
                                                                    default:
                                                                        break;
                                                                }
                                                            }
                                                            @Override
                                                            public void onFailure(Call<String> call, Throwable t) {
                                                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                                                                toast.show();
                                                            }
                                                        });
                                                    }
                                                });
                                                alert.show();
                                                break;
                                            default:
                                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.비밀번호일치), Toast.LENGTH_LONG);
                                                toast.show();
                                                break;
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                });
                            }
                            else {
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.비밀번호입력), Toast.LENGTH_LONG);  //비밀번호 미입력시 메세지 출력.
                                toast.show();
                            }
                        }
                    });
                }
            });
            Change_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    final AlertDialog.Builder alert = new AlertDialog.Builder(EditprofileActivity.this);
                    alert.setCancelable(false);
                    alert.setMessage(R.string.비밀번호변경비밀번호);

                    final EditText password = new EditText(EditprofileActivity.this);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setMaxLines(1);
                    alert.setView(password);

                    alert.setNegativeButton(R.string.취소, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
                    alert.setPositiveButton(R.string.확인, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
                    final AlertDialog dialog = alert.create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() { //버튼 눌러도 자동으로 꺼지지 않게 버튼클릭이벤트 오버라이드
                        @Override
                        public void onClick(View v) {
                            if (password.getText().length() > 0) {
                                final String spass = password.getText().toString();
                                networkClient.withdrawal(sid, spass, new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        switch (response.code()){
                                            case 200:
                                                Intent intent = new Intent(EditprofileActivity.this, NewpasswordActivity.class);  //비밀번호 변경전에 기존비밀번호를 묻는다.
                                                startActivity(intent);
                                                dialog.dismiss();
                                                break;
                                            default:
                                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.비밀번호일치), Toast.LENGTH_LONG);
                                                toast.show();
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.비밀번호입력), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });
                }
            });
            Commit.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    String sname = Name.getText().toString();
                    String spass = Password.getText().toString();
                    String semail = Email.getText().toString();
                    Log.d("회원정보수정", sname + spass + semail);
                    networkClient.editprofile(sid, spass, sname, semail, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.회원정보수정완료), Toast.LENGTH_LONG);
                                    toast.show();
                                    Intent intent = new Intent(EditprofileActivity.this, Home_Main.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                default:
                                    Toast toast_f = Toast.makeText(getApplicationContext(), getResources().getString(R.string.비밀번호일치), Toast.LENGTH_LONG);
                                    toast_f.show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

                }
            });
            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EditprofileActivity.this, Home_Main.class);
                    startActivity(intent);
                    finish();
                }
            });
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }  //BACK버튼 비활성화

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("EditprofileActivity", "onConnectionFailed:" + connectionResult);
    }


}

