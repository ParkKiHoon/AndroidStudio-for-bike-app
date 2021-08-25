package com.example.bike2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
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
    private FloatingActionButton fab, fab1, fab2,fab3;
    private TextView ft1,ft2,ft3;

    String[] name;
    String[] part;
    String[] image;
    float weight=0;
    float price=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        button_home=view.findViewById(R.id.btn_home);
        button_home1=view.findViewById(R.id.btn_home1);
        button_home2=view.findViewById(R.id.btn_home2);
        iv_home_1=view.findViewById(R.id.iv_home_1);
        iv_home_2=view.findViewById(R.id.iv_home_2);
        iv_home_3=view.findViewById(R.id.iv_home_3);
        iv_home_4=view.findViewById(R.id.iv_home_4);
        iv_home_5=view.findViewById(R.id.iv_home_5);
        tv_home_1=view.findViewById(R.id.tv_home_1);
        tv_home_3=view.findViewById(R.id.tv_home_3);
        tv_home_5=view.findViewById(R.id.tv_home_5);
        tv_home_7=view.findViewById(R.id.tv_home_7);
        tv_home_9=view.findViewById(R.id.tv_home_9);
        tv_home_11=view.findViewById(R.id.tv_home_11);

        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.float_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.float_close);

        fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab1 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton2);
        fab2 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton3);
        fab3 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton4);
        ft1=view.findViewById(R.id.floatingtext2);
        ft2=view.findViewById(R.id.floatingtext3);
        ft3=view.findViewById(R.id.floatingtext4);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent=new Intent(getActivity(),LoadingSelf.class);
                startActivity(intent);
            }
        });
        ft1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent=new Intent(getActivity(),LoadingSelf.class);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });
        ft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });
        ft3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });


        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weight==0)
                {
                    Toast.makeText(getActivity(),"'견적내기'를 먼저 해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(),SavePopupActivity.class);
                startActivityForResult(intent,9090);
            }
        });

        button_home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weight==0)
                {
                    Toast.makeText(getActivity(),"'견적내기'를 먼저 해주세요",Toast.LENGTH_SHORT).show();
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
        button_home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weight==0)
                {
                    Toast.makeText(getActivity(),"'견적내기'를 먼저 해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                ((MainActivity)getActivity()).replaceFragment(MapFragment.newInstance(),name,part);
            }
        });

        return view;
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
                        Glide.with(getActivity()).load(image[i]).into(iv_home_1); break;
                    case 1:tv_home_3.setText(name[i]+"\n"+Float.parseFloat(temp[1])+"kg "+Float.parseFloat(temp[2])+"₩");
                        Glide.with(getActivity()).load(image[i]).into(iv_home_2); break;
                    case 2:tv_home_5.setText(name[i]+"\n"+Float.parseFloat(temp[1])+"kg "+Float.parseFloat(temp[2])+"₩");
                        Glide.with(getActivity()).load(image[i]).into(iv_home_3); break;
                    case 3:tv_home_7.setText(name[i]+"\n"+Float.parseFloat(temp[1])+"kg "+Float.parseFloat(temp[2])+"₩");
                        Glide.with(getActivity()).load(image[i]).into(iv_home_4); break;
                    case 4:tv_home_9.setText(name[i]+"\n"+Float.parseFloat(temp[1])+"kg "+Float.parseFloat(temp[2])+"₩");
                        Glide.with(getActivity()).load(image[i]).into(iv_home_5); break;
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



    public void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            ft1.setVisibility(View.GONE);
            ft2.setVisibility(View.GONE);
            ft3.setVisibility(View.GONE);
            isFabOpen = false;
            fab.setImageResource(R.drawable.float1);
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            ft1.setVisibility(View.VISIBLE);
            ft2.setVisibility(View.VISIBLE);
            ft3.setVisibility(View.VISIBLE);
            isFabOpen = true;
            fab.setImageResource(R.drawable.float2);
        }
    }

}
