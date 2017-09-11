package com.szb.szb.start_pack.registerpack;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeinsertActivity extends BaseActivity {

    EditText codes;
    Button commit;
    Button cancel;
    NetworkClient networkClient;
    Ipm ipm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeinsert);
        ipm = new Ipm();
        commit = (Button) findViewById(R.id.commit);
        cancel = (Button) findViewById(R.id.cancel);
        codes = (EditText) findViewById(R.id.code_pt);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        codes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int sizeid = codes.getText().length();
                if(sizeid>0)
                    commit.setEnabled(true);
                else commit.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            Intent intent = getIntent();
            String sid = intent.getStringExtra("id");
            @Override
            public void onClick(View v) {
                String ip = ipm.getip();
                networkClient = NetworkClient.getInstance(ip);
                String scodes = codes.getText().toString();

                Log.e("아이디",sid);
                Log.e("코드",scodes);

                networkClient.entercode(sid, scodes, new Callback<FindDTO>() {
                    @Override
                    public void onResponse(Call<FindDTO> call, Response<FindDTO> response) {
                        switch (response.code()) {
                            case 200:
                                Intent intent = new Intent(CodeinsertActivity.this, NewpasswordActivity.class);
                                intent.putExtra("id",sid);
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
                    public void onFailure(Call<FindDTO> call, Throwable t) { Log.e("ACC", "s?? " + t.getMessage());
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
        }
        return super.onKeyDown(keyCode, event);
    }  //BACK버튼 비활성화

}
