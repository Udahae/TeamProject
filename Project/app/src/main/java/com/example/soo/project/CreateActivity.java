package com.example.soo.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setTitle("JOIN");
        Intent intent2 = getIntent();
    }

    public void findGroup(View view){
        final CharSequence[] items = {"명지대학교 컴퓨터공학과", "명지대학교 산업경영공학과", "명지대학교 토목환경공학과"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

        // 여기서 부터는 알림창의 속성 설정
        builder.setTitle("그룹을 선택하세요")        // 제목 설정
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){
                    // 목록 클릭시 설정
                    public void onClick(DialogInterface dialog, int index){
                        Toast.makeText(getApplicationContext(), items[index], Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림창 띄우기
    }

    public void cancel(View view){
        finish();
    }
}
