package com.example.bike2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AiMain extends AppCompatActivity implements View.OnClickListener {

    Button connect_btn; // ip 받아오는 버튼
    Button getresult_btn;
    TextView show_text; // 서버에서온거 보여주는 에디트
    TextView ai_text;
    RatingBar ratingBar,ratingBar2;
    TextView rating_score;
    TextView tv_ai_main,tv_ai_main2;
    ImageView product_image;

    // 소켓통신에 필요한것
    private String html = "";
    private Handler mHandler;

    private Socket socket;

    private DataOutputStream dos;
    private DataInputStream dis;

    private String ip = "121.174.217.51"; // IP 번호
    private int port = 1991; // port 번호
    private String[][] sendMsg = new String[5][2];;
    private static String STOP_MSG = "stop";
    private float rating_now;
    private String gotFrame = null;
    private String[] gotFrameImages = new String[100];
    private String[] gotFrameNames = new String[100];
    private String[] gotFrameValue = new String[100];
    private String[] gotFrameId = new String[100];
    private int numOfProduct = 0;
    private int currentnumOfProduct  = 0;
    private int[] randomNums = new int [5];
    private String[] resultNames = new String[5];
    private ArrayList<String> review_nickname=new ArrayList<String>();
    private ArrayList<String> review_content=new ArrayList<String>();;
    private ArrayList<Float> review_rating=new ArrayList<Float>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_main);

        connect_btn = (Button)findViewById(R.id.connect_btn);
        connect_btn.setOnClickListener(this);
        ai_text=findViewById(R.id.ai_text);
        getresult_btn = (Button)findViewById(R.id.get_result_btn);
        getresult_btn.setOnClickListener(this);


        show_text = (TextView)findViewById(R.id.show_text);


        ratingBar = findViewById(R.id.ratingBar);
        ratingBar2 = findViewById(R.id.ratingBar2);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating_now = rating;
            }
        });
        tv_ai_main=findViewById(R.id.tv_ai_main);
        tv_ai_main2=findViewById(R.id.tv_ai_main2);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("frame")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            numOfProduct = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                gotFrameNames[numOfProduct] = document.get("name").toString();
                                gotFrameId[numOfProduct]=document.getId();
                                gotFrameValue[numOfProduct]=document.get("value").toString().split("/")[1]+"kg / "+document.get("value").toString().split("/")[2]+" ₩";
                                gotFrameImages[numOfProduct++] = document.get("image").toString();
                            }
                            setIcon();
                        } else {
                            Log.d("Tag", "Error getting documents: ", task.getException());
                        }
                    }
                    public void setIcon() {
                        Random r = new Random();
                        for(int i=0;i<5;i++)
                        {
                            randomNums[i] = r.nextInt(numOfProduct);
                            for(int j=0;j<i;j++)
                            {
                                if(randomNums[i]==randomNums[j])
                                {
                                    i--;
                                }
                            }
                        }
                        TextView showmyTr = findViewById(R.id.show_text);
                        showmyTr.setText(gotFrameNames[randomNums[0]]+" / "+gotFrameValue[randomNums[0]]);
                        final int[] temp = {currentnumOfProduct + 1};
                        ai_text.setText(" 프레임 별점 ("+ temp[0] +"/5)");
                        product_image = (ImageView) findViewById(R.id.product_image);
                        String url = gotFrameImages[randomNums[0]];
                        Glide.with(AiMain.this).load(url).into(product_image);
                        db.collection("frame").document(gotFrameId[randomNums[0]]).collection("review").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        review_nickname.clear();
                                        review_content.clear();
                                        review_rating.clear();
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                review_nickname.add(document.get("nickname").toString());
                                                review_content.add(document.get("content").toString());
                                                review_rating.add(Float.parseFloat(document.get("rating").toString()));
                                            }
                                            int a[] = new int[2];
                                            List<Integer> list = new ArrayList<>();
                                            Random r = new Random();
                                            for(int i=0;i<=1;i++)
                                            {
                                                a[i] = r.nextInt(review_nickname.size());
                                                for(int j=0;j<i;j++) {
                                                    if(a[i]==a[j]) {
                                                        i--;

                                                    }

                                                }
                                            }
                                            for (int value : a) {
                                                list.add(value);
                                            }

                                            tv_ai_main.setText(review_nickname.get(list.get(0))+" : "+review_content.get(list.get(0)));
                                            tv_ai_main2.setText(review_nickname.get(list.get(1))+" : "+review_content.get(list.get(1)));
                                            float temp_rating=0;
                                            for(int i=0;i<review_rating.size();i++){
                                                temp_rating +=review_rating.get(i);
                                            }
                                            temp_rating=temp_rating/review_rating.size();
                                            ratingBar2.setRating(temp_rating);
                                        } else {
                                        }
                                    }
                                });
                    }
                });

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.connect_btn: // ip 받아오는 버튼
                if(currentnumOfProduct == 4){
                    break;
                }
                sendMsg[currentnumOfProduct][0] = gotFrameNames[randomNums[currentnumOfProduct]];
                sendMsg[currentnumOfProduct][1] = rating_now+"";
                Log.d("tag",rating_now+"aa");
                product_image = (ImageView) findViewById(R.id.product_image);
                String url = gotFrameImages[randomNums[++currentnumOfProduct]];
                Glide.with(AiMain.this).load(url).into(product_image);
                TextView showmyTr = findViewById(R.id.show_text);
                showmyTr.setText(gotFrameNames[randomNums[currentnumOfProduct]]+" / "+gotFrameValue[randomNums[currentnumOfProduct]]);
                int temp=currentnumOfProduct+1;
                if(temp==5){
                    connect_btn.setVisibility(View.GONE);
                    getresult_btn.setVisibility(View.VISIBLE);
                }
                ai_text.setText(" 프레임 별점 ("+ temp +"/5)");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("frame").document(gotFrameId[randomNums[currentnumOfProduct]]).collection("review").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                review_nickname.clear();
                                review_content.clear();
                                review_rating.clear();
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        review_nickname.add(document.get("nickname").toString());
                                        review_content.add(document.get("content").toString());
                                        review_rating.add(Float.parseFloat(document.get("rating").toString()));
                                    }
                                    int a[] = new int[2];
                                    List<Integer> list = new ArrayList<>();
                                    Random r = new Random();
                                    for(int i=0;i<=1;i++)
                                    {
                                        a[i] = r.nextInt(review_nickname.size());
                                        for(int j=0;j<i;j++) {
                                            if(a[i]==a[j]) {
                                                i--;

                                            }

                                        }
                                    }
                                    for (int value : a) {
                                        list.add(value);
                                    }

                                    Log.d("AAA",Integer.toString(list.get(0)));
                                    Log.d("AAA",Integer.toString(list.get(1)));
                                    tv_ai_main.setText(review_nickname.get(list.get(0))+" : "+review_content.get(list.get(0)));
                                    tv_ai_main2.setText(review_nickname.get(list.get(1))+" : "+review_content.get(list.get(1)));
                                    float temp_rating=0;
                                    for(int i=0;i<review_rating.size();i++){
                                        temp_rating +=review_rating.get(i);
                                    }
                                    temp_rating=temp_rating/review_rating.size();
                                    ratingBar2.setRating(temp_rating);
                                } else {
                                }
                            }
                        });
                break;
            case R.id.get_result_btn:
                sendMsg[currentnumOfProduct][0] = gotFrameNames[randomNums[currentnumOfProduct]];
                sendMsg[currentnumOfProduct][1] = rating_now+"";
                Log.d("tag",rating_now+"aa");
                connect();
                break;
        }
    }
    void switchIntent(){
        float rate=0;
        for(int i=0;i<5;i++){
            if(sendMsg[i][0].equals(resultNames[0]))
                rate=Float.parseFloat(sendMsg[i][1]);
        }
        Intent intent = new Intent(getApplicationContext(), AiSub.class);
        intent.putExtra("rate",rate);
        intent.putExtra("1등이름",resultNames[0]);
        Log.d("res",resultNames[0]+"awqwr");
        startActivity(intent);
    }
    // 로그인 정보 db에 넣어주고 연결시켜야 함.
    void connect(){
        mHandler = new Handler();
        Log.w("connect","연결 하는중");
// 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {
// ip받기
                String newip = String.valueOf("121.174.217.51");

// 서버 접속
                try {
                    socket = new Socket(newip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                Log.w("edit 넘어가야 할 값 : ","안드로이드에서 서버로 연결요청");
                Log.d("tags",sendMsg[0][0] + " " + sendMsg[0][1]);
                Log.d("tags",sendMsg[1][0] + " " + sendMsg[1][1]);
                Log.d("tags",sendMsg[2][0] + " " + sendMsg[2][1]);
                Log.d("tags",sendMsg[3][0] + " " + sendMsg[3][1]);
                Log.d("tags",sendMsg[4][0] + " " + sendMsg[4][1]);
                /*
                sendMsg = new String[3][2];
                sendMsg[0][0] = "스페셜라이즈드 프레임";
                sendMsg[0][1] = "3.4";
                sendMsg[1][0] = "아르곤 18 갈리움 cs";
                sendMsg[1][1] = "1.1";
                sendMsg[2][0] = "엘파마 레이다 로드";
                sendMsg[2][1] = "1.3";
                */
                try {
                    dos = new DataOutputStream(socket.getOutputStream()); // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream()); // input에 받을꺼 넣어짐
                    for(int i=0 ; i<5 ; i++){
                        byte[] buf = new byte[100];
                        int read_Byte = 0;
                        String line= "";


                        read_Byte = dis.read(buf);
                        line = new String(buf, 0, read_Byte);

                        Log.d("next1",line+"");
                        if(line.equals("next")){
                            dos.writeUTF(sendMsg[i][0]);
                            Log.d("msg1",sendMsg[i][0]);
                            read_Byte = dis.read(buf);
                            line = new String(buf, 0, read_Byte);
                        }
                        Log.d("next2",line+"");
                        if(line.equals("next")){
                            sleep(2);
                            dos.writeUTF(sendMsg[i][1]);
                            Log.d("msg2",sendMsg[i][1]);
                            sleep(2);
                        }

                    }

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼","버퍼생성 잘됨");

// 서버에서 계속 받아옴 - 한번은 문자, 한번은 숫자를 읽음. 순서 맞춰줘야 함.
                try {
                    String line = "";
                    byte[] buf = new byte[100];
                    int read_Byte = dis.read(buf);
                    line = new String(buf, 0, read_Byte);
                    resultNames[0] = line;
                    Log.w("DebugA","AAA");
                    Log.w("DebugA",resultNames[0]);
                    switchIntent();

                }catch (Exception e){

                }
            }
        };
// 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}