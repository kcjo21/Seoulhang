package com.szb.szb.start_pack.loginpackage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.szb.szb.R;

/**
 * Created by cwh62 on 2017-05-31.
 */

public class Dialog_grade extends Dialog {


    private Button commit;
    private View.OnClickListener click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_dialog_grade);


        commit = (Button)findViewById(R.id.commit);

        if(click!=null)
        commit.setOnClickListener(click);


    }
    public Dialog_grade(Context context,View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.click = singleListener;
    }

}
