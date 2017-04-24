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

public class TransitionActivity extends AppCompatActivity {
    private Button t1Button;
    private Button t2Button;
    private TextView tLevel_num;
    private Bundle bundle;
    private int level_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.activity_transition);
        bundle=this.getIntent().getExtras();
        level_num = bundle.getInt("level_num");
        t1Button=(Button)findViewById(R.id.t1_pic);
        t2Button=(Button)findViewById(R.id.t2_pic);
        tLevel_num=(TextView)findViewById(R.id.level_num);
        tLevel_num.setText("Level "+level_num);
        t1Button.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(TransitionActivity.this, PlayerActivity.class);
                bundle.putInt("level_num", level_num);
                intent.putExtras(bundle);
                SysApplication.getInstance().addActivity(TransitionActivity.this);
                TransitionActivity.this.startActivity(intent);
            }
        });
        t2Button.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(TransitionActivity.this, ChooseRoleActivity.class);
                bundle.putInt("level_num", level_num);
                intent.putExtras(bundle);
                SysApplication.getInstance().addActivity(TransitionActivity.this);
                TransitionActivity.this.startActivity(intent);
            }
        });
    }

}
