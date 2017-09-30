package com.hbag.seoulhang.home_package;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.hbag.seoulhang.R;

/**
 * Created by cwh62 on 2017-05-31.
 */

public class GradeDialog extends Dialog {


    private Button commit;
    private View.OnClickListener click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_grade_dialog);

        commit = (Button)findViewById(R.id.commit);

        if(click!=null)
        commit.setOnClickListener(click);
        commit.setTag(0);


    }
    public GradeDialog(Context context, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.click = singleListener;
    }

}
