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
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.Glide;
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
    private ImageView view_home1,view_home3,view_home5,view_home7,view_home9;
    private ImageView self_back;
    private ImageView transparent_view;

    private TextView name1,name2,name3,name4,name5,value1,value2,value3,value4,value5;


    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2,fab3;
    private TextView ft1,ft2,ft3;

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
        name1=findViewById(R.id.name1);
        name2=findViewById(R.id.name2);
        name3=findViewById(R.id.name3);
        name4=findViewById(R.id.name4);
        name5=findViewById(R.id.name5);
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

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weight==0)
                {
                    Toast.makeText(getApplicationContext(),"'견적내기'를 먼저 해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(),SavePopupActivity.class);
                startActivityForResult(intent,9090);
            }
        });
        ft3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weight==0)
                {
                    Toast.makeText(getApplicationContext(),"'견적내기'를 먼저 해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(),SavePopupActivity.class);
                startActivityForResult(intent,9090);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weight==0)
                {
                    Toast.makeText(getApplicationContext(),"'견적내기'를 먼저 해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
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
                if(weight==0)
                {
                    Toast.makeText(getApplicationContext(),"'견적내기'를 먼저 해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
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
                if(weight==0)
                {
                    Toast.makeText(getApplicationContext(),"'견적내기'를 먼저 해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
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
                if(weight==0)
                {
                    Toast.makeText(getApplicationContext(),"'견적내기'를 먼저 해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
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
                    case 0:
                        name1.setText(name[i]); value1.setText(Float.parseFloat(temp[1])+" kg\n"+Float.parseFloat(temp[2])+" 원");
                        Glide.with(getApplicationContext()).load(image[i]).into(iv_home_1); break;
                    case 1:
                        name2.setText(name[i]); value2.setText(Float.parseFloat(temp[1])+" kg\n"+Float.parseFloat(temp[2])+" 원");
                        Glide.with(getApplicationContext()).load(image[i]).into(iv_home_2); break;
                    case 2:
                        name3.setText(name[i]); value3.setText(Float.parseFloat(temp[1])+" kg\n"+Float.parseFloat(temp[2])+" 원");
                        Glide.with(getApplicationContext()).load(image[i]).into(iv_home_3); break;
                    case 3:
                        name4.setText(name[i]); value4.setText(Float.parseFloat(temp[1])+" kg\n"+Float.parseFloat(temp[2])+" 원");
                        Glide.with(getApplicationContext()).load(image[i]).into(iv_home_4); break;
                    case 4:
                        name5.setText(name[i]); value5.setText(Float.parseFloat(temp[1])+" kg\n"+Float.parseFloat(temp[2])+" 원");
                        Glide.with(getApplicationContext()).load(image[i]).into(iv_home_5); break;
                }
            }
            view_home1.setImageResource(R.drawable.self4);
            view_home3.setImageResource(R.drawable.self5);
            view_home5.setImageResource(R.drawable.self4);
            view_home7.setImageResource(R.drawable.self5);
            view_home9.setImageResource(R.drawable.self4);
            fab.setVisibility(View.VISIBLE);
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

}
