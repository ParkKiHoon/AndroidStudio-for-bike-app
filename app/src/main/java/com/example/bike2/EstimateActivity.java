package com.example.bike2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EstimateActivity extends AppCompatActivity {
    TextView tv_estimate1,tv_estimate2,tv_estimate3,tv_estimate4,tv_estimate5
            ,tv_estimate6,tv_estimate7,tv_estimate8,tv_estimate9,tv_estimate10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate);
        Intent intent=getIntent();
        String[] name=intent.getStringArrayExtra("name");
        float[] weight=intent.getFloatArrayExtra("weight");
        float[] price=intent.getFloatArrayExtra("price");
        tv_estimate1=findViewById(R.id.tv_estimate1);
        tv_estimate2=findViewById(R.id.tv_estimate2);
        tv_estimate3=findViewById(R.id.tv_estimate3);
        tv_estimate4=findViewById(R.id.tv_estimate4);
        tv_estimate5=findViewById(R.id.tv_estimate5);
        tv_estimate6=findViewById(R.id.tv_estimate6);
        tv_estimate7=findViewById(R.id.tv_estimate7);
        tv_estimate8=findViewById(R.id.tv_estimate8);
        tv_estimate9=findViewById(R.id.tv_estimate9);
        tv_estimate10=findViewById(R.id.tv_estimate10);
        tv_estimate1.setText(name[0]);
        tv_estimate2.setText(name[1]);
        tv_estimate3.setText(name[2]);
        tv_estimate4.setText(name[3]);
        tv_estimate5.setText(name[4]);
        tv_estimate6.setText("      "+Float.toString(weight[0])+"kg\n"+Float.toString(price[0])+"원");
        tv_estimate7.setText("      "+Float.toString(weight[1])+"kg\n"+Float.toString(price[1])+"원");
        tv_estimate8.setText("      "+Float.toString(weight[2])+"kg\n"+Float.toString(price[2])+"원");
        tv_estimate9.setText("      "+Float.toString(weight[3])+"kg\n"+Float.toString(price[3])+"원");
        tv_estimate10.setText("      "+Float.toString(weight[4])+"kg\n"+Float.toString(price[4])+"원");
    }
}