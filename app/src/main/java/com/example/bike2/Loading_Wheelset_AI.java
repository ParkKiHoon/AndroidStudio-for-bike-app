package com.example.bike2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class Loading_Wheelset_AI extends Activity {
    private ImageView loading_bike;
    private String[] gotWheelsetNames = new String[100];
    private String[] gotWheelsetValue = new String[100];
    private String[] gotWheelsetId = new String[100];
    private String[] WheelsetNames = new String[100];
    private String[] WheelsetValue = new String[100];
    private String[] WheelsetId = new String[100];
    private int numOfProduct = 0;
    private int[] randomNums = new int [5];
    private ArrayList<ArrayList<String>> review_nickname=new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> review_content=new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> review_rating=new ArrayList<ArrayList<String>>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_bike);

        loading_bike=findViewById(R.id.loading_bike);
        Glide.with(this).load(R.drawable.loading).into(loading_bike);

        Intent intent=new Intent(getApplicationContext(), Ai_Frame_Main.class);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("wheelset")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            numOfProduct = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                gotWheelsetNames[numOfProduct] = document.get("name").toString();
                                gotWheelsetId[numOfProduct]=document.getId();
                                gotWheelsetValue[numOfProduct++]=document.get("value").toString();
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
                            WheelsetNames[i]=gotWheelsetNames[randomNums[i]];
                            WheelsetId[i]=gotWheelsetId[randomNums[i]];
                            WheelsetValue[i]=gotWheelsetValue[randomNums[i]];
                        }
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("wheelset").document(WheelsetId[0]).collection("review").get()
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
                                        db.collection("wheelset").document(WheelsetId[1]).collection("review").get()
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
                                                        db.collection("wheelset").document(WheelsetId[2]).collection("review").get()
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
                                                                        db.collection("wheelset").document(WheelsetId[3]).collection("review").get()
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
                                                                                        db.collection("wheelset").document(WheelsetId[4]).collection("review").get()
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
                                                                                                            intent.putExtra("name",WheelsetNames);
                                                                                                            intent.putExtra("id",WheelsetId);
                                                                                                            intent.putExtra("value",WheelsetValue);
                                                                                                            intent.putExtra("type","wheelset");
                                                                                                            for(int i=0;i<5;i++) {

                                                                                                                intent.putStringArrayListExtra("nickname"+i, review_nickname.get(i));
                                                                                                                intent.putStringArrayListExtra("content"+i, review_content.get(i));
                                                                                                                intent.putStringArrayListExtra("rating"+i, review_rating.get(i));
                                                                                                            }
                                                                                                            startActivityForResult(intent,2101);
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
        if (requestCode == 2101 && resultCode == 1101) {
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
                            db.collection("wheelset").document(id).collection("review").add(map)
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
        else if(requestCode == 2101 && resultCode == 1102){
            finish();
            startActivity(new Intent(this, Loading_Wheelset_AI.class));
        }
    }
}
