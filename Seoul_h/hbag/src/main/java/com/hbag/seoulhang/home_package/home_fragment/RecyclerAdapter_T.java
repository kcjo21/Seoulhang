package com.hbag.seoulhang.home_package.home_fragment;


import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbag.seoulhang.R;

import java.util.ArrayList;


/**
 * Created by cwh62 on 2017-10-09.
 */

public class RecyclerAdapter_T extends RecyclerView.Adapter<RecyclerAdapter_T.ViewHoler>{
    private ArrayList<MyData_T> mDataset;

    public static class ViewHoler extends RecyclerView.ViewHolder{

        TextView question_name;
        ImageView background;
        TextView question_code;
        TextView visit_count;
        TextView region_name;


        public ViewHoler(View view){
            super(view);
            question_name = (TextView)view.findViewById(R.id.tv_top);
            question_code = (TextView)view.findViewById(R.id.tv_num);
            visit_count = (TextView)view.findViewById(R.id.tv_visit);
            region_name = (TextView)view.findViewById(R.id.tv_region);
            background = (ImageView)view.findViewById(R.id.iv_topback);
        }


    }
    public RecyclerAdapter_T(ArrayList<MyData_T> myDataset){
        mDataset = myDataset;
    }
    @Override
    public RecyclerAdapter_T.ViewHoler onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate((R.layout.item_topten),parent,false);
        ViewHoler vh = new ViewHoler(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder,int position){

        holder.question_name.setText(mDataset.get(position).question_name);
        holder.question_code.setText(mDataset.get(position).question_code);
        holder.region_name.setText(" "+mDataset.get(position).region_name+" ");
        holder.visit_count.setText(mDataset.get(position).visit_count);

    }
    @Override
    public int getItemCount(){
        return mDataset.size();
    }

}

class MyData_T{
    public String question_code;
    public String question_name;
    public String region_name;
    public String visit_count;

    public MyData_T(String question_code, String question_name, String region_name, String visit_count){
        this.question_code = question_code;
        this.question_name = question_name;
        this.region_name = region_name;
        this.visit_count = visit_count;
    }
}
