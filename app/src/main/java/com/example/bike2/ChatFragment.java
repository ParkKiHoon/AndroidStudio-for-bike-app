package com.example.bike2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private View view;
    public ChatAdapter chatAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ChatData> arrayList;
    private String opp_nickname;
    private TextView no_chat;
    ListenerRegistration listner;
    FirebaseFirestore db;
    String uid ;
    int chat_num;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_chat);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        chatAdapter = new ChatAdapter(arrayList);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setAdapter(chatAdapter);
        no_chat=view.findViewById(R.id.no_chat);
        chat_num=0;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid= user.getUid();
        db = FirebaseFirestore.getInstance();

        Log.d(Integer.toString(arrayList.size()),"AAAAAA");
        listner=db.collection("chats").orderBy("time")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        for (DocumentChange document : snapshots.getDocumentChanges()) {
                            arrayList.clear();
                            chatAdapter.clear();
                            if (document.getDocument().get("uid_me").equals(uid)) {
                                get_nickname(document.getDocument().getId(),document.getDocument().get("uid_you").toString(), document.getDocument().get("latest").toString(), Long.parseLong(document.getDocument().get("time").toString()));
                                chat_num++;
                            } else if (document.getDocument().get("uid_you").equals(uid)) {
                                get_nickname(document.getDocument().getId(),document.getDocument().get("uid_me").toString(), document.getDocument().get("latest").toString(), Long.parseLong(document.getDocument().get("time").toString()));
                                chat_num++;
                            }
                        }
                        if(chat_num==0){
                            no_chat.setVisibility(View.VISIBLE);
                        }
                        else{
                            no_chat.setVisibility(View.INVISIBLE);
                        }

                    }
                });

        return view;
    }

    private void get_nickname(String temp1,String uid, String temp2, Long temp3) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        opp_nickname = document.get("nickname").toString();
                        ChatData chatdata = new ChatData(temp1, opp_nickname,temp2,temp3);
                        arrayList.add(chatdata);
                        chatAdapter.notifyDataSetChanged();
                    } else {
                    }
                } else {
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        listner.remove();
    }

}
