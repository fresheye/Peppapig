package com.example.ilzxm.peppapig;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import utils.HttpPostUrl;
import utils.UlikeShareConst;

public class LoginActivity extends AppCompatActivity {
    private EditText mTextUserName;   //存储输入的用户名
    private EditText mTextPsw;        //存储输入的密码
    private Button mLoginBtn;         //登录按钮
    private String loginResult;
    private Button registerButton;//存储服务端返回的结果
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.activity_login);
        init();
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    void init(){
        //初始化对象
        mTextUserName = (EditText) this.findViewById(R.id.username);
        mTextPsw = (EditText) this.findViewById(R.id.passwd);
        mLoginBtn = (Button) this.findViewById(R.id.login);
        registerButton=(Button) this.findViewById(R.id.register);
        //增加登录按钮点击效果
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTest();
            }
        });
    }
    //登录请求
    void loginTest(){
        //把网络访问的代码放在这里
        //android 中网络访问不能在主线程中进行
        new Thread(){
            @Override
            public void run()
            {
                //获取输入的用户名密码
                String username = mTextUserName.getText().toString();
                String passwd = mTextPsw.getText().toString();
                //将用户名与密码放入hashmap中，对应的key应为服务端
                //接收数据的变量名
                System.out.println(username);
                System.out.println(passwd);
                HashMap<String,String> parameter = new HashMap<String,String>();
                parameter.put("username",username);
                parameter.put("encryptedPassword",passwd);
                //调用HttpPostUrl方法发送http请求，其参数为action请求的url以及hashmap中
                loginResult = HttpPostUrl.sendPost(UlikeShareConst.LOGIN_URL,parameter);
                loginResult="200";
                System.out.println(UlikeShareConst.LOGIN_URL);
                //依据服务端返回的result来处理，如果为200，则跳转到欢迎界面
                if (loginResult.contains("200")){
                    Intent levelActivity = new Intent(LoginActivity.this,LevelActivity.class);
                    levelActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(levelActivity);
                    finish();
                }else if (loginResult.contains("400")){
                    //反之则弹出警告框提示用户名或密码错误
                    new  AlertDialog.Builder(LoginActivity.this)
                            .setTitle("警告" )
                            .setMessage("用户名或密码错误" )
                            .setPositiveButton("确认" ,  null )
                            .show();
                    return;
                }
            }
        }.start();
    }


}
