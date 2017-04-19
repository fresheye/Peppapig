package com.example.ilzxm.peppapig;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Formatter;
import java.util.Locale;

import static com.example.ilzxm.peppapig.R.id.videoView;


/**
 * Created by ilzxm on 2017/3/23.
 */

public class PlayingActivity extends AppCompatActivity {
    private VideoView videoview;
    private Handler handler = new Handler();
    private ImageButton skip;
    private ImageButton vp;
    private ImageButton vr;
    private TextView ttest;
    private Bundle bundle;
    private int level_num1;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    String time;
    int stopPosition;
    int  currentPosition;
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
        test(currentPosition);
        time=stringForTime(videoview.getCurrentPosition());
        skip=(ImageButton)findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(PlayingActivity.this, RecordingActivity.class);
                PlayingActivity.this.startActivity(intent);
            }
        });

    }
    private void init() {
        videoview = (VideoView) findViewById(videoView);
        vp=(ImageButton)findViewById(R.id.voice_play);
        vr=(ImageButton)findViewById(R.id.voice_recorder);
        ttest=(TextView)findViewById(R.id.test);
        bundle=this.getIntent().getExtras();
        level_num1=1;
        videoview.setVideoURI(Uri.parse("/sdcard/peppapig/video/video"+level_num1+".avi"));
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
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
    private void timeToPlay(String str){
        switch (str){
            case "01:00":
                videoview.pause();
                vp.setVisibility(View.VISIBLE);
                vr.setVisibility(View.VISIBLE);
                break;
            case "02:00":
                videoview.pause();
                vp.setVisibility(View.VISIBLE);
                vr.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void test(int i) {
            ttest.setText("" + i);
    }

    private Runnable run =  new Runnable() {
        public void run() {
            currentPosition = videoview.getCurrentPosition();
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
}

