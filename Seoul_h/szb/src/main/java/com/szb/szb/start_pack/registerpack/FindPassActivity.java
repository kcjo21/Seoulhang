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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.szb.szb.R;
import com.szb.szb.model.retrofit.FindDTO;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;
import com.szb.szb.start_pack.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindPassActivity extends AppCompatActivity {

    EditText Id;
    EditText Email;
    EditText Name;
    Button commit;
    Button cancel;
    ConstraintLayout loadinglay;
    ProgressBar progressBar;
    NetworkClient networkClient;
    Ipm ipm;
    Handler handler = new Handler();
    int value = 0; // progressBar 값
    int add = 1; // 증가량, 방향


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ipm = new Ipm();
        setContentView(R.layout.activity_find_pass);
        commit = (Button)findViewById(R.id.commit);
        cancel = (Button)findViewById(R.id.cancel);
        Id = (EditText)findViewById(R.id.id_fpt);
        Name = (EditText)findViewById(R.id.name_fpt);
        Email = (EditText)findViewById(R.id.email_fpt);
        loadinglay = (ConstraintLayout)findViewById(R.id.loadinglay);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindPassActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() { // Thread 로 작업할 내용을 구현
                        while(true) {
                            value = value + add;
                            if (value>=100 || value<=0) {
                                add = -add;
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() { // 화면에 변경하는 작업을 구현
                                    commit.setEnabled(false);
                                    cancel.setEnabled(false);
                                    Name.setEnabled(false);
                                    Id.setEnabled(false);
                                    Email.setEnabled(false);
                                    loadinglay.setVisibility(View.VISIBLE);
                                    progressBar.setProgress(value);
                                }
                            });

                            try {
                                Thread.sleep(100); // 시간지연
                            } catch (InterruptedException e) {    }
                        } // end of while
                    }
                });
                t.start(); // 쓰레드 시작

                String sname = Name.getText().toString();
                String semail = Email.getText().toString();
                String sid = Id.getText().toString();
                String ip = ipm.getip();
                networkClient = NetworkClient.getInstance(ip);
                networkClient.findpassword(sname, semail, sid, new Callback<FindDTO>() {
                    @Override
                    public void onResponse(Call<FindDTO> call, Response<FindDTO> response) {
                        switch (response.code()) {
                            case 200:

                                Intent intent = new Intent(FindPassActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            default:
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

                    }
                });
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }return super.onKeyDown(keyCode, event);}  //BACK버튼 비활성화
}
