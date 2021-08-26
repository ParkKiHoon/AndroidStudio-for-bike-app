package com.example.bike2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ai_select extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_select);

        Button frame_rec_btn = (Button) findViewById(R.id.ai_btn_frame);
        frame_rec_btn.setOnClickListener(this);

        Button wheelset_rec_btn = (Button) findViewById(R.id.ai_btn_wheelset);
        wheelset_rec_btn.setOnClickListener(this);

        Button saddle_rec_btn = (Button) findViewById(R.id.ai_btn_saddle);
        saddle_rec_btn.setOnClickListener(this);

        Button handlebar_rec_btn = (Button) findViewById(R.id.ai_btn_handlebar);
        handlebar_rec_btn.setOnClickListener(this);

//        Button groupset_rec_btn = (Button) findViewById(R.id.ai_btn_groupset);
//        groupset_rec_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.ai_btn_frame:
                intent = new Intent(getApplicationContext(), Loading_Frame_AI.class);
                startActivity(intent);
                break;
            case R.id.ai_btn_wheelset:
                intent = new Intent(getApplicationContext(), Loading_Wheelset_AI.class);
                startActivity(intent);
                break;
            case R.id.ai_btn_saddle:
                intent = new Intent(getApplicationContext(), Loading_Saddle_AI.class);
                startActivity(intent);
                break;
            case R.id.ai_btn_handlebar:
                intent = new Intent(getApplicationContext(), Loading_Handlebar_AI.class);
                startActivity(intent);
                break;
//            case R.id.ai_btn_groupset:
//                intent = new Intent(getApplicationContext(), Loading_Groupset_AI.class);
//                startActivity(intent);
//                break;
            default:
                break;
        }

    }
}
