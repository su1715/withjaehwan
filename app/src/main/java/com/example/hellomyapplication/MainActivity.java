package com.example.hellomyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TabHost;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<PhoneBook> phoneBooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: 서버에 local phonebook, gallery 저장하기

        TabHost tabHost1=(TabHost)findViewById(R.id.tabHost1);
        tabHost1.setup();

        TabHost.TabSpec ts1=tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.phonebook);

        phoneBooks=new ArrayList<>();
//        phoneBooks=Loader.getData(this); TODO: 서버에서 phonebook 정보 가져오기
        //일단은 하드코딩
        phoneBooks.add(new PhoneBook("0","박수정","010-7183-8939"));
        phoneBooks.add(new PhoneBook("1","박수정","010-7183-8939"));
        phoneBooks.add(new PhoneBook("2","박수정","010-7183-8939"));
        phoneBooks.add(new PhoneBook("3","박수정","010-7183-8939"));
        phoneBooks.add(new PhoneBook("4","박수정","010-7183-8939"));
        phoneBooks.add(new PhoneBook("5","박수정","010-7183-8939"));
        phoneBooks.add(new PhoneBook("6","박수정","010-7183-8939"));
        phoneBooks.add(new PhoneBook("7","박수정","010-7183-8939"));
        phoneBooks.add(new PhoneBook("8","박수정","010-7183-8939"));
        phoneBooks.add(new PhoneBook("9","박수정","010-7183-8939"));




        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 Tab1TextAdapter 객체 지정.
        Tab1TextAdapter adapter = new Tab1TextAdapter(phoneBooks) ;
        recyclerView.setAdapter(adapter) ;


        ts1.setIndicator("PhoneBook");
        tabHost1.addTab(ts1);

        TabHost.TabSpec ts2=tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.gallery);
        ts2.setIndicator("Gallery");
        tabHost1.addTab(ts2);


        TabHost.TabSpec ts3=tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.special);
        ts3.setIndicator("Special");
        tabHost1.addTab(ts3);


    }
}
