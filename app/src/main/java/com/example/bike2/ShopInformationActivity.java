package com.example.bike2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class ShopInformationActivity extends AppCompatActivity {

    ShopInformationFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_information);
        fragment=new ShopInformationFragment();

        Intent intent=getIntent();
        Bundle bundle=new Bundle();
        bundle.putString("nickname",intent.getStringExtra("nickname"));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.info_frame, fragment).commit();
    }

    public void replaceFragment(Fragment fragment, String nickname, String content, Uri uri,String latitude,String longitude) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("nickname",nickname);
        bundle.putString("content",content);
        bundle.putParcelable("uri",uri);
        bundle.putString("latitude",latitude);
        bundle.putString("longitude",longitude);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.info_frame, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        Fragment test = getSupportFragmentManager().findFragmentById(R.id.info_frame);
        if(test != null){
            ((ShopInformationFragment.OnBackPressedListener)test).onBackPressed();
        }else{
            finish();
        }
    }
}