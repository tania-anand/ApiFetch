package com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapplication.R;
import com.myapplication.activity.UserDetail;
import com.myapplication.model.User;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewUsersAdapter extends RecyclerView.Adapter<ViewUsersAdapter.MyViewHolder> {

    private ArrayList<User> arrayList;
    private ArrayList<User> tempList;
    private Context context;

    public ViewUsersAdapter(ArrayList <User> arrayList, Context context) {
        this.arrayList = arrayList;
        tempList = new ArrayList <>();
        tempList.addAll(arrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewUsersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user_listitem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewUsersAdapter.MyViewHolder myViewHolder,final int i) {
        myViewHolder.txtUserName.setText(arrayList.get(i).getName());
        myViewHolder.txtUserDesignation.setText(arrayList.get(i).getDesignation()+"("+arrayList.get(i).getLocation()+")");
        myViewHolder.txtSalary.setText("$"+arrayList.get(i).getSalary());


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserDetail.class);
                intent.putExtra("user",arrayList.get(i));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_user_name)
        TextView txtUserName;
        @BindView(R.id.txt_user_designation_location)
        TextView txtUserDesignation;
        @BindView(R.id.txt_salary)
        TextView txtSalary;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void filter(String str){
        arrayList.clear();

        if(str.length()==0){
            arrayList.addAll(tempList);
        }else{
            for(User user : tempList){
                if(user.getName().toLowerCase().contains(str.toLowerCase())
                        ||user.getLocation().toLowerCase().contains(str.toLowerCase())
                        ||user.getDesignation().toLowerCase().contains(str.toLowerCase())
                        ||String.valueOf(user.getSalary()).toLowerCase().contains(str.toLowerCase())
                ){
                    arrayList.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }






}
