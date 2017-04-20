package com.example.ilzxm.peppapig;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import utils.SysApplication;


public class LoginSecondActivity extends Activity{
	private EditText mname,mpassword;
	private Button mlogin,mregi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
		setContentView(R.layout.activity_login);
		Bmob.initialize(this, "8f8cee2e375eb19fb43014310fbf7fa2");
		mname=(EditText) findViewById(R.id.username);
		mpassword=(EditText) findViewById(R.id.passwd);
		mlogin=(Button) findViewById(R.id.login);
		mregi=(Button) findViewById(R.id.register);
		mregi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(LoginSecondActivity.this, RegisterSecongActivity.class);
				SysApplication.getInstance().addActivity(LoginSecondActivity.this);
				startActivity(intent);
			}
		});
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlogin();
            }
        });
	}

	//登录点击
	public void mlogin(){
		String name=mname.getText().toString();
		String password=mpassword.getText().toString();
		if(name.equals("")||password.equals("")){
			Toast.makeText(this, "帐号或密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		BmobQuery<user> query=new BmobQuery<user>();
		query.addWhereEqualTo("name", name);
		query.addWhereEqualTo("password", password);
		query.findObjects(new FindListener<user>() {

			@Override
			public void done(List<user> arg0, BmobException e) {
				// TODO Auto-generated method stub
				if(e==null){
				String gname=arg0.get(0).getName().toString();
				String gpassword=arg0.get(0).getPassword().toString();
				String name=mname.getText().toString();
				String password=mpassword.getText().toString();
				Toast.makeText(LoginSecondActivity.this, "欢迎", Toast.LENGTH_LONG).show();
				if(gname.equals(name)&&gpassword.equals(password))
				{
					Intent seccess = new Intent();
					seccess.setClass(LoginSecondActivity.this, LevelActivity.class);
                    SysApplication.getInstance().addActivity(LoginSecondActivity.this);
					startActivity(seccess);
				}

				}
				else{
					Toast.makeText(LoginSecondActivity.this, "帐号或密码有误", Toast.LENGTH_LONG).show();
				}

			}
		});


	}


}
