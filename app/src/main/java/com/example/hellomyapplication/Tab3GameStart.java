package com.example.hellomyapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tab3GameStart extends AppCompatActivity {
    int flag=1;
    int p_num=0;
    Button check;
    Button howto;
    Check myCheck;
    ImageView arrow0,arrow1,arrow2,arrow3,arrow4,arrow5;

    ImageView [] circles=new ImageView[6];

    TextView p_num_info,p_num_info2,info1,info2,info3,info4,infoText1,infoText2,infoText3,infoText4;
    ArrayList<Check> checks;
    MyTimer myTimer;
    ngrok addr = new ngrok();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_gamestart);
        //TODO: 게임시작할때 시간정보 서버에 저장
        //TODO: 게임 중이면(length==6) 앱 종료했다가 켜도 바로 게임화면으로 가도록->flag=0 만들기


        //Id 로 view 불러옴
        //info
        info1=(TextView)findViewById(R.id.info1); info2=(TextView)findViewById(R.id.info2); info3=(TextView)findViewById(R.id.info3); info4=(TextView)findViewById(R.id.info4);
        infoText1=(TextView)findViewById(R.id.infoText1); infoText2=(TextView)findViewById(R.id.infoText2); infoText3=(TextView)findViewById(R.id.infoText3); infoText4=(TextView)findViewById(R.id.infoText4);

        //화살표와 원
        arrow0=(ImageView)findViewById(R.id.arrow0); arrow1=(ImageView)findViewById(R.id.arrow1); arrow2=(ImageView)findViewById(R.id.arrow2); arrow3=(ImageView)findViewById(R.id.arrow3); arrow4=(ImageView)findViewById(R.id.arrow4); arrow5=(ImageView)findViewById(R.id.arrow5);
        int [] idArr={R.id.circle0,R.id.circle1,R.id.circle2,R.id.circle3,R.id.circle4,R.id.circle5};
        for(int i=0;i<6;i++){
            circles[i]=(ImageView)findViewById(idArr[i]);
        }

        //기타
        p_num_info=(TextView)findViewById(R.id.p_num_info);
        p_num_info2=(TextView)findViewById(R.id.p_num_info2);
        check=(Button)findViewById(R.id.check);
        check.setVisibility(View.GONE);
        howto=(Button)findViewById(R.id.howto);


        myTimer=new MyTimer(1000,1000);//6명 들어오기 전까지 대기, 6명 들어오면

        //TODO: 수정: myTimer 하나 더만들어서 시간마다 info가 보이게 바꿀것;



        check.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){

                //TODO: 서버로부터 참가중인 사람들 정보(name, my,team,hunt) 불러와서 Check class에 저장, checks(arraylist)에 저장 //순서가 꼬리잡기와 같지 않게 조심할것
                checks=new ArrayList<Check>();
//                for(int i=0;i<6;i++){
//                    checks.add(new Check(name,my,team,hunt))
//                }
                show();

            }
        });

        howto.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                show_dialog();
            }
        });



    }//onCreate 끝

    public void getorder(){
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
                        try {
                            for(int i=0;i<response.length();i++){
                                JSONObject temp = response.getJSONObject(i);
                                if(p_num<response.length()){
                                    p_num += 1;
                                }
                            }
                            easyToast(Integer.toString(response.length()));
                        } catch (JSONException e) {
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
    }

    void easyToast(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }

    class MyTimer extends CountDownTimer
    {
        int checknum=0;
        public MyTimer(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
            this.start();

        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(flag==1){
                //TODO: 서버에서 참가하는 사람수 업데이트->p_num;
                //일단 하드코딩
                getorder();
                p_num_info.setText(p_num+"");
                if(p_num==6){
                    flag=0;
                    checknum=1;
                }
            }
            else{

                myCheck=new Check();//TODO: 서버에서 불러온 내 정보 업데이트

            }

        }

        @Override
        public void onFinish() {
            if (checknum==1){
                p_num_info.setVisibility(View.GONE);
                p_num_info2.setVisibility(View.GONE);
                arrow0.setVisibility(View.VISIBLE);arrow1.setVisibility(View.VISIBLE);arrow2.setVisibility(View.VISIBLE);arrow3.setVisibility(View.VISIBLE);arrow4.setVisibility(View.VISIBLE);arrow5.setVisibility(View.VISIBLE);
                for(int i=0;i<6;i++){
                    circles[i].setVisibility(View.VISIBLE);
                }
                info1.setVisibility(View.VISIBLE);infoText1.setVisibility(View.VISIBLE);
                checknum=0;
            }
            this.start();
        }
    }


    void show(){// 사냥할 사람들의 목록 Dialog로 보여줌
        //ArrayList에 저장된 Check 클래스 "순서대로", Check 클래스의 name 필드를, ArrayList<String>에 저장해서 목록으로 보여줌
        //선택한 항목의 index로 Check 클래스 알아냄-> 클래스를 변수에 저장해서 내것과 비교
        final ArrayList<String> listItems=new ArrayList<>();
        for(int i=0;i<checks.size();i++){
            listItems.add(checks.get(i).getName());
        }
        final CharSequence[] items = listItems.toArray(new String[listItems.size()]);
        final List SelectedItems = new ArrayList();
        int defaultItem = 0;
        SelectedItems.add(defaultItem);

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사냥하기");
        builder.setSingleChoiceItems(items, defaultItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectedItems.clear();
                        SelectedItems.add(which);
                    }
                });

        builder.setPositiveButton("HUNT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (!SelectedItems.isEmpty()) {
                            int index = (int) SelectedItems.get(0);

                            Check isHunted = checks.get(index);// 선택한 사람의 class 정보
                            if(myCheck.getHuntIndex()==isHunted.getMyIndex()){//잡은경우
                                //정보수정,  상대방에게 내가 잡았다는것을 알려야함
                                myCheck.setHuntIndex(isHunted.getHuntIndex());
                                //TODO: 서버에게 isHunted.getMyIndex()를 myindex로 가지는 사람. 그사람의 teamindex 나와 같도록 바꾸라고 지시
                                //TODO: 그사람과 팀이 같던 사람들도 모두 teamindex 바꾸라고 지시
                                Toast.makeText(getApplicationContext(),"잡았다!!!",Toast.LENGTH_LONG).show();
                            }

                            else if(myCheck.getMyIndex()==isHunted.getHuntIndex()) {//잡힌경우
                                // 정보수정, 상대방에게 내가 잡혔다는 것을 알려야함
                                myCheck.setTeamIndex(isHunted.getTeamIndex());
                                //TODO: 서버에게 isHunted.getMyIndex()를 myindex로 가지는 사람. 그사람의 huntindex 나와 같도록 바꾸라고 지시
                                //TODO: 그사람과 팀이 같던 사람도 모두 huntindex 바꾸라고 지시
                                Toast.makeText(getApplicationContext(),"잡혔다ㅠㅠㅠ",Toast.LENGTH_LONG).show();
                            }
                            else{// 아무 사이 아닌 경우
                                Toast.makeText(getApplicationContext(),"아무일도 일어나지 않았다.",Toast.LENGTH_LONG).show();
                            }


                        }
                    }
                });

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }


    void show_dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog Title");
        builder.setMessage("1. 화살표 끝에 있는 피식자를 알아내야합니다. \n" +
                "2. 피식자의 정보가 한시간마다 하나씩 알려집니다. \n" +
                "3. 원한다면 언제든 서로 확인을 시도할 수 있습니다. \n" +
                "4. 확인했을때 천적관계에 있다면 천적의 팀이 됩니다");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.show();
    }

    void setImage(){
        int my=myCheck.getMyIndex(),hunt=myCheck.getHuntIndex();
        int top=my;
        int num;
        if(hunt>my)
            num=hunt-my;
        else
            num=6+hunt-my;
        if(num!=0) {
            for (int i = 0; i < num; i++) {
                circles[top].setBackgroundResource(R.drawable.circlenew);
                top = (top + 1) % 6;
            }
        }
    }

}






class Check{
    private String name;
    private int myIndex;
    private int teamIndex;
    private int HuntIndex;

    Check(String name,int myIndex,int teamIndex,int lastHuntIndex){
        this.name=name;
        this.myIndex=myIndex;
        this.teamIndex=teamIndex;
        this.HuntIndex=lastHuntIndex;
    }
    Check(){

    }
    public void setting(String name,int myIndex,int teamIndex,int lastHuntIndex){
        this.name=name;
        this.myIndex=myIndex;
        this.teamIndex=teamIndex;
        this.HuntIndex=lastHuntIndex;
    }

    public String getName() {
        return name;
    }

    public int getMyIndex() {
        return myIndex;
    }

    public int getHuntIndex() {
        return HuntIndex;
    }

    public int getTeamIndex() {
        return teamIndex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMyIndex(int myIndex) {
        this.myIndex = myIndex;
    }

    public void setTeamIndex(int teamIndex) {
        this.teamIndex = teamIndex;
    }

    public void setHuntIndex(int huntIndex) {
        HuntIndex = huntIndex;
    }
}