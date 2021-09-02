package com.example.bike2;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    private View view;
    private ScrollView scrollView;
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
    private ImageView iv1,iv2,iv3,iv4,iv5;
    private ConstraintLayout cl1,cl2,cl3,cl4,cl5;

    private ImageView transparent_view;

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2,fab3;
    private TextView ft1,ft2,ft3;
    private TextView look1,look2,look3,look4,look5;

    String[] name;
    String[] part;
    String[] image;
    float weight=0;
    float price=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
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
        iv1=view.findViewById(R.id.home_iv1);
        iv2=view.findViewById(R.id.home_iv2);
        iv3=view.findViewById(R.id.home_iv3);
        iv4=view.findViewById(R.id.home_iv4);
        iv5=view.findViewById(R.id.home_iv5);
        cl1=view.findViewById(R.id.cl1);
        cl2=view.findViewById(R.id.cl2);
        cl3=view.findViewById(R.id.cl3);
        cl4=view.findViewById(R.id.cl4);
        cl5=view.findViewById(R.id.cl5);
        look1=view.findViewById(R.id.textView6);
        look2=view.findViewById(R.id.textView18);
        look3=view.findViewById(R.id.textView11);
        look4=view.findViewById(R.id.textView25);
        look5=view.findViewById(R.id.textView21);

        scrollView=view.findViewById(R.id.scrollView);

        transparent_view=view.findViewById(R.id.tranparent_view);
        Drawable alpha = transparent_view.getDrawable();
        alpha.setAlpha(150);


        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.float_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.float_close);

        fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab1 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton2);
        fab2 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton3);
        fab3 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton4);
        ft1=view.findViewById(R.id.floatingtext2);
        ft2=view.findViewById(R.id.floatingtext3);
        ft3=view.findViewById(R.id.floatingtext4);

        setView("frame","치넬리 비고렐리 샤크",iv1);
        setView("wheelset","스페셜라이즈드 로발 래피드CLX",iv2);
        setView("saddle","스페셜라이즈드 안장",iv3);
        setView("handlebar","치넬리 바이 핸들바",iv4);
        setView("groupset","시마노 울테그라 구동계",iv5);

        look1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cinelli-usa.com/vigorelli-shark-red-alert-frameset/"));
                startActivity(intent);
            }
        });
        look2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.specialized.com/kr/ko/roval-rapide-clx--front/p/174212?searchText=30020-6501&color=278173-174212"));
                startActivity(intent);
            }
        });
        look3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.specialized.com/us/en/power-comp/p/155836?color=229240-155836"));
                startActivity(intent);
            }
        });
        look4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cinelli-usa.com/cinelli-vai-bicycle-handlebar/"));
                startActivity(intent);
            }
        });
        look5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bike.shimano.com/ko-KR/product/component/ultegra-r8000.html"));
                startActivity(intent);
            }
        });
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
                startActivityForResult(intent,10010);
            }
        });
        ft1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent=new Intent(getActivity(),LoadingSelf.class);
                startActivityForResult(intent,10010);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent=new Intent(getActivity(),ai_select.class);
                startActivityForResult(intent,10010);
            }
        });
        ft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent=new Intent(getActivity(),ai_select.class);
                startActivityForResult(intent,10010);
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

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(isFabOpen)
                    anim();
            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(isFabOpen)
                    anim();
                return false;
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
        else if(requestCode == 10010 && resultCode ==10010 ){
            name=data.getExtras().getStringArray("name");
            part=data.getExtras().getStringArray("part");
            ((MainActivity)getActivity()).replaceFragment(MapFragment.newInstance(),name,part);
        }
    }


    public void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);

            isFabOpen = false;
            fab.setImageResource(R.drawable.float1);
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
                    transparent_view.setVisibility(View.INVISIBLE);
                }
            }, 300);
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
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
                    transparent_view.setVisibility(View.VISIBLE);
                }
            }, 300);
        }
    }

    private void setView(String a,String b,ImageView c){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(a)
                .whereEqualTo("name",b)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Glide.with(getActivity()).load(document.get("image")).listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        Bitmap bitmap=((BitmapDrawable)resource).getBitmap();
                                        c.setImageBitmap(bitmap);
                                        int rgb = bitmap.getPixel(1,1);
                                        switch (a){
                                            case "frame": cl1.setBackgroundColor(rgb); break;
                                            case "wheelset": cl2.setBackgroundColor(rgb); break;
                                            case "saddle": cl3.setBackgroundColor(rgb); break;
                                            case "handlebar": cl4.setBackgroundColor(rgb); break;
                                            case "groupset": cl5.setBackgroundColor(rgb); break;
                                        }
                                        return true;
                                    }
                                }).into(c);
                            }
                        } else {
                        }
                    }
                });
    }

}
