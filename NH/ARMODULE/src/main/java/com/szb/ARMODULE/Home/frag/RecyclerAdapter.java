package com.szb.ARMODULE.Home.frag;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.szb.ARMODULE.R;

import java.util.ArrayList;


/**
 * Created by cwh62 on 2017-05-03.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<MyData> mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView quiz_num;
        public TextView quiz_region;
        public TextView quiz;
        public EditText Answer;
        public Button Submit;

        public ViewHolder(View view) {
            super(view);
            quiz_num = (TextView) view.findViewById(R.id.quiz_num);
            quiz_region = (TextView) view.findViewById(R.id.quiz_region);
            quiz = (TextView) view.findViewById(R.id.quiz);
            Answer = (EditText) view.findViewById(R.id.Answer);
            Submit = (Button) view.findViewById(R.id.Submit);
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
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.quiz_num.setText(mDataset.get(position).text);
        holder.quiz_region.setText(mDataset.get(position).text2);
        holder.quiz.setText(mDataset.get(position).text3);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
class MyData{
    public String text;
    public String text2;
    public String text3;
    public MyData(String text,String text2,String text3){
        this.text = text;
        this.text2 = text2;
        this.text3 = text3;
    }


}


