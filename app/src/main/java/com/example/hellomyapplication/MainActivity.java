package com.example.hellomyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ArrayList<PhoneBook> phoneBooks;

    private Tab2GalleryManager mGalleryManager;
    private RecyclerView recyclerGallery;
    private Tab2GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: 서버에 local phonebook(id, name, phonenum, imageNumber), gallery 저장하기

        TabHost tabHost1=(TabHost)findViewById(R.id.tabHost1);
        tabHost1.setup();

        TabHost.TabSpec ts1=tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.phonebook);

        phoneBooks=new ArrayList<>();
//        phoneBooks=Loader.getData(this); TODO: 서버에서 phonebook 정보 가져오기(id, name, phonenum, imageNumber)
        //일단은 하드코딩
        phoneBooks.add(new PhoneBook("0","박수정","010-7183-8939",R.drawable.button_background));
        phoneBooks.add(new PhoneBook("1","박수정","010-7183-8939",R.drawable.button_background));
        phoneBooks.add(new PhoneBook("2","박수정","010-7183-8939",R.drawable.button_background));
        phoneBooks.add(new PhoneBook("3","박수정","010-7183-8939",R.drawable.button_background));
        phoneBooks.add(new PhoneBook("4","박수정","010-7183-8939",R.drawable.button_background));
        phoneBooks.add(new PhoneBook("5","박수정","010-7183-8939",R.drawable.button_background));
        phoneBooks.add(new PhoneBook("6","박수정","010-7183-8939",R.drawable.button_background));
        phoneBooks.add(new PhoneBook("7","박수정","010-7183-8939",R.drawable.button_background));
        phoneBooks.add(new PhoneBook("8","박수정","010-7183-8939",R.drawable.button_background));
        phoneBooks.add(new PhoneBook("9","박수정","010-7183-8939",R.drawable.button_background));




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

        initLayout();
        init();


        TabHost.TabSpec ts3=tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.special);
        ts3.setIndicator("Special");
        tabHost1.addTab(ts3);


    }




    //tab2필요한 부분
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) { //이게 뭘까?
//
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }


    /**
     * 레이아웃 초기화
     */
    private void initLayout() {

        recyclerGallery = (RecyclerView) findViewById(R.id.recyclerGallery);
    }


    /**
     * 데이터 초기화
     */
    private void init() {

        //갤러리 리사이클러뷰 초기화
        initRecyclerGallery();
    }


    /**
     * 갤러리 아미지 데이터 초기화
     */
    private List<Tab2PhotoVO> initGalleryPathList() {

        mGalleryManager = new Tab2GalleryManager(getApplicationContext());
        return mGalleryManager.getAllPhotoPathList();
    }

    /**
     * 갤러리 리사이클러뷰 초기화
     */
    private void initRecyclerGallery() {

        galleryAdapter = new Tab2GalleryAdapter(MainActivity.this, initGalleryPathList(), R.layout.tab2_item_photo);
        galleryAdapter.setOnItemClickListener(mOnItemClickListener);
        recyclerGallery.setAdapter(galleryAdapter);
        recyclerGallery.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerGallery.setItemAnimator(new DefaultItemAnimator());
//        recyclerGallery.addItemDecoration(new GridDividerDecoration(getResources(), R.drawable.divider_recycler_gallery));
    }


    /**
     * 리사이클러뷰 아이템 선택시 호출 되는 리스너
     */
    private Tab2OnItemClickListener mOnItemClickListener = new Tab2OnItemClickListener() {

        @Override
        public void OnItemClick(Tab2GalleryAdapter.PhotoViewHolder photoViewHolder, int position) {

            Tab2PhotoVO photoVO = galleryAdapter.getmPhotoList().get(position);
            System.out.println(galleryAdapter.getmPhotoList().size());
            String[] paths=new String[galleryAdapter.getmPhotoList().size()];

            for (int i=0;i<galleryAdapter.getmPhotoList().size();i++){
                paths[i]=galleryAdapter.getmPhotoList().get(i).getImgPath();
            }

//            System.out.println(position);
//            while(galleryAdapter)

//            List<Tab2PhotoVO> images=galleryAdapter.getmPhotoList();

            Intent fullScreenIntent=new Intent(getApplicationContext(), Tab2FullScreenImageActivity.class);
            fullScreenIntent.putExtra("imgPath", photoVO.getImgPath());
            fullScreenIntent.putExtra("paths",paths );
            fullScreenIntent.putExtra("position", position);

//        Data(PhotoVO.getImgPath());
            photoViewHolder.imgPhoto.getContext().startActivity(fullScreenIntent);

        }
    };





}
