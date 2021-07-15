package com.example.bike2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MapFragment mapFragment;
    private ListFragment listFragment;
    private ChatFragment chatFragment;
    private HomeFragment homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document!=null) {
                        if (document.exists()) {
                            Log.e("log", "find");
                        } else {
                            Log.e("log", "No such document");
                            Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    Log.e("log", "get failed with ", task.getException());
                }
            }
        });




        bottomNavigationView=findViewById(R.id.bottom_navy);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_home:
                        setFragment(0);break;
                    case R.id.menu_list:
                        setFragment(1);break;
                    case R.id.menu_map:
                        setFragment(2);break;
                    case R.id.menu_chat:
                        setFragment(3);break;
                }
                return true;
            }
        });
        homeFragment=new HomeFragment();
        listFragment=new ListFragment();
        mapFragment=new MapFragment();
        chatFragment=new ChatFragment();

        setFragment(0);
    }


    private void setFragment(int n){
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        switch (n){
            case 0:
                fragmentTransaction.replace(R.id.main_frame,homeFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.main_frame,listFragment);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.main_frame,mapFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.main_frame,chatFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    public interface OnBackPressedListener {
    }
}