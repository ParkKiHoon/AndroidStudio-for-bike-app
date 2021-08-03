package com.example.bike2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class AiSub extends AppCompatActivity implements Serializable {
    TextView tv_ai_sub;
    ImageView show_image1;
    Button btn_ai_sub,btn_ai_sub2;
    EditText et_ai_sub;
    String cur_parts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_sub);

        Intent myintent = getIntent();
        String firstName = myintent.getStringExtra("1등이름");
        float rating =myintent.getFloatExtra("rate",2.5f);

        tv_ai_sub=findViewById(R.id.tv_ai_sub);
        btn_ai_sub=findViewById(R.id.btn_ai_sub);
        btn_ai_sub2=findViewById(R.id.btn_ai_sub2);
        et_ai_sub=findViewById(R.id.et_ai_sub);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("frame")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String Name = document.getData().toString().split(",")[1].substring(6);

                                if(Name.equals(firstName) ){
                                    cur_parts=document.getId();
                                    String url = document.getData().toString().split(",")[0].substring(7);
                                    tv_ai_sub.setText(firstName+" / "+document.get("value").toString().split("/")[1]+"kg / "+document.get("value").toString().split("/")[2]+" ₩");
                                    setImage1(url);
                                }
                            } ;
                        } else {
                            Log.d("Tag", "Error getting documents: ", task.getException());
                        }
                    }
                    public void setImage1(String url){
                        show_image1 = (ImageView)findViewById(R.id.product_image1);
                        Glide.with(AiSub.this).load(url).into(show_image1);

                    }
                });

        btn_ai_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("content",et_ai_sub.getText().toString());
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
                                db.collection("frame").document(cur_parts).collection("review").add(map)
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
        });

        btn_ai_sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AiMain.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

}
