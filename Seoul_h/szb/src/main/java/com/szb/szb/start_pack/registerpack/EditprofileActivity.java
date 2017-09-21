package com.szb.szb.start_pack.registerpack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.szb.szb.BaseActivity;
import com.szb.szb.Home.Home_Main;
import com.szb.szb.R;
import com.szb.szb.model.retrofit.JoinDTO;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;
import com.szb.szb.start_pack.MainActivity;
import com.szb.szb.start_pack.loginpackage.Logm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditprofileActivity extends BaseActivity {
    Button Withdrawal;
    Button Change_pass;
    Button Commit;
    Button Cancel;
    EditText Password;
    EditText Email;
    EditText Name;
    InputMethodManager keyboard;
    TextView id;
    NetworkClient networkClient;
    Ipm ipm;
    Logm logm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        Withdrawal = (Button) findViewById(R.id.bt_Withdrawal);
        Change_pass = (Button) findViewById(R.id.bt_change_pass);
        Commit = (Button) findViewById(R.id.commit);
        Cancel = (Button) findViewById(R.id.cancel);
        keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        Name = (EditText) findViewById(R.id.name_t);
        Password = (EditText) findViewById(R.id.password_t);
        Email = (EditText) findViewById(R.id.email_t);
        id = (TextView) findViewById(R.id.id_t);
        ipm = new Ipm();
        logm = new Logm();
        final String sid = logm.getPlayerid();
        final String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);

        Log.e("아이디", sid);
        id.setText(sid); //아이디는 로그인값으로 기본설정.

        Withdrawal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }  //BACK버튼 비활성화

}