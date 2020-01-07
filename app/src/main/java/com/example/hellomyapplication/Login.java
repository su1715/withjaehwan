package com.example.hellomyapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Login extends AppCompatActivity {
    EditText email;
    EditText pwd;
    Button btn;
    Button signup_loginpg;
    String emailText;
    String pwdText;
    Intent intent;
    Intent intent_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        //TODO: 권한 허용 하나로 묻기 (by수정)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        email=(EditText)findViewById(R.id.email);
        pwd=(EditText)findViewById(R.id.pwd);
        btn=(Button)findViewById(R.id.login);
        signup_loginpg=(Button)findViewById(R.id.signup);

        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                emailText=email.getText().toString();
                pwdText=pwd.getText().toString();
                Context context=view.getContext();
                if(true){ // TODO: if 조건= emailText와 pwdText가 서버에 저장된 정보와 일치하는지 확인
                    intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("email",emailText);
                    intent.putExtra("password",pwdText);
                    startActivity(intent);
                }
                else{
                    Toast toast=Toast.makeText(context,"이메일 혹은 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT);
                }

            }

        });

        //회원가입
        signup_loginpg.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                intent_signup=new Intent(getApplicationContext(),Signup.class);
                startActivity(intent_signup);
            }
        });


    }


}