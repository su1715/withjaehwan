package com.example.hellomyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tab3InputInfo extends AppCompatActivity {
    EditText info1,info2,info3,info4,nameinfo;
    String info_1,info_2,info_3,info_4,name_info;
    Button inputEnd;
    Intent intentToStart;
    String key = ((MainActivity)MainActivity.context).globalkey;
    ngrok addr = new ngrok();
    int myturn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_input_info);
        info1=(EditText)findViewById(R.id.info1);
        info2=(EditText)findViewById(R.id.info2);
        info3=(EditText)findViewById(R.id.info3);
        info4=(EditText)findViewById(R.id.info4);
        nameinfo=(EditText)findViewById(R.id.nameinfo);
        inputEnd=(Button)findViewById(R.id.inputEnd);



        inputEnd.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){

                info_1=info1.getText().toString();
                info_2=info2.getText().toString();
                info_3=info3.getText().toString();
                info_4=info4.getText().toString();
                name_info=nameinfo.getText().toString();
                int order = getorder();
                if(info_1.length()!=0 && info_2.length()!=0 && info_3.length()!=0 && info_4.length()!=0 && name_info.length()!=0) {
                    getready(info_1,info_2,info_3,info_4,name_info,key,order);
                }
                else{
                    Toast toast=Toast.makeText(view.getContext(),"정보를 모두 입력해주세요.",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });



    }
    public void getready(String info1, String info2,String info3,String info4,String name,String key,int order){
        /*NGrok을 쓸거라 그때그때마다 바뀔꺼임!!!*/
        String url = addr.geturl() + "/gamedbs";

        //JSON형식으로 데이터 통신을 진행합니다!

        JSONObject testjson = new JSONObject();
        try {
            //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
            testjson.put("key", key);
            testjson.put("name", name);
            testjson.put("hint1", info1);
            testjson.put("hint2", info2);
            testjson.put("hint3", info3);
            testjson.put("hint4", info4);

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(Tab3InputInfo.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //받은 json형식의 응답을 받아
                        intentToStart = new Intent(getApplicationContext(), Tab3GameStart.class);
                        intentToStart.putExtra("name",name_info);
                        startActivity(intentToStart);

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
    public int getorder(){
        /*NGrok을 쓸거라 그때그때마다 바뀔꺼임!!!*/
        String url = addr.geturl() + "/games";
        RequestQueue requestQueue = Volley.newRequestQueue(this);  //downloading!!
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        myturn = response.length();
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
        return myturn;
    }

    void easyToast(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }

}
