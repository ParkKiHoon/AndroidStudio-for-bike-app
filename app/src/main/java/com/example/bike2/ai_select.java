package com.example.bike2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unity3d.player.Ai_Frame_Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ai_select extends AppCompatActivity {
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
    private ImageView view_home1,view_home3,view_home5,view_home7,view_home9;
    private ImageView self_back;
    private ImageView transparent_view;

    private TextView name1,name2,name3,name4,name5,value1,value2,value3,value4,value5;
    private TextView name6,name7,name8,name9,name10;


    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2,fab3;
    private TextView ft1,ft2,ft3;

    private float[] TotalPrice;
    private float[] TotalWeight;
    private int[] TotalSet;



    String[] name;
    String[] part;
    String[] image;
    float weight=0;
    float price=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_select);
        TotalPrice=new float[5];
        TotalWeight=new float[5];
        TotalSet=new int[5];
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
        name1=findViewById(R.id.name1);
        name2=findViewById(R.id.name2);
        name3=findViewById(R.id.name3);
        name4=findViewById(R.id.name4);
        name5=findViewById(R.id.name5);
        name6=findViewById(R.id.name6);
        name7=findViewById(R.id.name7);
        name8=findViewById(R.id.name8);
        name9=findViewById(R.id.name9);
        name10=findViewById(R.id.name10);
        value1=findViewById(R.id.value1);
        value2=findViewById(R.id.value2);
        value3=findViewById(R.id.value3);
        value4=findViewById(R.id.value4);
        value5=findViewById(R.id.value5);
        view_home1=findViewById(R.id.view_home_1);
        view_home3=findViewById(R.id.view_home_3);
        view_home5=findViewById(R.id.view_home_5);
        view_home7=findViewById(R.id.view_home_7);
        view_home9=findViewById(R.id.view_home_9);
        self_back=findViewById(R.id.self_back);

        transparent_view=findViewById(R.id.tranparent_view2);
        Drawable alpha = transparent_view.getDrawable();
        alpha.setAlpha(150);


        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_close);


        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton11);
        fab1 = (FloatingActionButton)findViewById(R.id.floatingActionButton22);
        fab2 = (FloatingActionButton) findViewById(R.id.floatingActionButton33);
        fab3 = (FloatingActionButton) findViewById(R.id.floatingActionButton44);
        ft1=findViewById(R.id.floatingtext22);
        ft2=findViewById(R.id.floatingtext33);
        ft3=findViewById(R.id.floatingtext44);

        view_home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressON(ai_select.this,null);
                LoadingAi("frame",1101);
            }
        });
        view_home3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressON(ai_select.this,null);
                LoadingAi("wheelset",2101);
            }
        });
        view_home5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressON(ai_select.this,null);
                LoadingAi("handlebar",3101);
            }
        });
        view_home7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressON(ai_select.this,null);
                LoadingAi("saddle",4101);
            }
        });
        view_home9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressON(ai_select.this,null);
                LoadingAi("groupset",5101);
            }
        });


        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SavePopupActivity.class);
                startActivityForResult(intent,9090);
            }
        });
        ft3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SavePopupActivity.class);
                startActivityForResult(intent,9090);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> temp=new HashMap<>();
                temp.put("image","아직 안넣음");
                temp.put("time",System.currentTimeMillis());
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
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                temp.put("name",document.get("nickname").toString());
                                db.collection("post").add(temp)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(getApplicationContext(),"공유 완료!",Toast.LENGTH_SHORT).show();
                                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error writing document", e);
                                            }
                                        });
                            } else {
                            }
                        } else {
                        }
                    }
                });
            }
        });
        ft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> temp=new HashMap<>();
                temp.put("image","아직 안넣음");
                temp.put("time",System.currentTimeMillis());
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
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                temp.put("name",document.get("nickname").toString());
                                db.collection("post").add(temp)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(getApplicationContext(),"공유 완료!",Toast.LENGTH_SHORT).show();
                                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error writing document", e);
                                            }
                                        });
                            } else {
                            }
                        } else {
                        }
                    }
                });
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("part",part);
                setResult(10010, intent);
                finish();
            }
        });
        ft1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("part",part);
                setResult(10010, intent);
                finish();
            }
        });

        self_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8080 && resultCode == 8080) {

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
                            Toast.makeText(getApplicationContext(),"저장 완료!",Toast.LENGTH_SHORT).show();
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
        else if (requestCode == 1101 && resultCode == 1101) {
            WriteReview("frame",data);
        }
        else if (requestCode == 2101 && resultCode == 1101) {
            WriteReview("wheelset",data);
        }
        else if (requestCode == 3101 && resultCode == 1101) {
            WriteReview("handlebar",data);
        }
        else if (requestCode == 4101 && resultCode == 1101) {
            WriteReview("saddle",data);
        }
        else if (requestCode == 5101 && resultCode == 1101) {
            WriteReview("groupset",data);
        }
        else if(requestCode == 1101 && resultCode == 1102){
            progressON(ai_select.this,null);
            LoadingAi("frame",1101);
        }
        else if(requestCode == 2101 && resultCode == 1102){
            progressON(ai_select.this,null);
            LoadingAi("wheelset",2101);
        }
        else if(requestCode == 3101 && resultCode == 1102){
            progressON(ai_select.this,null);
            LoadingAi("handlebar",3101);
        }
        else if(requestCode == 4101 && resultCode == 1102){
            progressON(ai_select.this,null);
            LoadingAi("saddle",4101);
        }
        else if(requestCode == 5101 && resultCode == 1102){
            progressON(ai_select.this,null);
            LoadingAi("groupset",5101);
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

    public void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
            fab.setImageResource(R.drawable.self9);
            Drawable tmp = fab.getBackground();
            tmp = DrawableCompat.wrap(tmp);
            DrawableCompat.setTint(tmp, Color.parseColor("#cecece"));
            fab.setBackground(tmp);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ft1.setVisibility(View.GONE);
                    ft2.setVisibility(View.GONE);
                    ft3.setVisibility(View.GONE);
                    transparent_view.setVisibility(View.INVISIBLE);
                }
            }, 300);
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);

            isFabOpen = true;
            fab.setImageResource(R.drawable.float2);
            Drawable tmp = fab.getBackground();
            tmp = DrawableCompat.wrap(tmp);
            DrawableCompat.setTint(tmp, Color.parseColor("#ffffff"));
            fab.setBackground(tmp);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ft1.setVisibility(View.VISIBLE);
                    ft2.setVisibility(View.VISIBLE);
                    ft3.setVisibility(View.VISIBLE);
                    transparent_view.setVisibility(View.VISIBLE);
                }
            }, 300);
        }
    }


    private void LoadingAi(String type,int requestCode){
        String[] gotFrameNames = new String[100];
        String[] gotFrameValue = new String[100];
        String[] gotFrameId = new String[100];
        String[] FrameNames = new String[100];
        String[] FrameValue = new String[100];
        String[] FrameId = new String[100];
        final int[] numOfProduct = {0};
        int[] randomNums = new int [5];
        ArrayList<ArrayList<String>> review_nickname=new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> review_content=new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> review_rating=new ArrayList<ArrayList<String>>();
        Intent intent=new Intent(getApplicationContext(), Ai_Frame_Main.class);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            numOfProduct[0] = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                gotFrameNames[numOfProduct[0]] = document.get("name").toString();
                                gotFrameId[numOfProduct[0]]=document.getId();
                                gotFrameValue[numOfProduct[0]++]=document.get("value").toString();
                            }
                            setIcon();
                        } else {
                            Log.d("Tag", "Error getting documents: ", task.getException());
                        }
                    }
                    public void setIcon() {
                        Random r = new Random();
                        for(int i=0;i<5;i++)
                        {
                            randomNums[i] = r.nextInt(numOfProduct[0]);
                            for(int j=0;j<i;j++)
                            {
                                if(randomNums[i]==randomNums[j])
                                {
                                    i--;
                                }
                            }
                        }
                        for(int i=0;i<5;i++){
                            FrameNames[i]=gotFrameNames[randomNums[i]];
                            FrameId[i]=gotFrameId[randomNums[i]];
                            FrameValue[i]=gotFrameValue[randomNums[i]];
                        }
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection(type).document(FrameId[0]).collection("review").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            ArrayList<String> data_nickname = new ArrayList<>();
                                            ArrayList<String> data_content = new ArrayList<>();
                                            ArrayList<String> data_rating = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                data_nickname.add(document.get("nickname").toString());
                                                data_content.add(document.get("content").toString());
                                                data_rating.add(document.get("rating").toString());
                                            }
                                            review_nickname.add(data_nickname);
                                            review_content.add(data_content);
                                            review_rating.add(data_rating);
                                        }
                                        db.collection(type).document(FrameId[1]).collection("review").get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                                        if (task2.isSuccessful()) {
                                                            ArrayList<String> data_nickname2 = new ArrayList<>();
                                                            ArrayList<String> data_content2 = new ArrayList<>();
                                                            ArrayList<String> data_rating2 = new ArrayList<>();
                                                            for (QueryDocumentSnapshot document : task2.getResult()) {
                                                                data_nickname2.add(document.get("nickname").toString());
                                                                data_content2.add(document.get("content").toString());
                                                                data_rating2.add(document.get("rating").toString());
                                                            }
                                                            review_nickname.add(data_nickname2);
                                                            review_content.add(data_content2);
                                                            review_rating.add(data_rating2);
                                                        }
                                                        db.collection(type).document(FrameId[2]).collection("review").get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task3) {
                                                                        if (task3.isSuccessful()) {
                                                                            ArrayList<String> data_nickname3 = new ArrayList<>();
                                                                            ArrayList<String> data_content3 = new ArrayList<>();
                                                                            ArrayList<String> data_rating3 = new ArrayList<>();
                                                                            for (QueryDocumentSnapshot document : task3.getResult()) {
                                                                                data_nickname3.add(document.get("nickname").toString());
                                                                                data_content3.add(document.get("content").toString());
                                                                                data_rating3.add(document.get("rating").toString());
                                                                            }
                                                                            review_nickname.add(data_nickname3);
                                                                            review_content.add(data_content3);
                                                                            review_rating.add(data_rating3);
                                                                        }
                                                                        db.collection(type).document(FrameId[3]).collection("review").get()
                                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task4) {
                                                                                        if (task4.isSuccessful()) {
                                                                                            ArrayList<String> data_nickname4 = new ArrayList<>();
                                                                                            ArrayList<String> data_content4 = new ArrayList<>();
                                                                                            ArrayList<String> data_rating4 = new ArrayList<>();
                                                                                            for (QueryDocumentSnapshot document : task4.getResult()) {
                                                                                                data_nickname4.add(document.get("nickname").toString());
                                                                                                data_content4.add(document.get("content").toString());
                                                                                                data_rating4.add(document.get("rating").toString());
                                                                                            }
                                                                                            review_nickname.add(data_nickname4);
                                                                                            review_content.add(data_content4);
                                                                                            review_rating.add(data_rating4);
                                                                                        }
                                                                                        db.collection(type).document(FrameId[4]).collection("review").get()
                                                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task5) {
                                                                                                        if (task5.isSuccessful()) {
                                                                                                            ArrayList<String> data_nickname5 = new ArrayList<>();
                                                                                                            ArrayList<String> data_content5 = new ArrayList<>();
                                                                                                            ArrayList<String> data_rating5 = new ArrayList<>();
                                                                                                            for (QueryDocumentSnapshot document : task5.getResult()) {
                                                                                                                data_nickname5.add(document.get("nickname").toString());
                                                                                                                data_content5.add(document.get("content").toString());
                                                                                                                data_rating5.add(document.get("rating").toString());
                                                                                                            }
                                                                                                            review_nickname.add(data_nickname5);
                                                                                                            review_content.add(data_content5);
                                                                                                            review_rating.add(data_rating5);
                                                                                                            intent.putExtra("name",FrameNames);
                                                                                                            intent.putExtra("id",FrameId);
                                                                                                            intent.putExtra("value",FrameValue);
                                                                                                            intent.putExtra("type",type);
                                                                                                            for(int i=0;i<5;i++) {

                                                                                                                intent.putStringArrayListExtra("nickname"+i, review_nickname.get(i));
                                                                                                                intent.putStringArrayListExtra("content"+i, review_content.get(i));
                                                                                                                intent.putStringArrayListExtra("rating"+i, review_rating.get(i));
                                                                                                            }
                                                                                                            progressOFF();
                                                                                                            startActivityForResult(intent,requestCode);
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                });
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    private void WriteReview(String type,Intent data){
        String id=data.getExtras().getString("id");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(type).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String name_tmp=document.get("name").toString();
                        String part_tmp=document.get("value").toString();
                        String image_tmp=document.get("image").toString();
                        String[] temp=part_tmp.split("/");
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
                        switch (type){
                            case "frame":
                                name1.setText(name_tmp); value1.setText(Float.parseFloat(temp[1])+" kg\n"+Float.parseFloat(temp[2])+" 원");
                                Glide.with(getApplicationContext()).load(image_tmp).apply(requestOptions).into(iv_home_1);
                                TotalPrice[0]=Float.parseFloat(temp[2]);
                                TotalWeight[0]=Float.parseFloat(temp[1]);
                                view_home1.setImageResource(R.drawable.self4);
                                name6.setVisibility(View.GONE);
                                TotalSet[0]=1;
                                break;
                            case "wheelset":
                                name2.setText(name_tmp); value2.setText(Float.parseFloat(temp[1])+" kg\n"+Float.parseFloat(temp[2])+" 원");
                                Glide.with(getApplicationContext()).load(image_tmp).apply(requestOptions).into(iv_home_2);
                                TotalPrice[1]=Float.parseFloat(temp[2]);
                                TotalWeight[1]=Float.parseFloat(temp[1]);
                                view_home3.setImageResource(R.drawable.self5);
                                name7.setVisibility(View.GONE);
                                TotalSet[1]=1;
                                break;
                            case "handlebar":
                                name3.setText(name_tmp); value3.setText(Float.parseFloat(temp[1])+" kg\n"+Float.parseFloat(temp[2])+" 원");
                                Glide.with(getApplicationContext()).load(image_tmp).apply(requestOptions).into(iv_home_3);
                                TotalPrice[2]=Float.parseFloat(temp[2]);
                                TotalWeight[2]=Float.parseFloat(temp[1]);
                                view_home5.setImageResource(R.drawable.self4);
                                name8.setVisibility(View.GONE);
                                TotalSet[2]=1;
                                break;
                            case "saddle":
                                name4.setText(name_tmp); value4.setText(Float.parseFloat(temp[1])+" kg\n"+Float.parseFloat(temp[2])+" 원");
                                Glide.with(getApplicationContext()).load(image_tmp).apply(requestOptions).into(iv_home_4);
                                TotalPrice[3]=Float.parseFloat(temp[2]);
                                TotalWeight[3]=Float.parseFloat(temp[1]);
                                view_home7.setImageResource(R.drawable.self5);
                                name9.setVisibility(View.GONE);
                                TotalSet[3]=1;
                                break;
                            case "groupset":
                                name5.setText(name_tmp); value5.setText(Float.parseFloat(temp[1])+" kg\n"+Float.parseFloat(temp[2])+" 원");
                                Glide.with(getApplicationContext()).load(image_tmp).apply(requestOptions).into(iv_home_5);
                                TotalPrice[4]=Float.parseFloat(temp[2]);
                                TotalWeight[4]=Float.parseFloat(temp[1]);
                                view_home9.setImageResource(R.drawable.self4);
                                name10.setVisibility(View.GONE);
                                TotalSet[4]=1;
                                break;
                        }
                        if(TotalSet[0]==1&&TotalSet[1]==1&&TotalSet[2]==1&&TotalSet[3]==1&&TotalSet[4]==1)
                            fab.setVisibility(View.VISIBLE);

                        float tmp_weight=0;
                        float tmp_price=0;
                        for(int i=0;i<5;i++){
                            tmp_weight=tmp_weight+TotalWeight[i];
                            tmp_price=tmp_price+TotalPrice[i];
                        }
                        String tmp_weight2=String.format("%.2f", tmp_weight);
                        String tmp_price2=String.format("%.0f", tmp_price);
                        tv_home_11.setText(tmp_weight2+"kg  -  "+tmp_price2+"₩");
                    } else {
                    }
                } else {
                }
            }
        });

        String content=data.getExtras().getString("content");
        Float rating=data.getExtras().getFloat("rating");
        Map<String, Object> map = new HashMap<>();
        map.put("content",content);
        map.put("rating",rating);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        map.put("nickname",document.get("nickname").toString());
                        db.collection(type).document(id).collection("review").add(map)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("TAG", "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error writing document", e);
                                    }
                                });
                    } else {
                    }
                } else {
                }
            }
        });
    }

}
