package com.example.soo.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {

    FeedReaderDBOpenHelper openHelper;
    SQLiteDatabase db;
    EditText mEmail, mPassword, mPasssign, mName;
    Button join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setTitle("JOIN");
        Intent intent2 = getIntent();

        openHelper = new FeedReaderDBOpenHelper(this);
        db = openHelper.getWritableDatabase();
        mEmail = (EditText) findViewById(R.id.crt_email);
        mPassword = (EditText) findViewById(R.id.crt_password);
        mPasssign = (EditText) findViewById(R.id.crt_passsign);
        mName = (EditText) findViewById(R.id.crt_name);
        join = (Button) findViewById(R.id.join);
        join.setOnClickListener(listener);

    }
    View.OnClickListener listener = new View.OnClickListener(){
        public void onClick(View v){
            switch (v.getId()) {
                case R.id.join:
                    String email = mEmail.getText().toString();
                    String password = mPassword.getText().toString();
                    String passsign = mPasssign.getText().toString();
                    String name = mName.getText().toString();
                    String sql = "select * from memberJoin where email='" + email + "'";
                    Cursor cursor = db.rawQuery(sql, null);

                    boolean cancel = false;
                    View focusView = null;

                    if (cursor.getCount() == 1) {
                        //해당 이메일이 이미 회원가입 되어있을 경우
                        Toast.makeText(CreateActivity.this, "이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show();
                    } else if(TextUtils.isEmpty(email)) {
                        //이메일 공란일 경우
                        mEmail.setError(getString(R.string.error_field_required));
                        focusView = mEmail;
                        cancel = true;
                    } else if (!isEmailValid(email)) {
                        //이메일에 @가 포함되지 않은 경우
                        mEmail.setError(getString(R.string.error_invalid_email));
                        focusView = mEmail;
                        cancel = true;
                    } else if(TextUtils.isEmpty(password) && cursor.getCount() == 0){
                        //비밀번호 공란일 경우
                        mPassword.setError("This field is required");
                        focusView = mPassword;
                        cancel = true;
                    } else if(!isPasssignValid(password, passsign) && cursor.getCount() == 0){
                        //비밀번호와 비밀번호확인이 같지 않을 경우
                        mPasssign.setError("Does not matched");
                        focusView = mPasssign;
                        cancel = true;
                    } else if(TextUtils.isEmpty(passsign) && cursor.getCount() == 0){
                        //비밀번호확인 공란일 경우
                        mPasssign.setError("This field is required");
                        focusView = mPasssign;
                        cancel = true;
                    } else if(TextUtils.isEmpty(name) && cursor.getCount() == 0){
                        //이름 공란일 경우
                        mName.setError("This field is required");
                        focusView = mName;
                        cancel = true;
                    } else{
                        //회원가입 조건이 모두 만족되었을 때 db에 insert
                        String sql2 = "insert into memberJoin(email, pass, passCheck, name) values('" + email + "','" + password + "','" + passsign + "','" + name + "')";
                        Log.i("HOHO",sql2);
                        db.execSQL(sql2);
                        Toast.makeText(CreateActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                    if (cancel) {
                        // 오류가 나온 경우 로그인을 시도하지 않고 오류가 있는 첫번째 양식필드로 가기
                        focusView.requestFocus();
                    }

                    cursor.close();
                    break;
            }
        }
    };

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasssignValid(String password, String passsign) {
        //TODO: Replace this with your own logic
        return passsign.equals(password);
    }

    public void findGroup(View view){
        final CharSequence[] items = {"교통공학과", "기계공학과", "산업경영공학과", "신소재공학과"
                , "전기공학과", "전자공학과", "정보통신공학과", "컴퓨터공학과", "토목환경공학과", "화학공학과", "환경에너지공학과"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

        // 여기서 부터는 알림창의 속성 설정
        builder.setTitle("그룹을 선택하세요")        // 제목 설정
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){
                    // 목록 클릭시 설정
                    public void onClick(DialogInterface dialog, int index){
                        //Toast.makeText(getApplicationContext(), items[index], Toast.LENGTH_SHORT).show();
                        TextView textView = new TextView(CreateActivity.this);
                        TextView tv = (TextView)findViewById(R.id.selected_grp);
                        tv.setText(items[index]);
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림창 띄우기
    }

    public void cancel(View view){
        finish();
    }
}
