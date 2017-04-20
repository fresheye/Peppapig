package com.example.ilzxm.peppapig;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import utils.SysApplication;

public class RegisterSecongActivity extends Activity {
	private EditText mname,mpassword,mnumber,mverify;
	private Button mgetsms,mgi,mreturn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
		setContentView(R.layout.activity_register);
		//Bmob.initialize(this,"614b7327837cd173e62ebd23c50ec16b");
		Bmob.initialize(this, "8f8cee2e375eb19fb43014310fbf7fa2");
		BmobSMS.initialize(this, "8f8cee2e375eb19fb43014310fbf7fa2");
		mname=(EditText)findViewById(R.id.rusername);
		mpassword=(EditText)findViewById(R.id.rpasswd1);
		mnumber=(EditText)findViewById(R.id.tel);
		mverify=(EditText)findViewById(R.id.sms);
		mgetsms=(Button)findViewById(R.id.getsms);
		mgi=(Button)findViewById(R.id.register1);
		mreturn=(Button)findViewById(R.id.rlogin);
		mreturn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				ret();
			}
		});
		mgi.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				zhuce();
			}
		});
	}
	
	//点击获取验证码
	public void gsms(View view){
		String number=mnumber.getText().toString();
		if(number.length()==0)
		{
			Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if(number.length()!=11)
		{
			Toast.makeText(this, "请输入11位有效号码", Toast.LENGTH_LONG).show();
			return;
		}
		BmobSMS.requestSMSCode(this, number, "短信模板", new RequestSMSCodeListener() {
			
			@Override
			public void done(Integer integer, cn.bmob.sms.exception.BmobException e) {
				// TODO Auto-generated method stub
				 if (e == null) {  
                     //发送成功时，让获取验证码按钮不可点击，且为灰色  
                     mgetsms.setClickable(false);  
                     //mgetsms.setBackgroundColor();  
                     Toast.makeText(RegisterSecongActivity.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();
                     /** 
                      * 倒计时1分钟操作 
                      * 说明： 
                      * new CountDownTimer(60000, 1000),第一个参数为倒计时总时间，第二个参数为倒计时的间隔时间 
                      * 单位都为ms，其中必须要实现onTick()和onFinish()两个方法，onTick()方法为当倒计时在进行中时， 
                      * 所做的操作，它的参数millisUntilFinished为距离倒计时结束时的时间，以此题为例，总倒计时长 
                      * 为60000ms,倒计时的间隔时间为1000ms，然后59000ms、58000ms、57000ms...该方法的参数 
                      * millisUntilFinished就等于这些每秒变化的数，然后除以1000，把单位变成秒，显示在textView 
                      * 或Button上，则实现倒计时的效果，onFinish()方法为倒计时结束时要做的操作，此题可以很好的 
                      * 说明该方法的用法，最后要注意的是当new CountDownTimer(60000, 1000)之后，一定要调用start() 
                      * 方法把该倒计时操作启动起来，不调用start()方法的话，是不会进行倒计时操作的 
                      */  
                     new CountDownTimer(60000, 1000) {  
                         @Override  
                         public void onTick(long millisUntilFinished) {  
                             //Message_btn.setBackgroundResource(R.drawable.button_shape02);  
                        	 mgetsms.setText(millisUntilFinished / 1000 + "秒");  
                         }  

                         @Override  
                         public void onFinish() {  
                        	 mgetsms.setClickable(true);  
                             //Message_btn.setBackgroundResource(R.drawable.button_shape);  
                        	 mgetsms.setText("重新发送");  
                         }  
                     }.start();   
                 }  
                 else {  
                     Toast.makeText(RegisterSecongActivity.this, "验证码发送失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                 }  
				
				
			}
		});
		
	}
	//返回到登录页面
	public void ret(){
	 
		Intent intent1 = new Intent();
		intent1.setClass(RegisterSecongActivity.this, LoginSecondActivity.class);
		SysApplication.getInstance().addActivity(RegisterSecongActivity.this);
		RegisterSecongActivity.this.startActivity(intent1);
		
		
	}
	
	//点击注册
	public void zhuce(){
	
		String name=mname.getText().toString();
		String password=mpassword.getText().toString();
		String number=mnumber.getText().toString();
		String verify=mverify.getText().toString();
		if(name.equals("")||password.equals(""))
		{
			Toast.makeText(this, "帐号或密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if(name.length()<6)
		{
			Toast.makeText(this, "帐号小于6位", Toast.LENGTH_LONG).show();
			return;
		}
		if(verify.length()==0)
		{
			Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if(number.length()==0)
		{
			Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if(number.length()!=11)
		{
			Toast.makeText(this, "请输入11位有效号码", Toast.LENGTH_LONG).show();
			return;
		}
		BmobSMS.verifySmsCode(this, number, verify, new VerifySMSCodeListener() {
			
			@Override
			public void done(cn.bmob.sms.exception.BmobException e) {
				// TODO Auto-generated method stub
				if (e == null) {  
                    Toast.makeText(RegisterSecongActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                  //验证码正确 添加用户信息
                    String name=mname.getText().toString();
            		String password=mpassword.getText().toString();
            		user User=new user();
            		User.setName(name);
            		User.setPassword(password);
            		User.save(new SaveListener<String>() {

            			@Override
            			public void done(String arg0, BmobException arg1) {
            				// TODO Auto-generated method stub
            				if(arg1==null){
            					return;
            					
            				}else{
            					return;
            				}
            			}
            			
            		});
            		
            		Intent intent2 = new Intent();
            		intent2.setClass(RegisterSecongActivity.this, LevelActivity.class);
					SysApplication.getInstance().addActivity(RegisterSecongActivity.this);
            		RegisterSecongActivity.this.startActivity(intent2);
                }  
                else {   
                    Toast.makeText(RegisterSecongActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }  
			}
		});
		
		
		
	}
	

}
