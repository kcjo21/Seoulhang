package com.szb.szb.start_pack.registerpack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.szb.szb.R;
import com.szb.szb.model.retrofit.FindDTO;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;
import com.szb.szb.start_pack.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewpasswordActivity extends AppCompatActivity {

    EditText password;
    EditText password_c;
    Button commit;
    Button cancel;
    NetworkClient networkClient;
    Ipm ipm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        ipm = new Ipm();
        commit = (Button) findViewById(R.id.commit);
        cancel = (Button) findViewById(R.id.cancel);
        password = (EditText)findViewById(R.id.password_fit);
        password_c = (EditText)findViewById(R.id.confirm_fit);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewpasswordActivity.this, CodeinsertActivity.class);
                startActivity(intent);
                finish();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = ipm.getip();
                String pass = password.getText().toString();
                String confirm  = password_c.getText().toString();
                Intent intent = getIntent();
                String sid = intent.getStringExtra("id");

                networkClient = NetworkClient.getInstance(ip);
                if(!pass.equals(confirm)){
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.비밀번호일치), Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                  networkClient.newpassword(sid, pass, new Callback<FindDTO>() {
                      @Override
                      public void onResponse(Call<FindDTO> call, Response<FindDTO> response) {
                          switch (response.code()) {
                              case 200:
                                  Intent intent = new Intent(NewpasswordActivity.this, MainActivity.class);
                                  startActivity(intent);
                                  finish();
                                  break;
                              default:
                                  Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.코드불일치), Toast.LENGTH_LONG);
                                  toast.show();
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
}
