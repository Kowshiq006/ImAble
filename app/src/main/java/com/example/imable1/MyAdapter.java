package com.example.imable1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHOlder> {

    Context context;
    ArrayList<User> userArrayList;

    public MyAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);


        return new MyViewHOlder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHOlder holder, int position) {

        User user =userArrayList.get(position);
        holder.title.setText(user.getActivity());
        holder.date.setText(user.getDate());
        holder.time.setText(user.getTime());

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


    public static class MyViewHOlder extends RecyclerView.ViewHolder{

        TextView title,date,time;



        public MyViewHOlder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titleb);
            date=itemView.findViewById(R.id.dateb);
            time=itemView.findViewById(R.id.timeb);
        }
    }



}


