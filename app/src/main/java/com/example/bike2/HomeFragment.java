package com.example.bike2;

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
import android.widget.TextView;
import android.widget.Toast;

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
    private MapFragment mapFragment;
    private ListFragment listFragment;
    private ChatFragment chatFragment;
    private Button button;
    private Button btn_logout;
    ArrayList<String> frame_name=new ArrayList<String>();
    ArrayList<String> frame_value=new ArrayList<String>();
    ArrayList<String> wheelset_name =new ArrayList<String>();
    ArrayList<String> wheelset_value =new ArrayList<String>();
    ArrayList<String> handlebar_name =new ArrayList<String>();
    ArrayList<String> handlebar_value = new ArrayList<String>();
    ArrayList<String> saddle_name =new ArrayList<String>();
    ArrayList<String> saddle_value = new ArrayList<String>();
    ArrayList<String> groupset_name = new ArrayList<String>();
    ArrayList<String> groupset_value = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
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
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("wheelset")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                wheelset_name.add(document.get("name").toString());
                                wheelset_value.add(document.get("value").toString());
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("handlebar")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                handlebar_name.add(document.get("name").toString());
                                handlebar_value.add(document.get("value").toString());
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("saddle")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                saddle_name.add(document.get("name").toString());
                                saddle_value.add(document.get("value").toString());
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("groupset")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                groupset_name.add(document.get("name").toString());
                                groupset_value.add(document.get("value").toString());
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });

        button=view.findViewById(R.id.button);
        btn_logout=view.findViewById(R.id.btn_logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
                intent.putStringArrayListExtra("frame_name",frame_name);
                intent.putStringArrayListExtra("frame_value",frame_value);
                intent.putStringArrayListExtra("wheelset_name",wheelset_name);
                intent.putStringArrayListExtra("wheelset_value",wheelset_value);
                intent.putStringArrayListExtra("handlebar_name",handlebar_name);
                intent.putStringArrayListExtra("handlebar_value",handlebar_value);
                intent.putStringArrayListExtra("saddle_name",saddle_name);
                intent.putStringArrayListExtra("saddle_value",saddle_value);
                intent.putStringArrayListExtra("groupset_name",groupset_name);
                intent.putStringArrayListExtra("groupset_value",groupset_value);
                startActivityForResult(intent,8080);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8080 && resultCode == 8080) {
            String[] name=data.getExtras().getStringArray("name");
            String[] part=data.getExtras().getStringArray("part");
            float weight=0;
            float price=0;
            for(int i=0;i<5;i++){
                String[] temp=part[i].split("/");
                weight+=Float.parseFloat(temp[1]);
                price+=Float.parseFloat(temp[2]);
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
