package com.example.ilzxm.peppapig;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;

/**
 * Created by ilzxm on 2017/3/23.
 */

public class RegisterActivity extends AppCompatActivity {
    private Button register1Button;
    private ImageButton cpButton;
    private EditText editText1;
    private EditText editText2;
    private String pwd1;
    private String pwd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.activity_register);
        register1Button=(Button)findViewById(R.id.register1);
        cpButton=(ImageButton)findViewById(R.id.register_picture);
        editText1=(EditText)findViewById(R.id.rpasswd1);
        editText2=(EditText)findViewById(R.id.rpasswd2);
        pwd1=editText1.getText().toString();
        pwd2=editText2.getText().toString();
        register1Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(pwd1.equals(pwd2))
                { Toast toast=Toast.makeText(getApplicationContext(), "注册成功，进入游戏", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, LevelActivity.class);
                    RegisterActivity.this.startActivity(intent);}
               else{
                    Toast toast=Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        cpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                ImageView imageView = (ImageView) findViewById(R.id.register_picture);
                /* 将Bitmap设定到ImageView */
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
