package com.example.bike2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.CustomViewHolder> {
    private final ArrayList<ChatData> arrayList;

    public ChatAdapter(ArrayList<ChatData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ChatAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        CustomViewHolder holder=new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.CustomViewHolder holder, int position) {
        holder.tv_nickname.setText(arrayList.get(position).getTv_nickname());
        holder.tv_chat.setText(arrayList.get(position).getTv_chat());
        holder.tv_time.setText(TimeConvert.timeconvert(arrayList.get(position).getTv_time()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(),ChatActivity.class);
                intent.putExtra("room_num",arrayList.get(position).getRoom_num());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return (null!=arrayList?arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_nickname;
        protected TextView tv_time;
        protected TextView tv_chat;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_nickname=itemView.findViewById(R.id.tv_nickname);
            this.tv_time=itemView.findViewById(R.id.tv_time);
            this.tv_chat=itemView.findViewById(R.id.tv_chat);
        }
    }

    public void clear(){
        arrayList.clear();
    }
}
