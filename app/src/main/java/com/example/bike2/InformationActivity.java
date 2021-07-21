package com.example.bike2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InformationActivity extends AppCompatActivity {

    private EditText et_in_information;
    private Button btn_in_information;
    private RadioButton radioButton,radioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        et_in_information=findViewById(R.id.et_in_information);
        btn_in_information=findViewById(R.id.btn_in_information);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        btn_in_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_in_information.getText().toString()==null){
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!radioButton.isChecked()&&!radioButton2.isChecked()) {
                    Toast.makeText(getApplicationContext(), "이용방식을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> temp = new HashMap<>();
                if(radioButton.isChecked()) {
                    temp.put("nickname",et_in_information.getText().toString());
                    temp.put("isShop", "false");
                    db.collection("users").document(user.getUid()).set(temp)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully written!");
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error writing document", e);
                                }
                            });
                }
                else {
                    Intent intent=new Intent(getApplicationContext(),ShopInformationActivity.class);
                    intent.putExtra("nickname",et_in_information.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}