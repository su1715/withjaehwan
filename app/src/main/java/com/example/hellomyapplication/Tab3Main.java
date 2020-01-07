package com.example.hellomyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Tab3Main extends AppCompatActivity {
    Button startBtn;
    Intent startIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_main);
        startBtn=(Button)findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                startIntent=new Intent(getApplicationContext(), Tab3InputInfo.class);
                startActivity(startIntent);

            }
        });

    }
}
