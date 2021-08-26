package com.example.bike2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unity3d.player.AiMain;
import com.unity3d.player.UnityPlayerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoadingSelf extends Activity {
    AppCompatDialog progressDialog;
    private View view;
    private Button button;
    private Button button_home;
    private Button button_home1;
    private Button button_home2;
    private ImageView iv_home_1;
    private ImageView iv_home_2;
    private ImageView iv_home_3;
    private ImageView iv_home_4;
    private ImageView iv_home_5;
    private TextView tv_home_1;
    private TextView tv_home_3;
    private TextView tv_home_5;
    private TextView tv_home_7;
    private TextView tv_home_9;
    private TextView tv_home_11;

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;

    String[] name;
    String[] part;
    String[] image;
    float weight=0;
    float price=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_self);
        progressON(this,null);

        iv_home_1=findViewById(R.id.iv_home_1);
        iv_home_2=findViewById(R.id.iv_home_2);
        iv_home_3=findViewById(R.id.iv_home_3);
        iv_home_4=findViewById(R.id.iv_home_4);
        iv_home_5=findViewById(R.id.iv_home_5);
        tv_home_1=findViewById(R.id.tv_home_1);
        tv_home_3=findViewById(R.id.tv_home_3);
        tv_home_5=findViewById(R.id.tv_home_5);
        tv_home_7=findViewById(R.id.tv_home_7);
        tv_home_9=findViewById(R.id.tv_home_9);
        tv_home_11=findViewById(R.id.tv_home_11);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_close);

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab1 = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fab2 = (FloatingActionButton) findViewById(R.id.floatingActionButton4);


        Intent intent = new Intent(getApplicationContext(), AiMain.class);
        final boolean[] cnt = new boolean[5];
        ArrayList<String> frame_name=new ArrayList<String>();
        ArrayList<String> frame_value=new ArrayList<String>();
        ArrayList<String> frame_image=new ArrayList<String>();
        ArrayList<String> wheelset_name =new ArrayList<String>();
        ArrayList<String> wheelset_value =new ArrayList<String>();
        ArrayList<String> wheelset_image =new ArrayList<String>();
        ArrayList<String> handlebar_name =new ArrayList<String>();
        ArrayList<String> handlebar_value = new ArrayList<String>();
        ArrayList<String> handlebar_image = new ArrayList<String>();
        ArrayList<String> saddle_name =new ArrayList<String>();
        ArrayList<String> saddle_value = new ArrayList<String>();
        ArrayList<String> saddle_image = new ArrayList<String>();
        ArrayList<String> groupset_name = new ArrayList<String>();
        ArrayList<String> groupset_value = new ArrayList<String>();
        ArrayList<String> groupset_image = new ArrayList<String>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("frame")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                frame_name.add(document.get("name").toString());
                                frame_value.add(document.get("value").toString());
                                frame_image.add(document.get("image").toString());
                                //  getStock(document.get("name").toString(),1);

                            }
                            db.collection("wheelset")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                            if (task2.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task2.getResult()) {
                                                    wheelset_name.add(document.get("name").toString());
                                                    wheelset_value.add(document.get("value").toString());
                                                    wheelset_image.add(document.get("image").toString());
                                                    // getStock(document.get("name").toString(),2);
                                                }
                                                db.collection("handlebar")
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task3) {
                                                                if (task3.isSuccessful()) {
                                                                    for (QueryDocumentSnapshot document : task3.getResult()) {
                                                                        handlebar_name.add(document.get("name").toString());
                                                                        handlebar_value.add(document.get("value").toString());
                                                                        handlebar_image.add(document.get("image").toString());
                                                                        //getStock(document.get("name").toString(),3);
                                                                    }
                                                                    db.collection("saddle")
                                                                            .get()
                                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task4) {
                                                                                    if (task4.isSuccessful()) {
                                                                                        for (QueryDocumentSnapshot document : task4.getResult()) {
                                                                                            saddle_name.add(document.get("name").toString());
                                                                                            saddle_value.add(document.get("value").toString());
                                                                                            saddle_image.add(document.get("image").toString());
                                                                                            //getStock(document.get("name").toString(),4);
                                                                                        }
                                                                                        db.collection("groupset")
                                                                                                .get()
                                                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task5) {
                                                                                                        if (task5.isSuccessful()) {
                                                                                                            for (QueryDocumentSnapshot document : task5.getResult()) {
                                                                                                                groupset_name.add(document.get("name").toString());
                                                                                                                groupset_value.add(document.get("value").toString());
                                                                                                                groupset_image.add(document.get("image").toString());
                                                                                                            }
                                                                                                            Intent intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                                                                                                            intent.putStringArrayListExtra("frame_name",frame_name);
                                                                                                            intent.putStringArrayListExtra("frame_value",frame_value);
                                                                                                            intent.putStringArrayListExtra("frame_image",frame_image);
                                                                                                            intent.putStringArrayListExtra("wheelset_name",wheelset_name);
                                                                                                            intent.putStringArrayListExtra("wheelset_value",wheelset_value);
                                                                                                            intent.putStringArrayListExtra("wheelset_image",wheelset_image);
                                                                                                            intent.putStringArrayListExtra("handlebar_name",handlebar_name);
                                                                                                            intent.putStringArrayListExtra("handlebar_value",handlebar_value);
                                                                                                            intent.putStringArrayListExtra("handlebar_image",handlebar_image);
                                                                                                            intent.putStringArrayListExtra("saddle_name",saddle_name);
                                                                                                            intent.putStringArrayListExtra("saddle_value",saddle_value);
                                                                                                            intent.putStringArrayListExtra("saddle_image",saddle_image);
                                                                                                            intent.putStringArrayListExtra("groupset_name",groupset_name);
                                                                                                            intent.putStringArrayListExtra("groupset_value",groupset_value);
                                                                                                            intent.putStringArrayListExtra("groupset_image",groupset_image);
                                                                                                            startActivityForResult(intent,8080);
                                                                                                            progressOFF();
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8080 && resultCode == 8080) {
            name=data.getExtras().getStringArray("name");
            part=data.getExtras().getStringArray("part");
            image=data.getExtras().getStringArray("image");
            for(int i=0;i<5;i++){
                String[] temp=part[i].split("/");
                weight+=Float.parseFloat(temp[1]);
                price+=Float.parseFloat(temp[2]);
                switch (i){
                    case 0:tv_home_1.setText(name[i]+"\n"+Float.parseFloat(temp[1])+"kg "+Float.parseFloat(temp[2])+"₩");
                        Glide.with(getApplicationContext()).load(image[i]).into(iv_home_1); break;
                    case 1:tv_home_3.setText(name[i]+"\n"+Float.parseFloat(temp[1])+"kg "+Float.parseFloat(temp[2])+"₩");
                        Glide.with(getApplicationContext()).load(image[i]).into(iv_home_2); break;
                    case 2:tv_home_5.setText(name[i]+"\n"+Float.parseFloat(temp[1])+"kg "+Float.parseFloat(temp[2])+"₩");
                        Glide.with(getApplicationContext()).load(image[i]).into(iv_home_3); break;
                    case 3:tv_home_7.setText(name[i]+"\n"+Float.parseFloat(temp[1])+"kg "+Float.parseFloat(temp[2])+"₩");
                        Glide.with(getApplicationContext()).load(image[i]).into(iv_home_4); break;
                    case 4:tv_home_9.setText(name[i]+"\n"+Float.parseFloat(temp[1])+"kg "+Float.parseFloat(temp[2])+"₩");
                        Glide.with(getApplicationContext()).load(image[i]).into(iv_home_5); break;
                }
            }
            tv_home_11.setText(Float.toString(weight)+"kg  -  "+Float.toString(price)+"₩");

        }
        else if(requestCode == 9090 && resultCode == 9090){
            Map<String, Object> temp=new HashMap<>();
            temp.put("title",data.getExtras().getString("result"));
            temp.put("image","아직 안넣음");
            temp.put("weight_price",Float.toString(weight)+"kg  -  "+Float.toString(price)+"₩");
            temp.put("frame_name",name[0]);
            temp.put("wheelset_name",name[1]);
            temp.put("handlebar_name",name[2]);
            temp.put("saddle_name",name[3]);
            temp.put("groupset_name",name[4]);
            temp.put("frame_value",part[0]);
            temp.put("wheelset_value",part[1]);
            temp.put("handlebar_value",part[2]);
            temp.put("saddle_value",part[3]);
            temp.put("groupset_value",part[4]);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.collection("myCustom").add(temp)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("TAG", "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "Error writing document", e);
                        }
                    });
        }
    }

    public void progressON(Activity activity, String message) {

        if (activity == null || activity.isFinishing()) {
            return;
        }


        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {

            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_loading);
            progressDialog.show();

        }


        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }


    }

    public void progressSET(String message) {

        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }

    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
