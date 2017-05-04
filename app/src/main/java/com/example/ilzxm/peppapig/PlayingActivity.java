package com.example.ilzxm.peppapig;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

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
import java.util.Formatter;
import java.util.Locale;
import java.util.Random;

import utils.SysApplication;

import static com.example.ilzxm.peppapig.R.id.videoView;


/**
 * Created by ilzxm on 2017/3/23.
 */

public class PlayingActivity extends AppCompatActivity {
    private VideoView videoview;
    private Handler handler = new Handler();
    private ImageButton skip;
    private SpeechSynthesizer mTts;
    private ImageButton vp;
    private ImageButton vr;
    private Bundle bundle;
    private int level_num;
    private int next_level;
    private int role_num;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private String time;
    private String str1;
    private int stopPosition;
    private int  currentPosition;
    private static boolean isExit = false;
    private int star_judge=0;
    private String a="1:00:00";
    private String b="1:00:00";
    private String c="1:00:00";
    private String d="1:00:00";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.activity_playing);
        init();
        MediaController mediaController = new MediaController(this);
        videoview.setMediaController(mediaController);
        videoview.start();
        handler.post(run);
        skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(PlayingActivity.this, ScoreActivity.class);
                bundle.putInt("level_num", level_num);
                intent.putExtras(bundle);
                SysApplication.getInstance().addActivity(PlayingActivity.this);
                PlayingActivity.this.startActivity(intent);
            }
        });
        vr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btnVoice();
            }
        });
        vp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mTts.startSpeaking(str1, mTtsListener);
            }
        });
    }
    private void init() {
        videoview = (VideoView) findViewById(videoView);
        skip=(ImageButton)findViewById(R.id.skip);
        vp=(ImageButton)findViewById(R.id.voice_play);
        vr=(ImageButton)findViewById(R.id.voice_recorder);
        bundle=this.getIntent().getExtras();
        level_num = bundle.getInt("level_num");
        role_num = bundle.getInt("role_num");
        videoview.setVideoURI(Uri.parse("/sdcard/peppapig/video/video"+level_num+".avi"));
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=586da44a");
        mTts = SpeechSynthesizer.createSynthesizer(this, null);
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
            str1=str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        set_mTts();
    }
    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public void timeToPlay(String str){
        vp .setVisibility(View.INVISIBLE);
        vr.setVisibility(View.INVISIBLE);
        GameTime();
        if(str.equals(a))
        {
            onPause();
            vp.setVisibility(View.VISIBLE);
            vr.setVisibility(View.VISIBLE);
        }
        if(str.equals(b))
        {
                onPause();
                vp.setVisibility(View.VISIBLE);
                vr.setVisibility(View.VISIBLE);
        }
        if(str.equals(c))
        {
                onPause();
                vp.setVisibility(View.VISIBLE);
                vr.setVisibility(View.VISIBLE);
        }
        if(str.equals(d))
        {
                onPause();
                vp.setVisibility(View.VISIBLE);
                vr.setVisibility(View.VISIBLE);
        }
    }

    private Runnable run =  new Runnable() {
        public void run() {
            currentPosition = videoview.getCurrentPosition();
            time=stringForTime(videoview.getCurrentPosition());
            timeToPlay(time);
            handler.postDelayed(run, 1000);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        stopPosition = videoview.getCurrentPosition(); //stopPosition is an int
        videoview.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        videoview.seekTo(stopPosition);
        videoview.start(); //Or use resume() if it doesn't work. I'm not sure
    }

    public void onClick(View v) {
        btnVoice();
    }
    //TODO 开始说话：
    public void btnVoice() {
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

    public void GameTime() {
        switch (role_num) {
            case 1:
                a="00:30";
                b="01:00";
                break;
            case 2:
                a="01:30";
                b="02:00";
                break;
            case 3:
                a="02:30";
                b="03:00";
                break;
            case 4:
                a="03:30";
                b="04:00";
                break;
        }
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
            Toast.makeText(getApplicationContext(), "再按一次回到选关界面",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else{
            Intent intent = new Intent();
            intent.setClass(PlayingActivity.this, LevelActivity.class);
            Bundle bundle1=new Bundle();
            bundle1.putInt("star_judge", star_judge);
            intent.putExtras(bundle1);
            SysApplication.getInstance().addActivity(PlayingActivity.this);
            PlayingActivity.this.startActivity(intent);
        }
    }
    @Override
    protected void onDestroy() {
        mTts.stopSpeaking();
        mTts.destroy();// 退出时释放连接
        super.onDestroy();
    }
}

