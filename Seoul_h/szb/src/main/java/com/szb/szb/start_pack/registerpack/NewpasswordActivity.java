package com.szb.szb.start_pack.registerpack;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.szb.szb.BaseActivity;
import com.szb.szb.R;
import com.szb.szb.model.retrofit.FindDTO;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;
import com.szb.szb.start_pack.MainActivity;
import com.szb.szb.start_pack.loginpackage.Logm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.szb.szb.R.id.alert;

public class NewpasswordActivity extends BaseActivity {

    EditText password;
    EditText password_c;
    Button commit;
    Button cancel;
    NetworkClient networkClient;
    Ipm ipm;
    Logm logm;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        ipm = new Ipm();
        logm = new Logm();
        commit = (Button) findViewById(R.id.commit);
        cancel = (Button) findViewById(R.id.cancel);
        password = (EditText)findViewById(R.id.password_fit);
        password_c = (EditText)findViewById(R.id.confirm_fit);
        String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                finish();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                String pass = password.getText().toString();
                String confirm  = password_c.getText().toString();
                String sid = logm.getPlayerid();


                if(!pass.equals(confirm)){
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.비밀번호일치), Toast.LENGTH_LONG);
                    toast.show();
                }

                else if(!textValidate(password.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.EPass), Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                  networkClient.newpassword(sid, pass, new Callback<FindDTO>() {
                      @Override
                      public void onResponse(Call<FindDTO> call, Response<FindDTO> response) {
                          switch (response.code()) {
                              case 200:
                                  Toast toast_s = Toast.makeText(getApplicationContext(), getResources().getText(R.string.비밀번호변경완료),Toast.LENGTH_LONG);
                                  toast_s.show();
                                  finish();
                                  break;
                              default:
                                  Toast toast_f = Toast.makeText(getApplicationContext(), getResources().getString(R.string.비밀번호일치), Toast.LENGTH_LONG);
                                  toast_f.show();
                                  break;
                          }
                      }

                      @Override
                      public void onFailure(Call<FindDTO> call, Throwable t) {
                          Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                          toast.show();
                      }
                  });
                }
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

    public boolean textValidate(String str) {  //영어+숫자+특수문자 혼합해서 6~18자 사이 비밀번호를 체크
        String Passwrod_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{6,18}$";
        Pattern pattern = Pattern.compile(Passwrod_PATTERN);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
