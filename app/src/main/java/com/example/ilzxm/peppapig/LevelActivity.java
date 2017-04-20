package com.example.ilzxm.peppapig;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import utils.SysApplication;

/**
 * Created by ilzxm on 2017/3/10.
 */

public class LevelActivity extends AppCompatActivity {
    private ImageButton button1;
    private ImageButton button2;
    private int level_num;
    private static boolean isExit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.activity_level);
//        button1=(ImageButton)findViewById(R.id.level1);
//        button2=(ImageButton)findViewById(R.id.level2);
//        button1.setOnClickListener(new OnClickListener(){
//        @Override
//            public void onClick(View v){
//            Intent intent = new Intent();
//            intent.setClass(LevelActivity.this, TransitionActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putInt("level_num", level_num);
//            intent.putExtras(bundle);
//            LevelActivity.this.startActivity(intent);
//        }
//        });
//        button2.setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent();
//                intent.setClass(LevelActivity.this, TransitionActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("level_num", level_num);
//                intent.putExtras(bundle);
//                LevelActivity.this.startActivity(intent);
//            }
//        });
    }
    public void clickEvent1(View v) {
        switch (v.getId()) {
            case R.id.level1:
                level_num=1;
                break;
            case R.id.level2:
                level_num=2;
                break;
        }
        Intent intent = new Intent();
        intent.setClass(LevelActivity.this, TransitionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("level_num", level_num);
        intent.putExtras(bundle);
        SysApplication.getInstance().addActivity(LevelActivity.this);
        LevelActivity.this.startActivity(intent);
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else{
            SysApplication.getInstance().exit();
        }
    }

}
