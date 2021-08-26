package com.example.bike2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class InformationActivity extends AppCompatActivity {

    private EditText et_in_information;
    private ImageView btn_in_information,btn2_in_information,iv_in_information;
    private RadioButton radioButton,radioButton2;
    private TextView tv_in_information;
    Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        et_in_information=findViewById(R.id.et_in_information);
        tv_in_information=findViewById(R.id.tv_in_information);
        btn_in_information=findViewById(R.id.btn_in_information);
        btn2_in_information=findViewById(R.id.btn2_in_information);
        iv_in_information=findViewById(R.id.iv_in_information);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);

        iv_in_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton2.setChecked(false);
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton.setChecked(false);
            }
        });

        btn2_in_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_in_information.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int tmp=0;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(et_in_information.getText().toString().equals(document.get("nickname"))){
                                            tmp++;
                                        }
                                    }
                                    if(tmp==0){
                                        tv_in_information.setText("이용 가능한 닉네임 입니다.");
                                        tv_in_information.setTextColor(Color.parseColor("#4cd964"));
                                    }
                                    else{
                                        tv_in_information.setText("중복된 닉네임 입니다.");
                                        tv_in_information.setTextColor(Color.parseColor("#ff3b30"));
                                    }
                                } else {
                                }
                            }
                        });
            }
        });

        btn_in_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_in_information.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!tv_in_information.getText().toString().equals("이용 가능한 닉네임 입니다.")){
                    Toast.makeText(getApplicationContext(), "중복확인을 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!radioButton.isChecked()&&!radioButton2.isChecked()) {
                    Toast.makeText(getApplicationContext(), "이용방식을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> temp = new HashMap<>();
                if(radioButton.isChecked()) {
                    temp.put("nickname",et_in_information.getText().toString());
                    temp.put("isShop", "false");
                    db.collection("users").document(user.getUid()).set(temp)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully written!");
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error writing document", e);
                                }
                            });




                    final String cu = user.getUid();
                    String filename = cu + "_" + System.currentTimeMillis();
                    FirebaseStorage storage= FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://bike-71038.appspot.com/").child("users/" + filename);
                    UploadTask uploadTask;
                    Uri file = null;
                    file = photoUri;
                    if(photoUri==null){
                        file = Uri.parse("android.resource://com.example.bike2/drawable/defaultimage");
                    }
                    uploadTask = storageRef.putFile(file);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.v("알림", "사진 업로드 실패");
                            exception.printStackTrace();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();;
                                    DocumentReference docRef = db.collection("users").document(user.getUid());
                                    Map<String, Object> temp = new HashMap<>();
                                    temp.put("profile", uri.toString());
                                    temp.put("nickname",et_in_information.getText().toString());
                                    temp.put("isShop", "false");
                                    db.collection("users").document(user.getUid()).set(temp)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("TAG", "DocumentSnapshot successfully written!");
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("TAG", "Error writing document", e);
                                                }
                                            });

                                }
                            });
                        }
                    });




                }
                else {
                    Intent intent=new Intent(getApplicationContext(),ShopInformationActivity.class);
                    intent.putExtra("nickname",et_in_information.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if(resultCode == getParent().RESULT_OK)
            {
                try{
                    photoUri=data.getData();
                    InputStream in =getApplication().getContentResolver().openInputStream(photoUri);
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    Glide.with(this).load(img).apply(new RequestOptions().circleCrop().centerCrop()).into(iv_in_information);

                }catch(Exception e)
                {

                }
            }
            else if(resultCode == getParent().RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(), "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}