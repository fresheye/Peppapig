package com.example.ilzxm.peppapig;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import utils.SelfDialog;
import utils.SysApplication;


public class ScoreActivity extends AppCompatActivity{
    private SelfDialog selfDialog;  //得分对话框
    private ImageView roleView;
    //得分1，得分2，星1，星2
    private int score1;
    private int score2;
    private int star1;
    private int star2;
    private int level_num;
    private int star_num;
    private int judge=1;
    private int star_judge=1;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.activity_score);
        init();
        bundle=this.getIntent().getExtras();
        level_num= bundle.getInt("level_num");
    }

    private void init() {
        backgroundAlpha((float) 0.50);
        Random random=new Random();
        score1=random.nextInt(50)+50;
        //计算星星个数
        if (score1 >= 85) star1 = 3;
        else if (score1 < 85 && score1 >= 70) star1 = 2;
        else if (score1 < 70 && score1 >= 55) star1 = 1;
        else star1 = 0;

        if (score2 >= 85) star2 = 3;
        else if (score2 < 85 && score2 >= 70) star2 = 2;
        else if (score2 < 70 && score2 >= 55) star2 = 1;
        else star2 = 0;
        scoreDialog(score1,star1);
        star_num=star1;
    }
    /**
     * 设置屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 得分对话框
     */
    private void scoreDialog(int score, int star) {//输入一个得分
        selfDialog = new SelfDialog(ScoreActivity.this);
        selfDialog.setScore1(score);
        selfDialog.setStarNum(star);
        selfDialog.setMenuOnclickListener(new SelfDialog.onMenuOnclickListener() {
            @Override
            public void onMenuClick() {
                Intent intent = new Intent();
                intent.setClass(ScoreActivity.this, LevelActivity.class);
                bundle.putInt("level_num", level_num);
                bundle.putInt("star_num", star_num);
                bundle.putInt("star_judge", star_judge);
                intent.putExtras(bundle);
                SysApplication.getInstance().addActivity(ScoreActivity.this);
                ScoreActivity.this.startActivity(intent);
                selfDialog.dismiss();
            }
        });
        selfDialog.setBackOnclickListener(new SelfDialog.onBackOnclickListener() {
            @Override
            public void onBackClick() {
                Intent intent = new Intent();
                intent.setClass(ScoreActivity.this, PlayingActivity.class);
                bundle.putInt("level_num", level_num);
                intent.putExtras(bundle);
                SysApplication.getInstance().addActivity(ScoreActivity.this);
                ScoreActivity.this.startActivity(intent);
                selfDialog.dismiss();
            }
        });
        selfDialog.setNextOnclickListener(new SelfDialog.onNextOnclickListener() {
            @Override
            public void onNextClick() {
                Intent intent = new Intent();
                intent.setClass(ScoreActivity.this, PlayModeActivity.class);
                bundle.putInt("level_num", level_num);
                bundle.putInt("judge", judge);
                intent.putExtras(bundle);
                SysApplication.getInstance().addActivity(ScoreActivity.this);
                ScoreActivity.this.startActivity(intent);
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }

    private void scoreDialog(int score1, int score2, int star1, int star2) {//输入两个得分
        selfDialog = new SelfDialog(ScoreActivity.this);
        selfDialog.setScore1(score1);
        selfDialog.setScore2(score2);
        selfDialog.setStarNum(star1);
        selfDialog.setMenuOnclickListener(new SelfDialog.onMenuOnclickListener() {
            @Override
            public void onMenuClick() {
                Toast.makeText(ScoreActivity.this, "点击了--菜单--按钮", Toast.LENGTH_LONG).show();
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }
//    public void ImageShow() {
//        switch (num1) {
//            case 1:
//                roleView.setImageResource(R.mipmap.pig_dad);
//                break;
//            case 2:
//                roleView.setImageResource(R.mipmap.pig_mum);
//                break;
//            case 3:
//                roleView.setImageResource(R.mipmap.pig2);
//                break;
//            case 4:
//                roleView.setImageResource(R.mipmap.pig1);
//                break;
//        }
//    }
private void turn_page(){
    Intent intent = new Intent();
    intent.setClass(ScoreActivity.this, LevelActivity.class);
    ScoreActivity.this.startActivity(intent);
}

}
