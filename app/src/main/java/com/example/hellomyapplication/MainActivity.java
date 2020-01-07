package com.example.hellomyapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUriExposedException;
import android.os.FileUtils;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ArrayList<PhoneBook> phoneBooks;
    public ArrayList<PhoneBook> phonetoServer;
    public ArrayList<PhoneBook> servertoPhone;
    private Tab2GalleryManager mGalleryManager;
    private RecyclerView recyclerGallery;
    private Tab2GalleryAdapter galleryAdapter;
    RecyclerView recyclerView;
    ngrok addr = new ngrok();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: 서버에 local phonebook(id, name, phonenum, imageNumber), gallery 저장하기
        phonetoServer=Loader.getData(this); //Uploading!
        String key = getIntent().getStringExtra("key");
        /*NGrok을 쓸거라 그때그때마다 바뀔꺼임!!!*/
        String url = addr.geturl() + "/contacts/private/books";
        for(int i =0; i<phonetoServer.size();i++){
            final JSONObject jsonkey = new JSONObject();
                try {
                    jsonkey.put("contactid", key);
                    jsonkey.put("name",phonetoServer.get(i).getName());
                    jsonkey.put("phonenumber",phonetoServer.get(i).getNumber());
//                    Bitmap temp = loadBackgroundBitmap(this,phonetoServer.get(i).getImageNumber());
                    final RequestQueue requestQueue = Volley.newRequestQueue(this);
                    final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,jsonkey, new Response.Listener<JSONObject>() {
                        //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(jsonObjectRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        phoneBooks=new ArrayList<>();
        String url2 = addr.geturl() + "/contacts/contactid/"+key;
        RequestQueue requestQueue = Volley.newRequestQueue(this);  //downloading!!
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url2,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                JSONObject temp = response.getJSONObject(i);
                                String name = temp.getString("name");
                                String phonenumber = temp.getString("phonenumber");
                                phoneBooks.add(new PhoneBook(Integer.toString(i), name, phonenumber, R.drawable.button_background));
                                Tab1TextAdapter adapter = new Tab1TextAdapter(phoneBooks) ;
                                recyclerView.setAdapter(adapter) ;
                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                }
        );
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);


        TabHost tabHost1=(TabHost)findViewById(R.id.tabHost1);
        tabHost1.setup();

        TabHost.TabSpec ts1=tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.phonebook);


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = findViewById(R.id.recycler1) ;
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
        ts3.setIndicator("Special").setContent(new Intent(this,Tab3Main.class));
        tabHost1.addTab(ts3);


    }
    public static Bitmap loadBackgroundBitmap(Context context, String imgFilePath) throws Exception, OutOfMemoryError{
        if(false){
            throw new FileNotFoundException("background-image file not found : " + imgFilePath);
        }

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgFilePath, options);

        float widthScale = options.outWidth / displayWidth;
        float heightScale = options.outHeight / displayHeight;
        float scale = widthScale > heightScale ? widthScale : heightScale;

        if (scale >= 8){
            options.inSampleSize = 8;
        }else if (scale >= 6){
            options.inSampleSize = 6;
        }else if (scale >= 4){
            options.inSampleSize = 4;
        }else if (scale >= 2){
            options.inSampleSize = 2;
        }else{
            options.inSampleSize = 1;
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imgFilePath, options);
    } //Url을 Bitmap으로!
    private String getStringFromBitmap(Bitmap bitmapPicture) {
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    } //bitmap을 Bytecode로 쪼개기




    /**
     * 레이아웃 초기화
     */
    private void initLayout() {

        recyclerGallery = (RecyclerView) findViewById(R.id.recyclerGallery);
    }
    void easyToast(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
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
class Loader{
    public static ArrayList<PhoneBook> getData(Context context){
        ArrayList<PhoneBook> datas=new ArrayList<>();
        ContentResolver resolver=context.getContentResolver();
        Uri phoneUri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String proj[]={ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        String sortOrder="case"+
                " when substr(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+", 1,1) BETWEEN 'ㄱ' AND '힣' then 1 "+
                " when substr(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+", 1,1) BETWEEN 'A' AND 'Z' then 2 "+
                " when substr(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+", 1,1) BETWEEN 'a' AND 'z' then 3 "+
                " else 4 end, " + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" COLLATE LOCALIZED ASC";
        Cursor cursor=resolver.query(phoneUri,proj,null,null,sortOrder);
        if(cursor!=null){
            while(cursor.moveToNext()){
                int index=cursor.getColumnIndex(proj[0]);
                String id=cursor.getString(index);

                index=cursor.getColumnIndex(proj[1]);
                String name=cursor.getString(index);

                index=cursor.getColumnIndex(proj[2]);
                String number=cursor.getString(index);


                PhoneBook book=new PhoneBook();
                book.setId(id);
                book.setName(name);
                book.setNumber(number);

                datas.add(book);

            }
        }
        cursor.close();
        return datas;
    }
}

