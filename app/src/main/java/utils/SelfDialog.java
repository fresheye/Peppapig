package utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ilzxm.peppapig.R;

/**
 * Created by hao on 2017/4/1.
 */

public class SelfDialog extends Dialog {
    private ImageButton menuBtn;//菜单按钮
    private ImageButton backBtn;//返回按钮
    private ImageButton nextBtn;//下一关按钮
    private ImageView star1;//星1
    private ImageView star2;//星2
    private ImageView star3;//星3
    //private TextView score1Text;//得分1文本
    private TextView score1Num;//得分1数字
    private TextView score2Text;//得分2文本
    private TextView score2Num;//得分2数字
    private int score1;//从外界设置的得分1
    private int score2;//从外界设置的得分2
    private int starNum;//从外界黄星星个数
    private onMenuOnclickListener menuOnclickListener;//菜单按钮被点击了的监听器
    private onBackOnclickListener backOnclickListener;//返回按钮被点击了的监听器
    private onNextOnclickListener nextOnclickListener;//下一关按钮被点击了的监听器

    public SelfDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        menuBtn = (ImageButton) findViewById(R.id.menuBtn);//菜单按钮
        backBtn = (ImageButton) findViewById(R.id.backBtn);//返回按钮
        nextBtn = (ImageButton) findViewById(R.id.nextBtn);//下一关按钮
        star1 = (ImageView) findViewById(R.id.star1);//星1
        star2 = (ImageView) findViewById(R.id.star2);//星2
        star3 = (ImageView) findViewById(R.id.star3);//星3
        //score1Text = (TextView)findViewById(R.id.score1Text);//得分1文本
        score1Num = (TextView) findViewById(R.id.score1Num);//得分1数字
        score2Text = (TextView)findViewById(R.id.score2Text);//得分2文本
        score2Num = (TextView) findViewById(R.id.score2Num);//得分2数字
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //用户定义黄色星星数量
        if (starNum == 3) {
            star1.setImageResource(R.mipmap.star_yellow);
            star2.setImageResource(R.mipmap.star_yellow);
            star3.setImageResource(R.mipmap.star_yellow);
        } else if (starNum == 2) {
            star1.setImageResource(R.mipmap.star_yellow);
            star2.setImageResource(R.mipmap.star_yellow);
            star3.setImageResource(R.mipmap.star_gray);
        } else if (starNum == 1) {
            star1.setImageResource(R.mipmap.star_yellow);
            star2.setImageResource(R.mipmap.star_gray);
            star3.setImageResource(R.mipmap.star_gray);
        } else {
            star1.setImageResource(R.mipmap.star_gray);
            star2.setImageResource(R.mipmap.star_gray);
            star3.setImageResource(R.mipmap.star_gray);
        }
        //设置得分
        score1Num.setText(String.valueOf(score1));
        score2Num.setText(String.valueOf(score2));
        //如果得分2是0，则不显示得分2
        if(score2 == 0){
            score2Text.setVisibility(View.INVISIBLE);
            score2Num.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 初始化界面的菜单、返回、下一关监听器
     */
    private void initEvent() {
        //菜单按钮被点击后，向外界提供监听
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuOnclickListener != null) {
                    menuOnclickListener.onMenuClick();
                }
            }
        });
        //返回按钮被点击后，向外界提供监听
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backOnclickListener != null) {
                    backOnclickListener.onBackClick();
                }
            }
        });
        //下一关按钮被点击后，向外界提供监听
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextOnclickListener != null) {
                    nextOnclickListener.onNextClick();
                }
            }
        });
    }


    /**
     * 设置菜单按钮的监听
     */
    public void setMenuOnclickListener(onMenuOnclickListener listener) {
        this.menuOnclickListener = listener;
    }

    /**
     * 设置返回按钮的监听
     */
    public void setBackOnclickListener(onBackOnclickListener listener) {
        this.backOnclickListener = listener;
    }

    /**
     * 设置下一关按钮的监听
     */
    public void setNextOnclickListener(onNextOnclickListener listener) {
        this.nextOnclickListener = listener;
    }


    /**
     * 设置菜单、返回、下一关被点击的接口
     */
    public interface onMenuOnclickListener {
        public void onMenuClick();
    }

    public interface onBackOnclickListener {
        public void onBackClick();
    }

    public interface onNextOnclickListener {
        public void onNextClick();
    }
}
