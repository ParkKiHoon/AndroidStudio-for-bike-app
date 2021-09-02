package com.example.bike2;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("nickname", arrayList.get(position).getTv_nickname())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RequestOptions requestOptions = new RequestOptions();
                                requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
                                if(document.get("profile")==null){
                                    Glide.with(holder.itemView.getContext()).load(document.get("image")).apply(requestOptions).into(holder.iv_image);
                                }
                                else {
                                    Glide.with(holder.itemView.getContext()).load(document.get("profile")).apply(requestOptions).into(holder.iv_image);
                                }
                            }
                        } else {
                        }
                    }
                });
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
        protected ImageView iv_image;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_nickname=itemView.findViewById(R.id.tv_nickname);
            this.tv_time=itemView.findViewById(R.id.tv_time);
            this.tv_chat=itemView.findViewById(R.id.tv_chat);
            this.iv_image=itemView.findViewById(R.id.iv_image);
        }
    }

    public void clear(){
        arrayList.clear();
    }
}
