package com.example.bike2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyActivity extends AppCompatActivity {

    ImageView iv_my,close_my;
    TextView tv1_my,tv2_my,tv3_my,tv4_my,tv5_my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        iv_my=findViewById(R.id.iv_my);
        close_my=findViewById(R.id.close_my);
        tv1_my=findViewById(R.id.tv1_my);
        tv2_my=findViewById(R.id.tv2_my);
        tv3_my=findViewById(R.id.tv3_my);
        tv4_my=findViewById(R.id.tv4_my);
        tv5_my=findViewById(R.id.tv5_my);

        close_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if(document.get("profile")==null)
                        {
                            Glide.with(getApplicationContext()).load(document.get("image").toString()).into(iv_my);
                        }
                        else {
                            Glide.with(getApplicationContext()).load(document.get("profile").toString()).into(iv_my);
                        }
                        tv2_my.setText(document.get("nickname").toString());
                        if(document.get("isShop").toString().equals("false")){
                            tv5_my.setText("일반 이용자");
                        }
                        else{
                            tv5_my.setText("점포 운영자");
                        }
                    } else {
                    }
                } else {
                }
            }
        });

        DocumentReference docRef2 = db.collection("additionaal").document(user.getUid());
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        tv3_my.setText(document.get("birth").toString());
                        tv4_my.setText(document.get("address").toString());
                    } else {
                    }
                } else {
                }
            }
        });

        tv1_my.setText(user.getEmail());
    }
}