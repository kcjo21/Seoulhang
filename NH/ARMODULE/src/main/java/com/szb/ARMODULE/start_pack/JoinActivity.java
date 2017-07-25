package com.szb.ARMODULE.start_pack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.szb.ARMODULE.R;
import com.szb.ARMODULE.network.Ipm;
import com.szb.ARMODULE.network.NetworkClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends Activity {

    Button commit;
    Button cancel;
    NetworkClient networkClient;
    EditText id;
    EditText password;
    EditText passwordconfirm;
    EditText name;
    RadioButton men;
    RadioButton women;
    EditText age;
    EditText phone;
    EditText email;
    String gender="남";
    Ipm ipm;
    Button check;
    TextView alert;
    Boolean check_tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ipm = new Ipm();
        check_tf=false;

        commit = (Button)findViewById(R.id.commit);
        cancel = (Button)findViewById(R.id.cancel);
        id = (EditText)findViewById(R.id.id_t);
        password = (EditText)findViewById(R.id.password_t);
        passwordconfirm = (EditText)findViewById(R.id.password_ct);
        name = (EditText)findViewById(R.id.name_t);
        men = (RadioButton)findViewById(R.id.men);
        women = (RadioButton)findViewById(R.id.women);
        age = (EditText)findViewById(R.id.age_t);
        phone = (EditText) findViewById(R.id.phone_t);
        email = (EditText) findViewById(R.id.email_t);
        check = (Button) findViewById(R.id.check_b);
        alert = (TextView)findViewById(R.id.alert);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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
                String sid = id.getText().toString();
                String ip = ipm.getip();
                networkClient = NetworkClient.getInstance(ip);


                networkClient.checkid(sid,new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                            switch (response.code()) {

                                case 200:
                                   check.setVisibility(View.GONE);
                                    alert.setText(getResources().getString(R.string.사용가능));
                                    check_tf=true;


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
                String sid = id.getText().toString();
                final String spass = password.getText().toString();
                final String scpass = passwordconfirm.getText().toString();
                String sname = name.getText().toString();
                men.setChecked(true);
                men.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender = "남";
                    }
                });
                women.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender = "여";
                    }
                });
                int sage = Integer.parseInt(age.getText().toString());
                String sphone = phone.getText().toString();
                String semail = email.getText().toString();
                String ip = ipm.getip();
                Log.e("ACC","TEAM id IS !!! "+ ip);
                networkClient = NetworkClient.getInstance(ip);
                Log.e("ACC","TEAM id IS !!! "+ sid);
                TextView alert = (TextView)findViewById(R.id.alert);
                if(id.getText().length()<0) alert.setText(getResources().getString(R.string.EID));
                else if(!check_tf)alert.setText(getResources().getString(R.string.checkplease));
                else if(password.getText().length()<0)alert.setText(getResources().getString(R.string.EPass));
                else if(!password.getText().toString().equals(passwordconfirm.getText().toString()))alert.setText(getResources().getString(R.string.checkplease));
                else if(name.getText().length()<0)alert.setText(getResources().getString(R.string.Ename));
                else if(age.getText().length()<0)alert.setText(getResources().getString(R.string.Eage));
                else if(phone.getText().length()<0)alert.setText(getResources().getString(R.string.Ephone));
                else if(email.getText().length()<0)alert.setText(getResources().getString(R.string.EEmail));



                networkClient.getjoin(sid,spass,sname,gender,sage,sphone,semail,new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (spass.equals(scpass)) {
                            switch (response.code()) {
                                case 200:
                                    String joinDTO = response.body();
                                    Log.e("TAG", "team dto : " + joinDTO.toString());
                                    Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;

                                case 205:
                                    Log.e("TAG", "입력 제한");
                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_already_id), Toast.LENGTH_LONG);
                                    toast.show();
                                    break;

                            }
                        }
                        Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.비밀번호일치),Toast.LENGTH_LONG);
                        toast.show();
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
    }
}
