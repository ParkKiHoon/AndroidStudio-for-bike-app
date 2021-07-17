package com.example.bike2;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unity3d.player.UnityPlayerActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    private View view;
    private Button button;
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

    String[] name;
    String[] part;
    String[] image;
    float weight=0;
    float price=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        button=view.findViewById(R.id.button);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                                                                                                    Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
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
            Map<String, Object> temp=new HashMap<>();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            temp.put("title",data.getExtras().getString("get_custom"));
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
    }

}
