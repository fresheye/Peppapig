package com.example.ilzxm.peppapig;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by ilzxm on 2017/3/23.
 */

public class PlayerActivity extends AppCompatActivity {
    private VideoView videoview;
    private ImageButton skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.activity_player);
        videoview = (VideoView) findViewById(R.id.videoView);
        videoview.setVideoURI(Uri.parse("/sdcard/video.avi"));
        MediaController mediaController = new MediaController(this);
        videoview.setMediaController(mediaController);
        videoview.start();
        skip=(ImageButton)findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(PlayerActivity.this, ChooseRoleActivity.class);
                PlayerActivity.this.startActivity(intent);
            }
        });
    }
}
