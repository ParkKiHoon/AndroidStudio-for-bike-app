package com.example.bike2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class SecondShopInformationFragment extends Fragment {
    private View view;

    FirebaseStorage storage;
    EditText info_second_content;
    ImageView info_second_image;
    Button info_second_btn;
    String nickname;
    String content;
    String latitude;
    String longitude;
    Uri photoUri;
    Uri second_photoUri;
    String downloadUrl;
    String second_downloadUrl;
    int cnt=4;

    public static SecondShopInformationFragment newInstance(){
        return new SecondShopInformationFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_shop_information_second,container,false);
        info_second_content=view.findViewById(R.id.second_info_content);
        info_second_image=view.findViewById(R.id.second_info_image);
        info_second_btn=view.findViewById(R.id.second_info_btn);
        nickname=getArguments().getString("nickname");
        content=getArguments().getString("content");
        photoUri=getArguments().getParcelable("uri");
        latitude=getArguments().getString("latitude");
        longitude=getArguments().getString("longitude");
        info_second_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });

        info_second_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info_second_content.getText().toString().equals("")||second_photoUri == null){
                    Toast.makeText(getActivity(),"사진 또는 글을 작성하여 주십시오.",Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String cu = user.getUid();
                String filename = cu + "_" + System.currentTimeMillis();
                storage=FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://bike-71038.appspot.com/").child("shops/" + filename);
                UploadTask uploadTask;
                Uri file = null;
                file = photoUri;
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
                                downloadUrl=uri.toString();
                                String second_filename = cu + "_" + System.currentTimeMillis();
                                StorageReference second_storageRef = storage.getReferenceFromUrl("gs://bike-71038.appspot.com/").child("shops/" + second_filename);
                                UploadTask second_uploadTask;
                                Uri second_file = null;
                                second_file = second_photoUri;
                                second_uploadTask = second_storageRef.putFile(second_file);
                                second_uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Log.v("알림", "사진 업로드 실패");
                                        exception.printStackTrace();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot2) {
                                        Task<Uri> result = taskSnapshot2.getMetadata().getReference().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                second_downloadUrl=uri.toString();
                                                FirebaseFirestore db = FirebaseFirestore.getInstance();;
                                                DocumentReference docRef = db.collection("users").document(user.getUid());
                                                Map<String, Object> temp = new HashMap<>();
                                                temp.put("image", downloadUrl);
                                                temp.put("nickname", nickname);
                                                temp.put("latitude", latitude);
                                                temp.put("longitude", longitude);
                                                temp.put("contents", content);
                                                temp.put("isShop","true");
                                                docRef.set(temp)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Map<String, Object> temp2 = new HashMap<>();
                                                                temp2.put("image", second_downloadUrl);
                                                                temp2.put("shopname", nickname);
                                                                temp2.put("contents", info_second_content.getText().toString());
                                                                docRef.collection("myShop").add(temp2)
                                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                            @Override
                                                                            public void onSuccess(DocumentReference documentReference) {
                                                                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                                                fragmentManager.beginTransaction().remove(SecondShopInformationFragment.this).commit();
                                                                                fragmentManager.popBackStack();
                                                                                ((ShopInformationActivity) getActivity()).finish();
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Log.d("TAG", "Error writing document", e);
                                                                            }
                                                                        });

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
                        });
                    }
                });



            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 2)
        {
            if(resultCode == getActivity().RESULT_OK)
            {
                try{
                    second_photoUri=data.getData();
                    InputStream in = getActivity().getContentResolver().openInputStream(photoUri);
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
                    Glide.with(getActivity()).load(img).apply(requestOptions).into(info_second_image);
                }catch(Exception e)
                {

                }
            }
            else if(resultCode == getActivity().RESULT_CANCELED)
            {
                Toast.makeText(getActivity(), "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

}

