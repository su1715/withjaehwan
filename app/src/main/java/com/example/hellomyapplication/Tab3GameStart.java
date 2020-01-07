package com.example.hellomyapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Tab3GameStart extends AppCompatActivity {
    int flag=1;
    int p_num=0;
    Button check;
    Button howto;
    int baseX,baseY;
    Check myCheck;
    TextView p_num_info,p_num_info2,info1,info2,info3,info4,infoText1,infoText2,infoText3,infoText4;
    ArrayList<Check> checks;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_gamestart);
        //TODO: 게임시작할때 시간정보 서버에 저장
        //TODO: 게임 중이면 앱 종료했다가 켜도 바로 게임화면으로 가도록->flag=0 만들기


        info1=(TextView)findViewById(R.id.info1); info2=(TextView)findViewById(R.id.info2); info3=(TextView)findViewById(R.id.info3); info4=(TextView)findViewById(R.id.info4);
        infoText1=(TextView)findViewById(R.id.infoText1); infoText2=(TextView)findViewById(R.id.infoText2); infoText3=(TextView)findViewById(R.id.infoText3); infoText4=(TextView)findViewById(R.id.infoText4);
        p_num_info=(TextView)findViewById(R.id.p_num_info);
        p_num_info2=(TextView)findViewById(R.id.p_num_info2);
        check=(Button)findViewById(R.id.check);
        check.setVisibility(View.GONE);
        howto=(Button)findViewById(R.id.howto);

        while(flag==1){
            //TODO: 1초마다 로그인한 사람정보, 로그인한 사람 수 정보 p_num에 받아오기
            p_num=6;//일단 하드코딩으로 사람 수 설정
            p_num_info.setText(p_num+"");

            //TODO: 1초 멈추는 메소드 걸것.
            if(p_num==6){
                flag=0;
            }
        }
        //gamestart
        //TODO: 게임시작시간 저장;
        p_num_info.setVisibility(View.GONE);
        p_num_info2.setVisibility(View.GONE);
        info1.setVisibility(View.VISIBLE);infoText1.setVisibility(View.VISIBLE);
        //TODO: 6명의 key 값 가져와서 ArrayList1에 저장/ ArrayList-> shuffle 시켜서 ArrayList2에 저장/ ArrayList2 index값으로 DB 정보 초기화-> 본인정보: index, 팀번호: index, 마지막사냥: index
        //TODO: 본인 정보 업데이트
        myCheck=new Check();//서버에서 불러와 넣을 것
        //TODO: 자신이 잡아야하는 사람 정보((마지막사냥번호+1)%6) 불러와서 infoText1-4에 각각 세팅, 시간지날때마다 visible로 바꾸기 (기본값 View.GONE)

        check.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){

                //TODO: ArrayList1 -> Check class에 저장, ArrayList check에 저장
                checks=new ArrayList<Check>();
                show();

            }
        });

        howto.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                show_dialog();
            }
        });

        //TODO: 원그리기, 화살표 그리기(수정)

        //TODO: 일정시간마다 서버 시간 확인해서 1시간마다 정보 추가로 알려주기( textview visible로 바꾸기 )

    }//onCreate 끝


    void show(){
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
                            if(myCheck.getMyIndex()==(isHunted.getMyIndex()+1)%6){//잡은경우// TODO: DB 저장 어떻게저장할지 결정하고 수정하기(수정)
                                Toast.makeText(getApplicationContext(),"잡았다!!!",Toast.LENGTH_LONG).show();
                            }

                            else if(false) {//잡힌경우
                                Toast.makeText(getApplicationContext(),"잡혔다ㅠㅠㅠ",Toast.LENGTH_LONG).show();
                            }
                            else{// 아무 사이 아닌 경우
                                Toast.makeText(getApplicationContext(),"아무일도 일어나지 않았다.",Toast.LENGTH_LONG).show();
                             }

                            // TODO: 서버정보 업데이트/잡은팀= lastHuntIndex 업데이트(+1%6), 잡힌팀= teamIndex 업데이트, lastHuntIndex 업데이트
                            // TODO: 서버에서 본인정보(myCheck) 받아오기
                            // TODO: 본인정보(myCheck)에 따라 그림 업데이트
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

    void makeCircle(int p_num){
        for(int i=0;i<p_num;i++){
            double x=baseX+100*cos(Math.PI*i/p_num);
            double y=baseY+100*sin(Math.PI*i/p_num);


        }
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

}
class Check{
    private String name;
    private int myIndex;
    private int teamIndex;
    private int lastHuntIndex;

    Check(String name,int myIndex,int teamIndex,int lastHuntIndex){
        this.name=name;
        this.myIndex=myIndex;
        this.teamIndex=teamIndex;
        this.lastHuntIndex=lastHuntIndex;
    }
    Check(){

    }
    public void setting(String name,int myIndex,int teamIndex,int lastHuntIndex){
        this.name=name;
        this.myIndex=myIndex;
        this.teamIndex=teamIndex;
        this.lastHuntIndex=lastHuntIndex;
    }

    public String getName() {
        return name;
    }

    public int getMyIndex() {
        return myIndex;
    }

    public int getLastHuntIndex() {
        return lastHuntIndex;
    }

    public int getTeamIndex() {
        return teamIndex;
    }

}