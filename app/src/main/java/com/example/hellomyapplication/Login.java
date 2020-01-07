package com.example.hellomyapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText email;
    EditText pwd;
    Button btn;
    Button signup_loginpg;
    String emailText;
    String pwdText;
    String key;
    Intent intent;
    Intent intent_signup;
    ngrok addr = new ngrok();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        //TODO: 권한 허용 하나로 묻기 (by수정)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        email=(EditText)findViewById(R.id.email);
        pwd=(EditText)findViewById(R.id.pwd);
        btn=(Button)findViewById(R.id.login);
        signup_loginpg=(Button)findViewById(R.id.signup);

        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                emailText=email.getText().toString();
                pwdText=pwd.getText().toString();
                requestLogin(emailText, pwdText);

            }

        });


        //회원가입
        signup_loginpg.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                intent_signup=new Intent(getApplicationContext(),Signup.class);
                startActivity(intent_signup);
            }
        });


    }

    public void requestLogin(String ID, String PW){
        /*NGrok을 쓸거라 그때그때마다 바뀔꺼임!!!*/
        String url = addr.geturl() + "/infos/login/local";
        //JSON형식으로 데이터 통신을 진행합니다!
        easyToast(url);
        JSONObject testjson = new JSONObject();
        try {
            //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
            testjson.put("email", ID);
            testjson.put("password", PW);
            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key값에 따sho라 value값을 쪼개 받아옵니다.
                        String resultId = jsonObject.getString("approve_id");
                        String resultPassword = jsonObject.getString("approve_pw");
                        String resultKey = jsonObject.getString("_id");

                        if(resultId.equals("OK") & resultPassword.equals("OK")){
                            Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                            intent=new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("key",resultKey);
                            startActivity(intent);
                            finish();
                        }else{
                            easyToast("이메일이나 비밀번호가 일치하지 않습니다.");
                        }

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

    void easyToast(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }



}