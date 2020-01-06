package com.example.hellomyapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Tab2FullScreenImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.tab2_activity_full_screen_image);
//        ImageView fullScreenImageView=(ImageView) findViewById(R.id.fullScreenImageView);
        setContentView(R.layout.tab2_activity_fullscreen);
        ImageView fullScreenImageView=(ImageView) findViewById(R.id.fullScreenImageView);

        Intent callingActivityIntent=getIntent();
        String name=callingActivityIntent.getExtras().getString("imgPath");
//        ArrayList<String> paths;
//        paths=callingActivityIntent.getExtras().getStringArrayList("paths");
        String[] paths;
        paths=callingActivityIntent.getExtras().getStringArray("paths");

        int position= callingActivityIntent.getExtras().getInt("position");

        ViewPager viewPager;
        viewPager=(ViewPager) findViewById((R.id.pager));
        com.example.hellomyapplication.Tab2FullScreenImageAdapter fullSizeAdapter=new com.example.hellomyapplication.Tab2FullScreenImageAdapter(this, paths);

        viewPager.setAdapter(fullSizeAdapter);
        viewPager.setCurrentItem(position, true);

//        if(callingActivityIntent!=null){
////            Uri imageUri=callingActivityIntent.getData();
//            if(name!=null && fullScreenImageView!=null){
//                Glide.with(this)
//                        .load(name)
//                        .into(fullScreenImageView);
//            }
//        }

    }
}
