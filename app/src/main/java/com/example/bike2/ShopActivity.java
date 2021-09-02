package com.example.bike2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ShopActivity extends AppCompatActivity {
    ImageView iv_shop;
    TextView tv_shop;
    TextView tv_shop2;
    Button bt_shop;
    String uid;
    String name[],part[];
    private int same=0;
    private String room_num,uid_me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Intent intent= getIntent();
        uid=intent.getStringExtra("id");
        try {
            name=intent.getStringArrayExtra("name");
            part=intent.getStringArrayExtra("part");

        } catch (Exception e) {
            e.printStackTrace();
        }
        iv_shop=findViewById(R.id.iv_shop);
        tv_shop=findViewById(R.id.tv_shop);
        tv_shop2=findViewById(R.id.tv_shop2);
        bt_shop=findViewById(R.id.bt_shop);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid_me=user.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.collection("myShop").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Glide.with(getApplicationContext()).load(document.get("image").toString()).into(iv_shop);
                        tv_shop.setText(document.get("shopname").toString());
                        tv_shop2.setText(document.get("contents").toString());
                    }
                } else {
                }
            }
        });

        bt_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_chat_room();
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() { public void run() {
                    if((!(uid_me.equals(uid)))&&same==0){
                        make_chat_room(uid_me);
                    }
                } }, 1500);

            }
        });

    }

    private void set_chat_room(){
        Intent intent=new Intent(ShopActivity.this,ChatActivity.class);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("chats").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String temp1=document.get("uid_you").toString();
                                String temp2=document.get("uid_me").toString();
                                Log.e("temp1",temp1);Log.e("temp2",temp2);
                                Log.e("uid_me",uid_me);Log.e("uid_you",uid);
                                if((temp1.equals(uid)&&temp2.equals(uid_me))||(temp1.equals(uid_me)&&temp2.equals(uid))){
                                    same++;
                                    intent.putExtra("room_num",document.getId());
                                    if(name!=null) {
                                        intent.putExtra("name", name);
                                        intent.putExtra("part", part);
                                    }
                                    startActivity(intent);
                                }
                            }
                        } else {
                        }
                    }
                });

    }

    private void make_chat_room(String uid_me){
        Map<String, Object> temp = new HashMap<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        temp.put("uid_you",uid);
        temp.put("uid_me",uid_me);
        temp.put("latest","");
        temp.put("time",System.currentTimeMillis());
        db.collection("chats").add(temp)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        room_num=documentReference.getId();
                        Intent intent=new Intent(ShopActivity.this,ChatActivity.class);
                        intent.putExtra("room_num",room_num);
                        if(name!=null) {
                            intent.putExtra("name", name);
                            intent.putExtra("part", part);
                        }
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
}