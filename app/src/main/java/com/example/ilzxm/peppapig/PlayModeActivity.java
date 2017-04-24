package com.example.ilzxm.peppapig;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import utils.SysApplication;

/**
 * Created by ilzxm on 2017/3/10.
 */

public class PlayModeActivity extends AppCompatActivity {
    private Button t1Button;
    private Button t2Button;
    private TextView tLevel_num;
    private Bundle bundle;
    private int level_num;
    private int judge;
    private int star_judge=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.activity_play_mode);
        bundle=this.getIntent().getExtras();
        level_num = bundle.getInt("level_num");
        judge = bundle.getInt("judge" );
        t1Button=(Button)findViewById(R.id.t1_pic);
        t2Button=(Button)findViewById(R.id.t2_pic);
        tLevel_num=(TextView)findViewById(R.id.level_num);
        judgement(judge);
        t1Button.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(PlayModeActivity.this, TransitionActivity.class);
                bundle.putInt("level_num", level_num);
                intent.putExtras(bundle);
                SysApplication.getInstance().addActivity(PlayModeActivity.this);
                PlayModeActivity.this.startActivity(intent);
            }
        });
        t2Button.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
//                Intent intent = new Intent();
//                intent.setClass(PlayModeActivity.this, ChooseRoleActivity.class);
//                SysApplication.getInstance().addActivity(PlayModeActivity.this);
//                PlayModeActivity.this.startActivity(intent);
            }
        });
    }
    private void judgement(int i)
    {
        switch (i){
            case 0:
                tLevel_num.setText("Level "+level_num);
                break;
            case 1:
                level_num=level_num+1;
                tLevel_num.setText("Level "+level_num);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        //TODO something
        Intent intent = new Intent();
        intent.setClass(PlayModeActivity.this, LevelActivity.class);
        Bundle bundle1=new Bundle();
        bundle1.putInt("star_judge", star_judge);
        intent.putExtras(bundle1);
        SysApplication.getInstance().addActivity(PlayModeActivity.this);
        PlayModeActivity.this.startActivity(intent);
    }
}
