package com.hbag.seoulhang.home_package;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cwh62 on 2017-05-31.
 */

public class GradeDialog extends Dialog {


    private Button commit;
    private View.OnClickListener click;
    NetworkClient networkClient;
    Ipm ipm;
    UserProfileData_singleton profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_grade_dialog);

        profile = UserProfileData_singleton.getInstance();

        ipm= new Ipm();
        String ip = ipm.getip();
        String loginid = profile.getId();

        networkClient = NetworkClient.getInstance();

        commit = (Button)findViewById(R.id.commit);

        if(click!=null)
            commit.setOnClickListener(click);
        commit.setTag(0);

        networkClient.grade(loginid, new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                switch (response.code()){
                    case 200:
                        List<Integer> grade_rate;
                        grade_rate = response.body();
                        for(int i = 0 ;i<grade_rate.size();i++){
                            grade_rate.get(i);
                            Log.d("리스트",grade_rate.get(i).toString());

                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {

            }
        });


    }
    public GradeDialog(Context context, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.click = singleListener;
    }

}
