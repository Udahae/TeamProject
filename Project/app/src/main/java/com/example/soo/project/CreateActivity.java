package com.example.soo.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {

    private LoginActivity.UserLoginTask mAuthTask = null;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mNameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setTitle("JOIN");
        Intent intent2 = getIntent();

        mEmailView = (EditText) findViewById(R.id.crt_email);
        mPasswordView = (EditText) findViewById(R.id.crt_password);
        mNameView = (EditText) findViewById(R.id.crt_name);

        Button mJoinButton = (Button) findViewById(R.id.join_button);
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptJoin();
            }
        });
    }

    /**
     * 로그인 양식이 정한 계정에 로그인하거나 등록하기
     * 양식에 오류(부정확, 필수기재사항이 빠짐 등)가 있는 경우,
     * 오류가 나타나며 실제 로그인 시도는 이루어지지 않음
     */
    private void attemptJoin() {
        if (mAuthTask != null) {
            return;
        }

        // 오류 재설정
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mNameView.setError(null);

        // 로그인 시도 시점에 값 저장하기
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = mNameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 비밀번호 유효성 체크
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // 이메일 공란일 경우
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) { //이메일 양식이 잘못된 경우
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // 비밀번호 공란일 경우
        if (!TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // 비밀번호 공란일 경우
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }


    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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
