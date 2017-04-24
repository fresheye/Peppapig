package com.example.ilzxm.peppapig;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import utils.NetUtil;
import utils.SysApplication;


public class LoginSecondActivity extends AppCompatActivity implements View.OnClickListener {
	private EditText mname,mpassword;
	private Button mlogin,mregi;
	private SharedPreferences sp;
	private CheckBox rem_pwd;
	private int star_judge=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
		setContentView(R.layout.activity_login);
		Bmob.initialize(this, "8f8cee2e375eb19fb43014310fbf7fa2");
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        mname=(EditText) findViewById(R.id.username);
		mpassword=(EditText) findViewById(R.id.passwd);
		mlogin=(Button) findViewById(R.id.login);
		mregi=(Button) findViewById(R.id.register);
        rem_pwd=(CheckBox)findViewById(R.id.save_password);
        mlogin.setOnClickListener(this);
        mregi.setOnClickListener(this);
		rem_pwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (rem_pwd.isChecked()) {
					sp.edit().putBoolean("ISCHECK", true).commit();
				}else {
					sp.edit().putBoolean("ISCHECK", false).commit();
				}

			}
		});
		if(sp.getBoolean("ISCHECK", false))
		{
			//设置默认是记录密码状态
			rem_pwd.setChecked(true);
			mname.setText(sp.getString("USER_NAME", ""));
			mpassword.setText(sp.getString("PASSWORD", ""));
		}
	}
    @Override
    public void onClick(View v) {
        if (v == mregi) {
            Intent intent = new Intent(LoginSecondActivity.this, RegisterSecongActivity.class);
            SysApplication.getInstance().addActivity(LoginSecondActivity.this);
            startActivity(intent);
        } else {
            boolean isNetConnected = NetUtil.isNetworkAvailable(this);
            if(!isNetConnected){
                Toast.makeText(LoginSecondActivity.this, "请检查网络连接", Toast.LENGTH_LONG).show();
                return;
            }
            mlogin();
        }
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
				if(gname.equals(name)&&gpassword.equals(password))
				{
                    if(rem_pwd.isChecked())
                {
                    //记住用户名、密码、
                    Editor editor = sp.edit();
                    editor.putString("USER_NAME", name);
                    editor.putString("PASSWORD",password);
                    editor.commit();
                }
					Intent seccess = new Intent();
					seccess.setClass(LoginSecondActivity.this, LevelActivity.class);
                    Bundle bundle1=new Bundle();
					bundle1.putInt("star_judge", star_judge);
					seccess.putExtras(bundle1);
					SysApplication.getInstance().addActivity(LoginSecondActivity.this);
					startActivity(seccess);
                    Toast.makeText(LoginSecondActivity.this, "欢迎", Toast.LENGTH_LONG).show();
				}
				}
                else{
                    Toast.makeText(LoginSecondActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
                }
			}
		});


	}


}
