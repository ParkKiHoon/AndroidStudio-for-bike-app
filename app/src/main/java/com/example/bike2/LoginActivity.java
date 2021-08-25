package com.example.bike2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import static java.security.AccessController.getContext;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id_in_log,et_pw_in_log;
    private ImageView btn_login_in_log;
    private TextView btn_register_in_login,find_pw;
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
        find_pw=findViewById(R.id.find_pw);

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

        find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(LoginActivity.this);
                FrameLayout container = new FrameLayout(LoginActivity.this);
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                et.setLayoutParams(params);
                container.addView(et);
                et.setHint("이메일 입력");
                et.setTextSize(24);
                et.setTextColor(Color.parseColor("#ffffff"));
                final AlertDialog.Builder alt_bld = new AlertDialog.Builder(LoginActivity.this,R.style.MyAlertDialogStyle);
                alt_bld.setTitle(" 비밀번호 찾기").setMessage("\n비밀번호 재설정 메일이 전송됩니다.").setIcon(R.drawable.email).setCancelable(false).
                        setView(container).setPositiveButton("찾기",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String value = et.getText().toString();
                                if(!value.equals(""))
                                mAuth.sendPasswordResetEmail(value);
                            }
                        });
                AlertDialog alert = alt_bld.create();
                alert.show();
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this, SelectActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else{
                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //해당 이메일에 확인메일을 보냄
//                                            AlertDialog.Builder oDialog = new AlertDialog.Builder(LoginActivity.this,
//                                                    android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
//
//                                            String strHtml =
//                                                    "<b><font color='#ff0000'>"+email+"</font></b>로 인증메일이 전송되었습니다.<br/>인증완료후 로그인이 가능합니다.";
//                                            Spanned oHtml;
//
//                                            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
//                                                // noinspection deprecation
//                                                oHtml = Html.fromHtml(strHtml);
//                                            }
//                                            else
//                                            {
//                                                oHtml = Html.fromHtml(strHtml, Html.FROM_HTML_MODE_LEGACY);
//                                            }
//
//                                            oDialog.setTitle("이메일 인증")
//                                                    .setMessage(oHtml)
//                                                    .setPositiveButton("확인", null)
//                                                    .setCancelable(false)
//                                                    .show();
                                            final TextView et = new TextView(LoginActivity.this);
                                            FrameLayout container = new FrameLayout(LoginActivity.this);
                                            FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                                            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                                            et.setLayoutParams(params);
                                            container.addView(et);
                                            et.setText(email);
                                            et.setTextSize(26);
                                            et.setTextColor(Color.parseColor("#ffffff"));
                                            final AlertDialog.Builder alt_bld = new AlertDialog.Builder(LoginActivity.this,R.style.MyAlertDialogStyle);
                                            alt_bld.setTitle(" 인증메일 전송됨").setMessage("\n이메일 인증완료후 로그인이 가능합니다.").setIcon(R.drawable.email).setCancelable(false).
                                                    setView(container).setPositiveButton("확인",null);
                                            AlertDialog alert = alt_bld.create();
                                            alert.show();

                                        } else {//메일 보내기 실패
                                            final TextView et = new TextView(LoginActivity.this);
                                            FrameLayout container = new FrameLayout(LoginActivity.this);
                                            FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                                            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                                            et.setLayoutParams(params);
                                            container.addView(et);
                                            et.setText(email);
                                            et.setTextSize(26);
                                            et.setTextColor(Color.parseColor("#ffffff"));
                                            final AlertDialog.Builder alt_bld = new AlertDialog.Builder(LoginActivity.this,R.style.MyAlertDialogStyle);
                                            alt_bld.setTitle(" 인증메일 전송됨").setMessage("\n이미 인증메일이 전송되었습니다.").setIcon(R.drawable.email).setCancelable(false).
                                                    setView(container).setPositiveButton("확인",null);
                                            AlertDialog alert = alt_bld.create();
                                            alert.show();
                                        }
                                    }
                                });
                            }
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