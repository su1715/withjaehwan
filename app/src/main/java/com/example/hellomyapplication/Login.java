package com.example.hellomyapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText email;
    EditText pwd;
    Button btn;
    String emailText;
    String pwdText;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        email=(EditText)findViewById(R.id.email);
        pwd=(EditText)findViewById(R.id.pwd);
        btn=(Button)findViewById(R.id.login);

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
                    Toast toast=Toast.makeText(context,"이메일이나 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT);
                }

            }

        });


    }
}