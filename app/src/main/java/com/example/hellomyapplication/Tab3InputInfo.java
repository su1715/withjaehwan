package com.example.hellomyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Tab3InputInfo extends AppCompatActivity {
    EditText info1,info2,info3,info4;
    String info_1,info_2,info_3,info_4;
    Button inputEnd;
    Intent intentToStart;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_input_info);
        info1=(EditText)findViewById(R.id.info1);
        info2=(EditText)findViewById(R.id.info2);
        info3=(EditText)findViewById(R.id.info3);
        info4=(EditText)findViewById(R.id.info4);
        inputEnd=(Button)findViewById(R.id.inputEnd);



        inputEnd.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){

                info_1=info1.getText().toString();
                info_2=info2.getText().toString();
                info_3=info3.getText().toString();
                info_4=info4.getText().toString();
                if(info_1.length()!=0 && info_2.length()!=0 && info_3.length()!=0 && info_4.length()!=0) {


                    //TODO: 서버에 info1~4 서버저장
                    //TODO: 게임에 접속했다는 key값 서버저장

                    intentToStart = new Intent(getApplicationContext(), Tab3GameStart.class);
                    startActivity(intentToStart);
                }
                else{
                    Toast toast=Toast.makeText(view.getContext(),"정보를 모두 입력해주세요.",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });



    }
}
