package com.example.bike2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatActivityAdapter extends RecyclerView.Adapter<ChatActivityAdapter.CustomViewHolder> {
    private final ArrayList<ChatActivityData> arrayList;

    public ChatActivityAdapter(ArrayList<ChatActivityData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ChatActivityAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_activity,parent,false);
        CustomViewHolder holder=new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatActivityAdapter.CustomViewHolder holder, int position) {
        if (arrayList.get(position).getIs_right()==1) {
            holder.container.setGravity(Gravity.RIGHT);
            holder.tv_nickname.setText(arrayList.get(position).getTv_nickname());
            holder.tv_chat.setText(arrayList.get(position).getTv_chat());
            holder.tv_time.setText(TimeConvert.timeconvert(arrayList.get(position).getTv_time()));
        }
        else{
            holder.container.setGravity(Gravity.LEFT);
            holder.tv_nickname.setText(arrayList.get(position).getTv_nickname());
            holder.tv_chat.setText(arrayList.get(position).getTv_chat());
            holder.tv_time.setText(TimeConvert.timeconvert(arrayList.get(position).getTv_time()));
        }

    }


    @Override
    public int getItemCount() {
        return (null!=arrayList?arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_nickname;
        protected TextView tv_time;
        protected TextView tv_chat;
        protected LinearLayout container;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_nickname=itemView.findViewById(R.id.tv_nickname);
            this.tv_time=itemView.findViewById(R.id.tv_time);
            this.tv_chat=itemView.findViewById(R.id.tv_chat);
            this.container=itemView.findViewById(R.id.container);

        }
    }
}
