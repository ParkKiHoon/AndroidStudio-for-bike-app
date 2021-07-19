package com.example.bike2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ListFragment extends Fragment  {
    private View view;
    private ListAdapter listAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ListData> arrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_list,container,false);
        recyclerView=view.findViewById(R.id.recycler_view);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList=new ArrayList<>();
        listAdapter=new ListAdapter(arrayList);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setAdapter(listAdapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("post").orderBy("time")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ListData listdata = new ListData(document.get("image").toString(),document.get("name").toString(),document.get("time").toString(),document.get("weight_price").toString()
                                        ,document.get("frame_name").toString(),document.get("frame_value").toString(),document.get("wheelset_name").toString(),document.get("wheelset_value").toString()
                                        ,document.get("handlebar_name").toString(),document.get("handlebar_value").toString(),document.get("saddle_name").toString(),document.get("saddle_value").toString()
                                        ,document.get("groupset_name").toString(),document.get("groupset_value").toString());
                                arrayList.add(listdata);
                                listAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });


        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listAdapter.clear();
                db.collection("post").orderBy("time")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        ListData listdata = new ListData(document.get("image").toString(),document.get("name").toString(),document.get("time").toString(),document.get("weight_price").toString()
                                                ,document.get("frame_name").toString(),document.get("frame_value").toString(),document.get("wheelset_name").toString(),document.get("wheelset_value").toString()
                                                ,document.get("handlebar_name").toString(),document.get("handlebar_value").toString(),document.get("saddle_name").toString(),document.get("saddle_value").toString()
                                                ,document.get("groupset_name").toString(),document.get("groupset_value").toString());
                                        arrayList.add(listdata);
                                        listAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    Log.d("tag", "Error getting documents: ", task.getException());
                                }
                            }
                        });
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }


}
