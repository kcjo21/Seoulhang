package com.szb.szb.start_pack.registerpack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.szb.szb.BaseActivity;
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
        keyboard =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        Name = (EditText)findViewById(R.id.name_t);
        Password = (EditText)findViewById(R.id.password_t);
        Email = (EditText)findViewById(R.id.email_t);
        id = (TextView) findViewById(R.id.id_t);
        ipm = new Ipm();
        logm = new Logm();
        final String sid = logm.getPlayerid();
        final String ip = ipm.getip();

        Log.e("아이디",sid);
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

                alert.setPositiveButton(R.string.취소, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton(R.string.확인,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                final AlertDialog dialog = alert.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (password.getText().length()>0) {
                            keyboard.hideSoftInputFromWindow(password.getWindowToken(),0);

                            AlertDialog.Builder alert = new AlertDialog.Builder(EditprofileActivity.this);
                            alert.setCancelable(false);
                            alert.setTitle(R.string.탈퇴재확인);

                            alert.setPositiveButton(R.string.취소, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                            alert.setNegativeButton(R.string.회원탈퇴, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(EditprofileActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            alert.show();
                            dialog.dismiss();
                        }
                        else {
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.비밀번호입력), Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                });
            }
        });
        Change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditprofileActivity.this, NewpasswordActivity.class);
                startActivity(intent);
            }
        });
        Commit.setOnClickListener(new View.OnClickListener() {
            String sname = Name.getText().toString();
            String spass = Password.getText().toString();
            String semail = Email.getText().toString();
            @Override
            public void onClick(View v) {
                networkClient = NetworkClient.getInstance(ip);
                networkClient.editprofile(sid, spass, sname, semail, new Callback<JoinDTO>() {
                    @Override
                    public void onResponse(Call<JoinDTO> call, Response<JoinDTO> response) {
                        switch (response.code()){
                            case 200:
                                finish();
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<JoinDTO> call, Throwable t) {
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
