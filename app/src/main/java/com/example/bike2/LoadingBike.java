package com.example.bike2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.unity3d.player.Ai_Frame_Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoadingBike extends Activity {
    private ImageView loading_bike;
    private String[] gotFrameNames = new String[100];
    private String[] gotFrameValue = new String[100];
    private String[] gotFrameId = new String[100];
    private String[] FrameNames = new String[100];
    private String[] FrameValue = new String[100];
    private String[] FrameId = new String[100];
    private int numOfProduct = 0;
    private int[] randomNums = new int [5];
    private ArrayList<ArrayList<String>> review_nickname=new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> review_content=new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> review_rating=new ArrayList<ArrayList<String>>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_bike);

        //
        //현재 미사용중입니다.
        //LoadingBike Activity에서 모든것을 대체합니다.
        //
        loading_bike=findViewById(R.id.loading_bike);
        Glide.with(this).load(R.drawable.frame_loading).into(loading_bike);

        Intent intent=new Intent(getApplicationContext(), Ai_Frame_Main.class);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("frame")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            numOfProduct = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                gotFrameNames[numOfProduct] = document.get("name").toString();
                                gotFrameId[numOfProduct]=document.getId();
                                gotFrameValue[numOfProduct++]=document.get("value").toString();
                            }
                            setIcon();
                        } else {
                            Log.d("Tag", "Error getting documents: ", task.getException());
                        }
                    }
                    public void setIcon() {
                        Random r = new Random();
                        for(int i=0;i<5;i++)
                        {
                            randomNums[i] = r.nextInt(numOfProduct);
                            for(int j=0;j<i;j++)
                            {
                                if(randomNums[i]==randomNums[j])
                                {
                                    i--;
                                }
                            }
                        }
                        for(int i=0;i<5;i++){
                            FrameNames[i]=gotFrameNames[randomNums[i]];
                            FrameId[i]=gotFrameId[randomNums[i]];
                            FrameValue[i]=gotFrameValue[randomNums[i]];
                        }
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("frame").document(FrameId[0]).collection("review").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            ArrayList<String> data_nickname = new ArrayList<>();
                                            ArrayList<String> data_content = new ArrayList<>();
                                            ArrayList<String> data_rating = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                data_nickname.add(document.get("nickname").toString());
                                                data_content.add(document.get("content").toString());
                                                data_rating.add(document.get("rating").toString());
                                            }
                                            review_nickname.add(data_nickname);
                                            review_content.add(data_content);
                                            review_rating.add(data_rating);
                                        }
                                        db.collection("frame").document(FrameId[1]).collection("review").get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                                        if (task2.isSuccessful()) {
                                                            ArrayList<String> data_nickname2 = new ArrayList<>();
                                                            ArrayList<String> data_content2 = new ArrayList<>();
                                                            ArrayList<String> data_rating2 = new ArrayList<>();
                                                            for (QueryDocumentSnapshot document : task2.getResult()) {
                                                                data_nickname2.add(document.get("nickname").toString());
                                                                data_content2.add(document.get("content").toString());
                                                                data_rating2.add(document.get("rating").toString());
                                                            }
                                                            review_nickname.add(data_nickname2);
                                                            review_content.add(data_content2);
                                                            review_rating.add(data_rating2);
                                                        }
                                                        db.collection("frame").document(FrameId[2]).collection("review").get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task3) {
                                                                        if (task3.isSuccessful()) {
                                                                            ArrayList<String> data_nickname3 = new ArrayList<>();
                                                                            ArrayList<String> data_content3 = new ArrayList<>();
                                                                            ArrayList<String> data_rating3 = new ArrayList<>();
                                                                            for (QueryDocumentSnapshot document : task3.getResult()) {
                                                                                data_nickname3.add(document.get("nickname").toString());
                                                                                data_content3.add(document.get("content").toString());
                                                                                data_rating3.add(document.get("rating").toString());
                                                                            }
                                                                            review_nickname.add(data_nickname3);
                                                                            review_content.add(data_content3);
                                                                            review_rating.add(data_rating3);
                                                                        }
                                                                        db.collection("frame").document(FrameId[3]).collection("review").get()
                                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task4) {
                                                                                        if (task4.isSuccessful()) {
                                                                                            ArrayList<String> data_nickname4 = new ArrayList<>();
                                                                                            ArrayList<String> data_content4 = new ArrayList<>();
                                                                                            ArrayList<String> data_rating4 = new ArrayList<>();
                                                                                            for (QueryDocumentSnapshot document : task4.getResult()) {
                                                                                                data_nickname4.add(document.get("nickname").toString());
                                                                                                data_content4.add(document.get("content").toString());
                                                                                                data_rating4.add(document.get("rating").toString());
                                                                                            }
                                                                                            review_nickname.add(data_nickname4);
                                                                                            review_content.add(data_content4);
                                                                                            review_rating.add(data_rating4);
                                                                                        }
                                                                                        db.collection("frame").document(FrameId[4]).collection("review").get()
                                                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task5) {
                                                                                                        if (task5.isSuccessful()) {
                                                                                                            ArrayList<String> data_nickname5 = new ArrayList<>();
                                                                                                            ArrayList<String> data_content5 = new ArrayList<>();
                                                                                                            ArrayList<String> data_rating5 = new ArrayList<>();
                                                                                                            for (QueryDocumentSnapshot document : task5.getResult()) {
                                                                                                                data_nickname5.add(document.get("nickname").toString());
                                                                                                                data_content5.add(document.get("content").toString());
                                                                                                                data_rating5.add(document.get("rating").toString());
                                                                                                            }
                                                                                                            review_nickname.add(data_nickname5);
                                                                                                            review_content.add(data_content5);
                                                                                                            review_rating.add(data_rating5);
                                                                                                            intent.putExtra("name",FrameNames);
                                                                                                            intent.putExtra("id",FrameId);
                                                                                                            intent.putExtra("value",FrameValue);
                                                                                                            for(int i=0;i<5;i++) {
                                                                                                                intent.putStringArrayListExtra("nickname"+i, review_nickname.get(i));
                                                                                                                intent.putStringArrayListExtra("content"+i, review_content.get(i));
                                                                                                                intent.putStringArrayListExtra("rating"+i, review_rating.get(i));
                                                                                                            }
                                                                                                            startActivityForResult(intent,1101);
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                });
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1101 && resultCode == 1101) {
            String content=data.getExtras().getString("content");
            Float rating=data.getExtras().getFloat("rating");
            String id=data.getExtras().getString("id");
            Map<String, Object> map = new HashMap<>();
            map.put("content",content);
            map.put("rating",rating);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            map.put("nickname",document.get("nickname").toString());
                            db.collection("frame").document(id).collection("review").add(map)
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
                        } else {
                        }
                    } else {
                    }
                }
            });

        }
        else if(requestCode == 1101 && resultCode == 1102){
            finish();
            startActivity(new Intent(this, LoadingBike.class));
        }
    }
}
