package com.example.hellomyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    EditText emailT,pwdT,pwdvfT;
    Button signupBtn,cancelBtn;
    String emailString,pwdString,pwdvfString;
    Intent intent_login;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        emailT=(EditText)findViewById(R.id.email_sp);
        pwdT=(EditText)findViewById(R.id.pwd_sp);
        pwdvfT=(EditText)findViewById(R.id.pwd2_sp);
        signupBtn=(Button)findViewById(R.id.signupBtn);
        cancelBtn=(Button)findViewById(R.id.cancelBtn);
        intent_login=new Intent(getApplicationContext(),Login.class);

        signupBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){

                emailString=emailT.getText().toString();
                pwdString=pwdT.getText().toString();
                pwdvfString=pwdvfT.getText().toString();
                if(emailString.length()!=0 && pwdString.length()!=0 && pwdvfString.length()!=0) {
                    if (pwdvfString.equals(pwdString)) { //조건: 비밀번호와 비밀번호 확인이 같은 경우

                        if (true) {//TODO: 일치하는 이메일이 서버에 없는 경우 (서버로 확인)
                            //TODO: 이메일, 비밀번호 서버에 저장
                            Toast toast=Toast.makeText(view.getContext(), "가입되셨습니다.", Toast.LENGTH_SHORT);
                            toast.show();
                            startActivity(intent_login);

                        } else {
                            Toast toast=Toast.makeText(view.getContext(), "이미 가입된 이메일입니다.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    } else {
                        Toast toast=Toast.makeText(view.getContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast=Toast.makeText(view.getContext(), "모두 입력해주세요.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        cancelBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(intent_login);
            }

        });
    }
}
