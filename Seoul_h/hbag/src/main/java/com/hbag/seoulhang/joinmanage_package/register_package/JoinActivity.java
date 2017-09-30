package com.hbag.seoulhang.joinmanage_package.register_package;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbag.seoulhang.appbase_package.BaseActivity;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends BaseActivity {

    Button commit;
    Button cancel;
    NetworkClient networkClient;
    EditText id;
    EditText password;
    EditText passwordconfirm;
    EditText name;
    EditText email;
    Ipm ipm;
    Button check;
    TextView alert;
    TextView check_id;
    Boolean check_tf;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ipm = new Ipm();
        check_tf=false; // 성별 체크 유무 확인.

        commit = (Button)findViewById(R.id.commit);
        cancel = (Button)findViewById(R.id.cancel);
        id = (EditText)findViewById(R.id.id_t);
        password = (EditText)findViewById(R.id.password_t);
        passwordconfirm = (EditText)findViewById(R.id.password_ct);
        name = (EditText)findViewById(R.id.name_t);
        email = (EditText) findViewById(R.id.email_t);
        check = (Button) findViewById(R.id.check_b);
        alert = (TextView)findViewById(R.id.alert);
        check_id = (TextView)findViewById(R.id.check_t);

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

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                check.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check.setVisibility(View.VISIBLE);
                check_id.setVisibility(View.GONE);
                alert.setText("");
                int sizeid = id.getText().length();
                if(sizeid>0)
                    check.setEnabled(true);
                else check.setEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                String sid = id.getText().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(id.getWindowToken(), 0);


                networkClient.checkid(sid,new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                            switch (response.code()) {

                                case 200:
                                   check.setVisibility(View.GONE);
                                    check_id.setVisibility(View.VISIBLE);
                                    check_id.setText(getResources().getString(R.string.사용가능));
                                    check_tf=true;
                                    break;

                                default:
                                    alert.setText(getResources().getString(R.string.please_check_already_id));
                                    break;
                            }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("ACC","s?? " + t.getMessage());
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                        toast.show();

                    }
                });


            }
        });




        commit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


                TextView alert = (TextView)findViewById(R.id.alert);
                if(id.getText().length()<=0) alert.setText(getResources().getString(R.string.EID));
                else if(!checkEmail(email.getText().toString()))alert.setText(getResources().getString(R.string.checkEmail));
                else if(!check_tf)alert.setText(getResources().getString(R.string.checkplease));
                else if(!textValidate(password.getText().toString()))alert.setText(getResources().getString(R.string.EPass));
                else if(!password.getText().toString().equals(passwordconfirm.getText().toString()))alert.setText(getResources().getString(R.string.비밀번호일치));
                else if(name.getText().length()<=0)alert.setText(getResources().getString(R.string.Ename));
                else if(email.getText().length()<=0)alert.setText(getResources().getString(R.string.EEmail));
                else if(!checkEmail(email.getText().toString()))alert.setText(getResources().getString(R.string.checkEmail));

                else {

                    String sid = id.getText().toString();
                    final String spass = password.getText().toString();
                    final String scpass = passwordconfirm.getText().toString();
                    String sname = name.getText().toString();
                    String semail = email.getText().toString();
                    String logintype = "normal";

                    networkClient.getjoin(sid, spass, sname, semail, logintype, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (spass.equals(scpass)) {
                                switch (response.code()) {
                                    case 200:
                                        String joinDTO = response.body();
                                        finish();
                                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.회원가입성공), Toast.LENGTH_LONG);
                                        toast.show();
                                        break;

                                    case 205:
                                        Log.e("TAG", "입력 제한");
                                        Toast toast_f = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_already_id), Toast.LENGTH_LONG);
                                        toast_f.show();
                                        break;

                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("ACC", "s?? " + t.getMessage());
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                            toast.show();

                        }
                    });
                }
            }
        });
    }
    public static boolean checkEmail(String email){ //이메일 유효성 체크

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isTrue = m.matches();
        return isTrue;

    }
    public boolean textValidate(String str) {  //영어+숫자+특수문자 혼합해서 6~18자 사이 비밀번호를 체크
        String Passwrod_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{6,18}$";
        Pattern pattern = Pattern.compile(Passwrod_PATTERN);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }return super.onKeyDown(keyCode, event);}  //BACK버튼 비활성화

}
