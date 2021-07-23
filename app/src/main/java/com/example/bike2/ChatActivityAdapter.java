package com.example.bike2;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        if(arrayList.get(position).getIs_estimate().equals("0")) {
            if (arrayList.get(position).getIs_right() == 1) {
                holder.container.setVisibility(View.VISIBLE);
                holder.container2.setVisibility(View.GONE);
                holder.container.setGravity(Gravity.RIGHT);
                holder.tv_nickname.setText(arrayList.get(position).getTv_nickname());
                holder.tv_chat.setText(arrayList.get(position).getTv_chat());
                holder.tv_time.setText(TimeConvert.timeconvert(arrayList.get(position).getTv_time()));
            } else {
                holder.container.setVisibility(View.VISIBLE);
                holder.container2.setVisibility(View.GONE);
                holder.container.setGravity(Gravity.LEFT);
                holder.tv_nickname.setText(arrayList.get(position).getTv_nickname());
                holder.tv_chat.setText(arrayList.get(position).getTv_chat());
                holder.tv_time.setText(TimeConvert.timeconvert(arrayList.get(position).getTv_time()));
            }
        }
        else{
            String[] arr=arrayList.get(position).getIs_estimate().split(",");
            float[] arr2=new float[5];
            float[] arr3=new float[5];
            float weight=0f;
            float price=0f;
            for(int i=0;i<5;i++){
                String[] temp=arr[i+5].split("/");
                arr2[i]=Float.parseFloat(temp[1]);
                arr3[i]=Float.parseFloat(temp[2]);
                weight=weight+Float.parseFloat(temp[1]);
                price=price+Float.parseFloat(temp[2]);
            }
            if (arrayList.get(position).getIs_right() == 1) {
                holder.container2.setVisibility(View.VISIBLE);
                holder.container.setVisibility(View.GONE);
                holder.container2.setGravity(Gravity.RIGHT);
                holder.tv_nickname2.setText(arrayList.get(position).getTv_nickname());
                holder.tv_chat2.setText("견적요청!  "+Float.toString(weight)+"kg  "+Float.toString(price)+"₩");
                holder.tv_time2.setText(TimeConvert.timeconvert(arrayList.get(position).getTv_time()));
                holder.btn_chat2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(holder.itemView.getContext(),EstimateActivity.class);
                        intent.putExtra("name",arr);
                        intent.putExtra("weight",arr2);
                        intent.putExtra("price",arr3);
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
            } else {
                holder.container2.setVisibility(View.VISIBLE);
                holder.container.setVisibility(View.GONE);
                holder.container2.setGravity(Gravity.LEFT);
                holder.tv_nickname2.setText(arrayList.get(position).getTv_nickname());
                holder.tv_chat2.setText("견적요청!  "+Float.toString(weight)+"kg "+Float.toString(price)+"₩");
                holder.tv_time2.setText(TimeConvert.timeconvert(arrayList.get(position).getTv_time()));
                holder.btn_chat2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(holder.itemView.getContext(),EstimateActivity.class);
                        intent.putExtra("name",arr);
                        intent.putExtra("weight",arr2);
                        intent.putExtra("price",arr3);
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
            }
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
        protected TextView tv_nickname2;
        protected TextView tv_time2;
        protected TextView tv_chat2;
        protected Button btn_chat2;
        protected LinearLayout container2;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_nickname=itemView.findViewById(R.id.tv_nickname);
            this.tv_time=itemView.findViewById(R.id.tv_time);
            this.tv_chat=itemView.findViewById(R.id.tv_chat);
            this.container=itemView.findViewById(R.id.container);
            this.tv_nickname2=itemView.findViewById(R.id.tv_nickname2);
            this.tv_time2=itemView.findViewById(R.id.tv_time2);
            this.tv_chat2=itemView.findViewById(R.id.tv_chat2);
            this.container2=itemView.findViewById(R.id.container2);
            this.btn_chat2=itemView.findViewById(R.id.btn_chat2);

        }
    }
}
