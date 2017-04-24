package com.example.ilzxm.peppapig;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import utils.SysApplication;


/**
 * Created by hao on 2017/3/24.
 */

public class ChooseRoleActivity extends Activity {
    private LinearLayout dadLayout;//猪爸爸布局
    private LinearLayout mumLayout;//猪妈妈布局
    private LinearLayout peppaLayout;//佩奇布局
    private LinearLayout georgeLayout;//乔治布局
    private ImageView dadArrowImage;//猪爸爸箭头
    private ImageView mumArrowImage;//猪妈妈箭头
    private ImageView peppaArrowImage;//佩奇箭头
    private ImageView georgeArrowImage;//乔治箭头
    private ImageView dadImage;//猪爸爸图片
    private ImageView mumImage;//猪妈妈图片
    private ImageView peppaImage;//佩奇图片
    private ImageView georgeImage;//乔治图片
    private ImageButton okBtn;//确认按钮
    private Bundle bundle;
    private int role_num;
    private int level_num;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题,AppCompatActivity无法全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        bundle=this.getIntent().getExtras();
        level_num = bundle.getInt("level_num");
        setContentView(R.layout.activity_choose_role);
        init();
    }

    private void init() {
        dadLayout = (LinearLayout) findViewById(R.id.dadLayout);//猪爸爸布局
        mumLayout = (LinearLayout) findViewById(R.id.mumLayout);//猪妈妈布局
        peppaLayout = (LinearLayout) findViewById(R.id.peppaLayout);//佩奇布局
        georgeLayout = (LinearLayout) findViewById(R.id.georgeLayout);//乔治布局
        dadArrowImage = (ImageView) findViewById(R.id.dadArrowImage);//猪爸爸箭头
        mumArrowImage = (ImageView) findViewById(R.id.mumArrowImage);//猪妈妈箭头
        peppaArrowImage = (ImageView) findViewById(R.id.peppaArrowImage);//佩奇箭头
        georgeArrowImage = (ImageView) findViewById(R.id.georgeArrowImage);//乔治箭头
        georgeLayout = (LinearLayout) findViewById(R.id.georgeLayout);//乔治布局
        dadImage = (ImageView) findViewById(R.id.dadImage);//猪爸爸图片
        mumImage = (ImageView) findViewById(R.id.mumImage);//猪妈妈图片
        peppaImage = (ImageView) findViewById(R.id.peppaImage);//佩奇图片
        georgeImage = (ImageView) findViewById(R.id.georgeImage);//乔治图片
        okBtn = (ImageButton) findViewById(R.id.okBtn);//确认按钮
        okBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(ChooseRoleActivity.this, PlayingActivity.class);
                bundle.putInt("level_num", level_num);
                bundle.putInt("role_num", role_num);
                intent.putExtras(bundle);
                SysApplication.getInstance().addActivity(ChooseRoleActivity.this);
                ChooseRoleActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * dp转为px
     *
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    private int dip2px(Context context, float dipValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }

    //设置监听,注意这里必须时public
    public void clickEvent(View v) {
        //先把所有箭头隐藏，所有布局都变小
        if (dadArrowImage.getVisibility() == View.VISIBLE)
            dadArrowImage.setVisibility(View.INVISIBLE);
        if (mumArrowImage.getVisibility() == View.VISIBLE)
            mumArrowImage.setVisibility(View.INVISIBLE);
        if (peppaArrowImage.getVisibility() == View.VISIBLE)
            peppaArrowImage.setVisibility(View.INVISIBLE);
        if (georgeArrowImage.getVisibility() == View.VISIBLE)
            georgeArrowImage.setVisibility(View.INVISIBLE);
//        LinearLayout.LayoutParams lParams1 = (LinearLayout.LayoutParams) peppaImage.getLayoutParams();
//        //dadImage.getLayoutParams();
//        lParams1.width = dip2px(ChooseRoleActivity.this, 120);
//        lParams1.height = dip2px(ChooseRoleActivity.this,225);
//        peppaImage.setLayoutParams(lParams1);

        //对应箭头显示，布局变大
        switch (v.getId()) {
            case R.id.dadImage:
                role_num=1;
                dadArrowImage.setVisibility(View.VISIBLE);//箭头可见
//                LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) dadImage.getLayoutParams();
//                //dadImage.getLayoutParams();
//                lParams.width = dip2px(ChooseRoleActivity.this, 160);
//                lParams.height = dip2px(ChooseRoleActivity.this,265);
//                dadImage.setLayoutParams(lParams);
                break;
            case R.id.mumImage:
                role_num=2;
                mumArrowImage.setVisibility(View.VISIBLE);
                break;
            case R.id.peppaImage:
                role_num=3;
                peppaArrowImage.setVisibility(View.VISIBLE);
                break;
            case R.id.georgeImage:
                role_num=4;
                georgeArrowImage.setVisibility(View.VISIBLE);
                break;
        }
    }
}
