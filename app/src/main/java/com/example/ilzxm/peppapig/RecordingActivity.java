package com.example.ilzxm.peppapig;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

import utils.SelfDialog;


public class RecordingActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private String str1;
    private SpeechSynthesizer mTts;
    private TextView wordsText; //语音文字
    private ImageView roleView; //当前角色
    private ImageView dialogBoxView;    //文字框
    private ImageButton voiceBtn;   //声音按钮
    private ImageButton microphoneBtn;  //话筒
    private ImageView volumeView;   //音量显示
    private SelfDialog selfDialog;  //得分对话框
    //得分1，得分2，星1，星2
    private int score1;
    private int score2;
    private int star1;
    private int star2;
    private int num1;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.activity_recording);
        init();
        bundle=this.getIntent().getExtras();
        num1 = bundle.getInt("num");
        ImageShow();
    }

    private void init() {
        wordsText = (TextView) findViewById(R.id.wordsText);
        roleView = (ImageView) findViewById(R.id.roleView);
        dialogBoxView = (ImageView) findViewById(R.id.dialogBoxView);
        voiceBtn = (ImageButton) findViewById(R.id.voiceBtn);
        microphoneBtn = (ImageButton) findViewById(R.id.microphoneBtn);
        volumeView = (ImageView) findViewById(R.id.volumeView);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=586da44a");
        mTts = SpeechSynthesizer.createSynthesizer(this, null);
        set_mTts();

        microphoneBtn.setOnClickListener(this);
//        backgroundAlpha((float) 0.50);
        try {
            Random random=new Random();
            String a="text";
            String b=a+random.nextInt(3);
            File urlFile = new File("/sdcard/peppapig/text/"+b+".txt");
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            String mimeTypeLine = null ;
            while ((mimeTypeLine = br.readLine()) != null) {
                str = str+mimeTypeLine;
            }
            wordsText.setText(str);
            str1=str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        voiceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mTts.startSpeaking(str1, mTtsListener);
            }
        });

        //计算星星个数
       /* if (score1 >= 85) star1 = 3;
        else if (score1 < 85 && score1 >= 70) star1 = 2;
        else if (score1 < 70 && score1 >= 55) star1 = 1;
        else star1 = 0;

        if (score2 >= 85) star2 = 3;
        else if (score2 < 85 && score2 >= 70) star2 = 2;
        else if (score2 < 70 && score2 >= 55) star2 = 1;
        else star2 = 0;*/
        scoreDialog(80,2);
    }
    @Override
    public void onClick(View v) {
        btnVoice();
    }
    //TODO 开始说话：
    private void btnVoice() {
        RecognizerDialog dialog = new RecognizerDialog(this,null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult);
            }
            @Override
            public void onError(SpeechError speechError) {
            }
        });
        dialog.show();
        Toast.makeText(this, "请开始说话", Toast.LENGTH_SHORT).show();
    }
    //回调结果：
    private void printResult(RecognizerResult results) {
        String text = parseIatResult(results.getResultString());
        // 自动填写地址
        Toast toast=Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT);
        toast.show();
        selfDialog.show();
    }
    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }
    private void set_mTts() {
        // 设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        // 设置语速
        mTts.setParameter(SpeechConstant.SPEED, "20");
        // 设置音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        // 设置音量0-100
        mTts.setParameter(SpeechConstant.VOLUME, "100");
        // 设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 如果不需要保存保存合成音频，请注释下行代码
        // mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,
        // "./sdcard/iflytek.pcm");

    }
    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        // 缓冲进度回调，arg0为缓冲进度，arg1为缓冲音频在文本中开始的位置，arg2为缓冲音频在文本中结束的位置，arg3为附加信息
        @Override
        public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
            // TODO Auto-generated method stub

        }

        // 会话结束回调接口，没有错误时error为空
        @Override
        public void onCompleted(SpeechError error) {
            // TODO Auto-generated method stub

        }

        // 开始播放
        @Override
        public void onSpeakBegin() {
            // TODO Auto-generated method stub

        }

        // 停止播放
        @Override
        public void onSpeakPaused() {
            // TODO Auto-generated method stub

        }

        // 播放进度回调,arg0为播放进度0-100；arg1为播放音频在文本中开始的位置，arg2为播放音频在文本中结束的位置。
        @Override
        public void onSpeakProgress(int arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        // 恢复播放回调接口
        @Override
        public void onSpeakResumed() {
            // TODO Auto-generated method stub

        }
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {}
    };
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
        selfDialog = new SelfDialog(RecordingActivity.this);
        selfDialog.setScore1(score);
        selfDialog.setStarNum(star);
        selfDialog.setMenuOnclickListener(new SelfDialog.onMenuOnclickListener() {
            @Override
            public void onMenuClick() {
                Intent intent = new Intent();
                intent.setClass(RecordingActivity.this, LevelActivity.class);
                RecordingActivity.this.startActivity(intent);
                selfDialog.dismiss();
            }
        });
//        selfDialog.show();
    }

    private void scoreDialog(int score1, int score2, int star1, int star2) {//输入两个得分
        selfDialog = new SelfDialog(RecordingActivity.this);
        selfDialog.setScore1(score1);
        selfDialog.setScore2(score2);
        selfDialog.setStarNum(star1);
        selfDialog.setMenuOnclickListener(new SelfDialog.onMenuOnclickListener() {
            @Override
            public void onMenuClick() {
                Toast.makeText(RecordingActivity.this, "点击了--菜单--按钮", Toast.LENGTH_LONG).show();
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }
    @Override
    protected void onDestroy() {
        mTts.stopSpeaking();
        mTts.destroy();// 退出时释放连接
        super.onDestroy();
    }
    public void ImageShow() {
        switch (num1) {
            case 1:
                roleView.setImageResource(R.mipmap.pig_dad);
                break;
            case 2:
                roleView.setImageResource(R.mipmap.pig_mum);
                break;
            case 3:
                roleView.setImageResource(R.mipmap.pig2);
                break;
            case 4:
                roleView.setImageResource(R.mipmap.pig1);
                break;
        }
    }
}
