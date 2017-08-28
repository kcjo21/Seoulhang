package com.szb.szb.start_pack.registerpack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.szb.szb.R;
import com.szb.szb.start_pack.MainActivity;

public class FindIdActivity extends AppCompatActivity {

    EditText name;
    EditText Email;
    Button commit;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);
        commit = (Button)findViewById(R.id.commit);
        cancel = (Button)findViewById(R.id.cancel);
        name = (EditText)findViewById(R.id.name_fit);
        Email = (EditText)findViewById(R.id.email_fit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindIdActivity.this, MainActivity.class);
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
        }return super.onKeyDown(keyCode, event);}  //BACK버튼 비활성화
}
