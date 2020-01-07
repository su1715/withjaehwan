package com.example.hellomyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Tab3Main extends AppCompatActivity {
    Button startBtn;
    Intent startIntent;
    String key = ((MainActivity)MainActivity.context).globalkey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_main);
        easyToast(key);
        startBtn=(Button)findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                startIntent=new Intent(getApplicationContext(), Tab3InputInfo.class);
                startActivity(startIntent);

            }
        });

    }
    void easyToast(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }

}
