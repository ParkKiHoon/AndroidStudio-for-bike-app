package com.example.bike2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unity3d.player.AiMain;

import java.util.ArrayList;
import java.util.Random;

public class SelectActivity extends AppCompatActivity {

    Button btn_ai,btn_self;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent=new Intent(SelectActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document!=null) {
                        if (document.exists()) {
                            Log.e("log", "find");
                        } else {
                            Log.e("log", "No such document");
                            Intent intent = new Intent(SelectActivity.this, InformationActivity.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    Log.e("log", "get failed with ", task.getException());
                }
            }
        });
        btn_ai=findViewById(R.id.btn_ai);
        btn_self=findViewById(R.id.btn_self);
        btn_ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoadingBike.class);
                startActivity(intent);
            }
        });

        btn_self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


    }

}