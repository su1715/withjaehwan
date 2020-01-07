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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Signup extends AppCompatActivity {
    EditText emailT,pwdT,pwdvfT;
    Button signupBtn,cancelBtn;
    String emailString,pwdString,pwdvfString;
    Intent intent_login;
    ngrok addr = new ngrok();
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
                        register(emailString,pwdString);

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

    public void register(String ID, String PW){
        /*NGrok을 쓸거라 그때그때마다 바뀔꺼임!!!*/
        String url = addr.geturl() + "/infos/login/register";

        //JSON형식으로 데이터 통신을 진행합니다!

        JSONObject testjson = new JSONObject();
        try {
            //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.

            testjson.put("infoid", ID);
            testjson.put("pw", PW);
            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key값에 따라 value값을 쪼개 받아옵니다.
                        String keys = jsonObject.getString("key");
                        if(keys.equals("success")){
                            startActivity(intent_login);
                            finish();
                        }else{
                            easyToast("이미 가입된 아이디입니다.");
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
