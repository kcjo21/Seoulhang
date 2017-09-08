package com.szb.szb.start_pack.registerpack;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.szb.szb.R;
import com.szb.szb.model.retrofit.FindDTO;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;
import com.szb.szb.start_pack.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindIdActivity extends AppCompatActivity {

    EditText name;
    EditText Email;
    Button commit;
    Button cancel;
    ProgressBar progressBar;
    ConstraintLayout loadinglay;
    NetworkClient networkClient;
    Ipm ipm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);
        ipm = new Ipm();
        commit = (Button)findViewById(R.id.commit);
        cancel = (Button)findViewById(R.id.cancel);
        name = (EditText)findViewById(R.id.name_fit);
        Email = (EditText)findViewById(R.id.email_fit);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        loadinglay = (ConstraintLayout)findViewById(R.id.loadinglay);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindIdActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().length() <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.Ename), Toast.LENGTH_LONG);
                    toast.show();
                } else if (Email.getText().length() <= 0) {
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.EEmail), Toast.LENGTH_LONG);
                toast.show();
                } else if (!checkEmail(Email.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.checkEmail), Toast.LENGTH_LONG);
                    toast.show();
                }

                else {
                    commit.setEnabled(false);

                    cancel.setEnabled(false);
                    name.setEnabled(false);
                    Email.setEnabled(false);
                    loadinglay.setVisibility(View.VISIBLE);

                    String sname = name.getText().toString();
                    String semail = Email.getText().toString();
                    String ip = ipm.getip();
                    networkClient = NetworkClient.getInstance(ip);
                    networkClient.findid(sname, semail, new Callback<FindDTO>() {
                        @Override
                        public void onResponse(Call<FindDTO> call, Response<FindDTO> response) {
                            switch (response.code()) {
                                case 200:
                                    Intent intent = new Intent(FindIdActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                default:
                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.아이디찾을수없음), Toast.LENGTH_LONG);
                                    toast.show();
                                    break;

                            }
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.확인메일보냄), Toast.LENGTH_LONG);
                            toast.show();
                        }

                        @Override
                        public void onFailure(Call<FindDTO> call, Throwable t) {
                            Log.e("ACC", "s?? " + t.getMessage());
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                            toast.show();
                            commit.setEnabled(true);
                            cancel.setEnabled(true);
                            name.setEnabled(true);
                            Email.setEnabled(true);
                            loadinglay.setVisibility(View.GONE);

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
        }return super.onKeyDown(keyCode, event);}  //BACK버튼 비활성화

    public static boolean checkEmail(String email){

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isTrue = m.matches();
        return isTrue;

    }
}