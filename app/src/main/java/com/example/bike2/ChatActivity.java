package com.example.bike2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private ChatActivityAdapter chatActivityAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ChatActivityData> arrayList;
    private EditText et_message;
    private Button btn_send;
    private String room_num,uid_you,uid_me;
    private String nickName_you,nickName_me;
    private String last_text,last_time;
    String name[],part[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent=getIntent();
        room_num = intent.getStringExtra("room_num");
        try {
            name=intent.getStringArrayExtra("name");
            part=intent.getStringArrayExtra("part");

        } catch (Exception e) {
            e.printStackTrace();
        }
        et_message=findViewById(R.id.et_message);
        btn_send=findViewById(R.id.btn_send);
        uid_me= FirebaseAuth.getInstance().getCurrentUser().getUid();
        recyclerView = findViewById(R.id.recycler_view_chatAct);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        chatActivityAdapter = new ChatActivityAdapter(arrayList);
        recyclerView.setAdapter(chatActivityAdapter);
        get_uid();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_message();
            }
        });
        if(name!=null){
            send_estimate();
        }
    }

    private void get_uid(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("chats").document(room_num);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if(document.get("uid_me").equals(uid_me)){
                            uid_you=document.get("uid_you").toString();
                        }
                        else{
                            uid_you=document.get("uid_me").toString();
                        }
                        get_nickname_me();
                    } else {
                    }
                } else {
                }
            }
        });
    }

    private void get_nickname_me(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(uid_me);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nickName_me=document.get("nickname").toString();
                        get_nickname_you();
                    } else {
                    }
                } else {
                }
            }
        });

    }


    private void get_nickname_you(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(uid_you);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nickName_you=document.get("nickname").toString();
                        update_chat();

                    } else {
                    }
                } else {
                }
            }
        });

    }

    /*
    private void get_previous_chat(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String a="timestam";
        db.collection("chats").document(room_num).collection("messages").orderBy(a)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ChatActivityData chatActivityData;
                                if((document.get("fromUid").toString()).equals(uid_me))
                                    chatActivityData= new ChatActivityData(nickName_me,1,document.get("text").toString(), Long.parseLong(document.get("timestam").toString()));
                                else
                                    chatActivityData= new ChatActivityData(nickName_you,0,document.get("text").toString(), Long.parseLong(document.get("timestam").toString()));
                                arrayList.add(chatActivityData);
                                chatActivityAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    */

    private void update_chat(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("chats").document(room_num).collection("messages").orderBy("timestam")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:{
                                    ChatActivityData chatActivityData;
                                    String isEstimate=dc.getDocument().get("isEstimate").toString();
                                    String temp_text=dc.getDocument().get("text").toString();
                                    String temp_time=dc.getDocument().get("timestam").toString();
                                    if((dc.getDocument().get("fromUid").toString()).equals(uid_me)){
                                        chatActivityData= new ChatActivityData(nickName_me,1,temp_text, Long.parseLong(temp_time),isEstimate);
                                    }
                                    else{
                                        chatActivityData= new ChatActivityData(nickName_you,0,temp_text, Long.parseLong(temp_time),isEstimate);
                                    }
                                    arrayList.add(chatActivityData);
                                    chatActivityAdapter.notifyDataSetChanged();
                                    last_text=temp_text;
                                    last_time=temp_time;


                                }
                                break;

                            }
                        }

                    }
                });
    }
    private void send_message(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> temp;
        temp = new HashMap<>();
        temp.put("text",et_message.getText().toString());
        temp.put("timestam",System.currentTimeMillis());
        temp.put("fromUid",uid_me);
        temp.put("isEstimate","0");
        db.collection("chats").document(room_num).collection("messages").add(temp)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }

    private void send_estimate(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> temp;
        temp = new HashMap<>();
        temp.put("text","견적요청!");
        temp.put("timestam",System.currentTimeMillis());
        temp.put("fromUid",uid_me);
        temp.put("isEstimate",name[0]+","+name[1]+","+name[2]+","+name[3]+","+name[4]+","+part[0]+","+part[1]+","+part[2]+","+part[3]+","+part[4]);
        db.collection("chats").document(room_num).collection("messages").add(temp)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        DocumentReference docRef = db.collection("chats").document(room_num);
        docRef.update("latest", last_text)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

        docRef.update("time", last_time)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }


}
