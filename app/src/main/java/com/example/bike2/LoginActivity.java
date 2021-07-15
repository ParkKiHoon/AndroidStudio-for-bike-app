package com.example.bike2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id_in_log,et_pw_in_log;
    private Button btn_login_in_log,btn_register_in_login;
    private CheckBox save_account;
    private FirebaseAuth mAuth;
    private SharedPreferences setting;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        et_id_in_log=findViewById(R.id.et_id_in_log);
        et_pw_in_log=findViewById(R.id.et_pw_in_log);
        btn_login_in_log=findViewById(R.id.btn_login_in_log);
        btn_register_in_login=findViewById(R.id.btn_register_in_log);
        save_account=findViewById(R.id.save_account);

        setting=getSharedPreferences("setting",MODE_PRIVATE);
        editor=setting.edit();

        if(save_account.isChecked()){
            et_id_in_log.setText(setting.getString("id",""));
            et_pw_in_log.setText(setting.getString("pw",""));
        }


        btn_register_in_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login_in_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signIn(){
        String email=et_id_in_log.getText().toString();
        String password=et_pw_in_log.getText().toString();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            editor.putString("id",email);
                            editor.putString("pw",password);
                            editor.commit();
                            Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}