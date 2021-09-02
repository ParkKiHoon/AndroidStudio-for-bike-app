package com.example.bike2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class Loading extends Activity {
    private ImageView loading_view;
    private SharedPreferences setting;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        setting=getSharedPreferences("setting",MODE_PRIVATE);
        editor=setting.edit();

        loading_view=findViewById(R.id.loading_view);
        Glide.with(this).load(R.drawable.logo).into(loading_view);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(FirebaseAuth.getInstance().getCurrentUser()==null||!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified() || setting.getString("auto","").equals("2"))
                    {intent=new Intent(getBaseContext(),LoginActivity.class);}
                else
                    {intent=new Intent(getBaseContext(),MainActivity.class);}
                startActivity(intent);
                finish();
            }
        },1800);

    }
}
