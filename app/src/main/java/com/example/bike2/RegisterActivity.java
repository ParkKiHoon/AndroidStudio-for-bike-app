package com.example.bike2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {

    private EditText et_id_in_reg, et_pw_in_reg, et_pw2_in_reg;
    private Button btn_register_in_reg;
    private TextView tv_login_in_reg;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        et_id_in_reg = findViewById(R.id.et_id_in_reg);
        et_pw_in_reg = findViewById(R.id.et_pw_in_reg);
        et_pw2_in_reg = findViewById(R.id.et_pw2_in_reg);
        btn_register_in_reg = findViewById(R.id.btn_register_in_reg);
        tv_login_in_reg = findViewById(R.id.tv_login_in_reg);


        tv_login_in_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_register_in_reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!et_id_in_reg.getText().toString().contains(".")){
                    Toast.makeText(getApplicationContext(), "올바르지 않은 이메일 형식입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_pw2_in_reg.getText().toString().length()<6) {
                    Toast.makeText(getApplicationContext(), "비밀번호는 최소 6자 이상 사용하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                signUp();
            }
        });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signUp() {
        String email = et_id_in_reg.getText().toString();
        String password = et_pw_in_reg.getText().toString();
        String password2 = et_pw2_in_reg.getText().toString();
        if (password.equals(password2)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "비밀번호가 틀립니다", Toast.LENGTH_SHORT).show();
        }
    }
}