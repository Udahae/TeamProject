package com.example.soo.project;

import android.*;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.database.Cursor;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;


public class LoginActivity extends AppCompatActivity{

    public static Context context;
    FeedReaderDBOpenHelper openHelper;
    SQLiteDatabase db;
    EditText mEmail, mPassword;
    Button mLogin;
    TextView mJoin;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setPermission();
        context = this;
        setTitle("LOGIN");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent login = getIntent();
        openHelper = new FeedReaderDBOpenHelper(this);
        db = openHelper.getWritableDatabase();

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
        mJoin = (TextView) findViewById(R.id.join);
        mLogin.setOnClickListener(listener);
        mJoin.setOnClickListener(listener);

//
        //splash
        Intent intent = new Intent(getApplicationContext(), Splash.class);
        startActivity(intent);
    }

    private void setPermission() {
        String[] permissions = new String[]{android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_COARSE_LOCATION
                , android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, permissions, 1);
            } else {
                ActivityCompat.requestPermissions(this,permissions, 1);
            }
        }else{
            return;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"권한허용 동의",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this,"권한허용 비동의",Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    View.OnClickListener listener = new View.OnClickListener(){
        public void onClick(View v){
            switch (v.getId()){
                case R.id.join:
                    // 회원가입 텍스트를 누른 경우
                    Intent intent2 = new Intent(LoginActivity.this, CreateActivity.class);
                    startActivity(intent2);
//                    finish();
                    break;
                case R.id.login:
                    //로그인 버튼을 누른 경우
                    email = mEmail.getText().toString();
                    String password = mPassword.getText().toString();
                    String sql = "select * from memberJoin where email='"+email+"'and pass='"+password+"'";
                    Cursor cursor = db.rawQuery(sql, null);

                    boolean cancel = false;
                    View focusView = null;

                    while(cursor.moveToNext()){
                        String e = cursor.getString(0);
                        String p = cursor.getString(1);
                    }
                    if(cursor.getCount() == 1){
                        //해당 이메일이 있는 경우
                        Log.i("hoho","YES");
                        Toast.makeText(LoginActivity.this, email+"님 환영합니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class); // 로그인 성공시 넘어갈 액티비티
                        startActivity(intent);
                        finish();
                    } else if(TextUtils.isEmpty(password) && cursor.getCount() == 0){
                        //비밀번호 공란인 경우
                        mPassword.setError("This field is required");
                        focusView = mPassword;
                        cancel = true;
                    } else{
                        //이메일 또는 비밀번호 오류
                        Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }


                    if (TextUtils.isEmpty(email)) {
                        // 이메일 공란일 경우
                        mEmail.setError(getString(R.string.error_field_required));
                        focusView = mEmail;
                        cancel = true;
                    } else if (!isEmailValid(email)) {
                        //이메일에 @가 포함되지 않은 경우
                        mEmail.setError(getString(R.string.error_invalid_email));
                        focusView = mEmail;
                        cancel = true;
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

    public String getEmail() {
        return email;
    }
}

