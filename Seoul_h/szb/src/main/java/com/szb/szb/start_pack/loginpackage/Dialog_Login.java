package com.szb.szb.start_pack.loginpackage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.szb.szb.R;

public class Dialog_Login extends Dialog {

    private Button facebook;
    private Button kakao;
    private Button seoulhang;
    private DialogListener dialogListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_dialog_login);

        facebook = (Button)findViewById(R.id.bt_facebook);
        kakao = (Button)findViewById(R.id.bt_kakao);
        seoulhang = (Button)findViewById(R.id.bt_seoulhang);


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.customDialogListener(0);
            }
        });
        kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.customDialogListener(1);
            }
        });
        seoulhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.customDialogListener(2);
            }
        });

    }

    public interface DialogListener{
        public void customDialogListener(int data);
    }


    public Dialog_Login(Context context, DialogListener dialogListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.dialogListener  = dialogListener;
    }



}