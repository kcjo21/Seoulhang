package com.hbag.seoulhang.home_package.home_fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbag.seoulhang.home_package.Home_Main;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.model.retrofit.HintDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.hbag.seoulhang.joinmanage_package.login_package.LoginManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by cwh62 on 2017-05-03.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<MyData> mDataset;
    private HintDTO hints;
    private int grade_check;
    private Ipm ipm;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView quiz_num;
        private TextView quiz_region;
        private ImageView quiz_image;
        private TextView quiz;
        private EditText Answer;
        private RadioGroup Answer_ox;
        private Button Submit;
        private TextView hint_text;
        private Button get_hint;
        private LinearLayout background;
        private RecyclerView recyclerView;
        private RadioButton bt_o;
        private RadioButton bt_x;




        public ViewHolder(View view) {
            super(view);
            quiz_num = (TextView) view.findViewById(R.id.quiz_num);
            quiz_region = (TextView) view.findViewById(R.id.quiz_region);
            quiz_image = (ImageView) view.findViewById(R.id.quiz_image);
            quiz = (TextView) view.findViewById(R.id.quiz);
            quiz.setMovementMethod(ScrollingMovementMethod.getInstance());
            Answer = (EditText) view.findViewById(R.id.Answer);
            Answer_ox = (RadioGroup) view.findViewById(R.id.Answer_ox);
            Submit = (Button) view.findViewById(R.id.Submit);
            hint_text = (TextView)view.findViewById(R.id.hint_text);
            hint_text.setMovementMethod(ScrollingMovementMethod.getInstance());
            get_hint = (Button)view.findViewById(R.id.button_hint);
            background = (LinearLayout) view.findViewById(R.id.back);
            recyclerView = (RecyclerView) view.findViewById(R.id.rv);
            bt_o = (RadioButton)view.findViewById(R.id.rd_o);
            bt_x = (RadioButton)view.findViewById(R.id.rd_x);

        }


    }

    public RecyclerAdapter(ArrayList<MyData> myDataset) {
        mDataset = myDataset;

    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.quiz_num.setText(mDataset.get(position).text_num);
        holder.quiz_region.setText(mDataset.get(position).text_region);
        holder.quiz_image.setImageResource(mDataset.get(position).region_img);
        holder.quiz.setText(mDataset.get(position).text_quiz);
        holder.Submit.setEnabled(false);
        holder.hint_text.setText(mDataset.get(position).hint);
        holder.recyclerView = mDataset.get(position).recyclerView;
        if(mDataset.get(position).q_type.equals("ox")){                    //문제형식에 따라 UI변경
            holder.Answer_ox.setVisibility(View.VISIBLE);
            holder.Answer.setVisibility(View.GONE);
        }
        else if(mDataset.get(position).q_type.equals("default")){
            holder.Answer.setVisibility(View.VISIBLE);
            holder.Answer_ox.setVisibility(View.GONE);
        }


        switch (mDataset.get(position).region_code){
            case 1:
                holder.background.setBackgroundResource(R.color.LIGHTGREEN_);
                break;
            case 2:
                holder.background.setBackgroundResource(R.color.SKYBLUE);
                break;
            case 3:
                holder.background.setBackgroundResource(R.color.ORINGERED);
                break;
            case 4:
                holder.background.setBackgroundResource(R.color.PURPLE);
                break;
            case 5:
                holder.background.setBackgroundResource(R.color.RED);
                break;
            case 6:
                holder.background.setBackgroundResource(R.color.LIME);
                break;
            case 7:
                holder.background.setBackgroundResource(R.color.GOLD);
                break;
            case 8:
                holder.background.setBackgroundResource(R.color.Creamson);
                break;
            case 9:
                holder.background.setBackgroundResource(R.color.Green00cc66);
                break;
            case 10:
                holder.background.setBackgroundResource(R.color.LIGHTGREEN_);
                break;
            case 11:
                holder.background.setBackgroundResource(R.color.SKYBLUE);
                break;
            case 12:
                holder.background.setBackgroundResource(R.color.ORINGERED);
                break;
            case 13:
                holder.background.setBackgroundResource(R.color.PURPLE);
                break;
            case 14:
                holder.background.setBackgroundResource(R.color.RED);
                break;
            case 15:
                holder.background.setBackgroundResource(R.color.LIME);
                break;
            case 16:
                holder.background.setBackgroundResource(R.color.GOLD);
                break;
            case 17:
                holder.background.setBackgroundResource(R.color.Creamson);
                break;
            case 18:
                holder.background.setBackgroundResource(R.color.Green00cc66);
                break;
            case 19:
                holder.background.setBackgroundResource(R.color.Green00cc66);
                break;
            case 20:
                holder.background.setBackgroundResource(R.color.Green00cc66);
                break;
            case 21:
                holder.background.setBackgroundResource(R.color.Green00cc66);
                break;
            case 22:
                holder.background.setBackgroundResource(R.color.Green00cc66);
                break;
            case 23:
                holder.background.setBackgroundResource(R.color.Green00cc66);
                break;
            case 24:
                holder.background.setBackgroundResource(R.color.Green00cc66);
                break;
            case 25:
                holder.background.setBackgroundResource(R.color.Green00cc66);
                break;
        }

       holder.quiz.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {  //퀴즈텍스트 스크롤 생성
                if (holder.quiz.canScrollVertically(1)||holder.quiz.canScrollVertically(-1)) {
                    if (event.getAction() == MotionEvent.ACTION_UP)
                        holder.recyclerView.requestDisallowInterceptTouchEvent(false);
                    else
                        holder.recyclerView.requestDisallowInterceptTouchEvent(true);
                }
                return false;

            }
        });
        holder.hint_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {   //힌트 텍스트 스크롤 생성
                if (holder.hint_text.canScrollVertically(1)||holder.hint_text.canScrollVertically(-1)) {
                    if (event.getAction() == MotionEvent.ACTION_UP)
                        holder.recyclerView.requestDisallowInterceptTouchEvent(false);
                    else
                        holder.recyclerView.requestDisallowInterceptTouchEvent(true);
                }
                return false;

            }
        });

        holder.Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Methods methods;
                methods = new Methods();
                methods.progressON(mDataset.get(position).activity, mDataset.get(position).activity.getResources().getString(R.string.Loading)); //응답대기 애니메이션
                String answer="";
                ipm = new Ipm();
                String ip = ipm.getip();
                NetworkClient.getInstance(ip);
                int qun = Integer.parseInt(mDataset.get(position).text_num);
                Log.e("확인",""+qun);
                Log.e("확인",mDataset.get(position).playerid);
                if(mDataset.get(position).q_type.equals("ox")){  //퀴즈타입에 따라 반환값 타입 설정
                    if(holder.bt_o.isChecked()){
                        answer = "o";
                    }
                    else if(holder.bt_x.isChecked()){
                        answer = "x";
                    }
                }
                else if(mDataset.get(position).q_type.equals("default")){
                    answer = holder.Answer.getText().toString();
                }

                if (answer.equalsIgnoreCase(mDataset.get(position).Answer_q)) {  //해당 position에 있는 답과 입력값 비교
                    Log.e("확인",mDataset.get(position).playerid+ mDataset.get(position).text_num);
                    mDataset.get(position).networkClient.checkanswer(mDataset.get(position).playerid, qun, new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            switch (response.code()) {
                                case 200:
                                    methods.progressOFF();
                                    grade_check = response.body();
                                    Log.e("확인","aaa"+grade_check);
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Frag_Quiz.activity);
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();     //닫기
                                            Intent intent = new Intent(mDataset.get(position).activity,Home_Main.class);
                                            intent.putExtra("playerid",mDataset.get(position).playerid);
                                            mDataset.get(position).activity.startActivity(intent);
                                            mDataset.get(position).activity.finish();

                                        }
                                    });
                                    if(grade_check == 2) {
                                        methods.progressOFF();
                                        alert.setMessage(R.string.승급);
                                        alert.show();
                                    }
                                    else if(grade_check == 1) {
                                        methods.progressOFF();
                                        alert.setMessage(R.string.정답이다);
                                        alert.show();
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            methods.progressOFF();
                        }
                    });


                }

                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Frag_Quiz.activity);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                        }
                    });
                    methods.progressOFF();
                    alert.setMessage(R.string.오답);
                    alert.show();
                }

            }
        });

        ipm = new Ipm();
        final String loginid = mDataset.get(position).playerid;
        final String ip = ipm.getip();
        final int qcode = Integer.parseInt(mDataset.get(position).text_num);
        NetworkClient.getInstance(ip);

        mDataset.get(position).networkClient.gethint(loginid, qcode, new Callback<HintDTO>() {
            @Override
            public void onResponse(Call<HintDTO> call, Response<HintDTO> response) {
                hints=response.body();
                if (hints.getHintcount()==1 || hints.getHintflag()==1) {
                    holder.get_hint.setVisibility(View.GONE);
                    holder.hint_text.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<HintDTO> call, Throwable t) {

            }
        });



        holder.get_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance();
                NetworkClient.getInstance(ip);
                mDataset.get(position).networkClient.gethint(loginid,qcode, new Callback<HintDTO>() {
                    @Override
                    public void onResponse(Call<HintDTO> call, Response<HintDTO> response) {
                        Log.d("퀴즈", "123");
                        switch (response.code()) {
                            case 200:
                                hints = response.body();
                                if (hints.getHintcount()==1 || hints.getHintflag()==1) {
                                    holder.get_hint.setVisibility(View.GONE);
                                    holder.hint_text.setVisibility(View.VISIBLE);
                                } else {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Frag_Quiz.activity);
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setMessage(R.string.힌트없음);
                                    alert.show();
                                }

                                break;
                            default:
                                break;

                        }
                    }

                    @Override
                    public void onFailure(Call<HintDTO> call, Throwable t) {

                    }
                });
            }
        });


        holder.Answer_ox.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.rd_o || checkedId == R.id.rd_x){
                    holder.Submit.setEnabled(true);
                }
                else holder.Submit.setEnabled(false);
            }
        });  //체크값이 없으면 제출버튼 비활성화

        holder.Answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textsize = holder.Answer.getText().length();
                if(textsize > 0 || mDataset.get(position).q_type.equals("ox")){
                    holder.Submit.setEnabled(true);
                }
                else holder.Submit.setEnabled(false);

            }  //텍스트 입력값이 없으면 제출버튼 비활성화

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

class MyData{
    public String text_num;
    public String text_region;
    public int region_code;
    public int region_img;
    public String text_quiz;
    public String Answer_q;
    public String playerid;
    public String hint;
    public String q_type;
    Activity activity;
    NetworkClient networkClient;
    RecyclerView recyclerView;
    public MyData(String q_type, String text_num,String text_region,int region_code,int region_img,String text_quiz,String answer_q,String playerid,NetworkClient networkClient,Activity activity,String hint,RecyclerView recyclerView){
        this.q_type = q_type;
        this.text_num = text_num;
        this.text_region = text_region;
        this.region_code = region_code;
        this.region_img = region_img;
        this.text_quiz = text_quiz;
        this.Answer_q = answer_q;
        this.playerid = playerid;
        this.networkClient = networkClient;
        this.activity = activity;
        this.hint = hint;
        this.recyclerView = recyclerView;
    }


}

