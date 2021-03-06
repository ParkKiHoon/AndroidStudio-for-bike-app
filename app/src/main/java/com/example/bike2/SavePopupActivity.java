package com.example.bike2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class SavePopupActivity extends Activity {
    TextView txtText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_save);

        txtText = (TextView)findViewById(R.id.txtText);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        if(txtText.getText().toString().getBytes().length <= 0)
        {
            Toast.makeText(getApplicationContext(),"제목을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("result", txtText.getText().toString());
        Toast.makeText(getApplicationContext(),txtText.getText(), Toast.LENGTH_SHORT).show();
        setResult(9090, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
