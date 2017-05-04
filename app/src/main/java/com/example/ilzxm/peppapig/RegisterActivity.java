package com.example.ilzxm.peppapig;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import utils.NetUtil;
import utils.SysApplication;

public class RegisterActivity extends Activity {
	private EditText musername,mpassword,mnumber,mverify;
	private Button mgetsms,mgi,mreturn;
	private ImageView head_image;
	private int star_judge=0;
    private String img_url1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
		setContentView(R.layout.activity_register);
		Bmob.initialize(this, "8f8cee2e375eb19fb43014310fbf7fa2");
		musername=(EditText)findViewById(R.id.rusername);
		mpassword=(EditText)findViewById(R.id.rpasswd1);
		mnumber=(EditText)findViewById(R.id.tel);
		mverify=(EditText)findViewById(R.id.sms);
		mgetsms=(Button)findViewById(R.id.getsms);
		mgi=(Button)findViewById(R.id.register1);
		head_image=(ImageView) findViewById(R.id.head_image);
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
		head_image.setOnClickListener(new View.OnClickListener(){
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
            String []imgs={MediaStore.Images.Media.DATA};//将图片URI转换成存储路径
            Cursor cursor1=this.managedQuery(uri, imgs, null, null, null);
            int index1=cursor1.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor1.moveToFirst();
            img_url1=cursor1.getString(index1);
			Log.e("uri", uri.toString());
			ContentResolver cr = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
//                resizeImage(uri);
//                bitmap=showResizeImage();
                bitmap=toRoundBitmap(bitmap);
                ImageView imageView = (ImageView) findViewById(R.id.head_image);
                /* 将Bitmap设定到ImageView */
				imageView.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(),e);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    public Bitmap toRoundBitmap(Bitmap bitmap){
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        float r=0;
        //取最短边做边长
        if(width<height){
            r=width;
        }else{
            r=height;
        }
        //构建一个bitmap
        Bitmap backgroundBm=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas=new Canvas(backgroundBm);
        Paint p=new Paint();
        //设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect=new RectF(0, 0, r, r);
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        //且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r/2, r/2, p);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return backgroundBm;
    }
    public void resizeImage(Uri uri) {//重塑图片大小
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//可以裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }
    private Bitmap showResizeImage(Intent data) {//显示图片
        Bundle extras = data.getExtras();
        Bitmap bitmap;
            bitmap = extras.getParcelable("data");
        return bitmap;
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
		BmobSMS.requestSMSCode(this, number, "模板1", new RequestSMSCodeListener() {
			
			@Override
			public void done(Integer integer, cn.bmob.sms.exception.BmobException e) {
				// TODO Auto-generated method stub
				 if (e == null) {  
                     //发送成功时，让获取验证码按钮不可点击，且为灰色  
                     mgetsms.setClickable(false);  
                     //mgetsms.setBackgroundColor();  
                     Toast.makeText(RegisterActivity.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();
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
                     Toast.makeText(RegisterActivity.this, "验证码发送失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                 }  
				
				
			}
		});
		
	}
	//返回到登录页面
	public void ret(){
	 
		Intent intent1 = new Intent();
		intent1.setClass(RegisterActivity.this, LoginActivity.class);
		SysApplication.getInstance().addActivity(RegisterActivity.this);
		RegisterActivity.this.startActivity(intent1);
		
		
	}
	
	//点击注册
	public void zhuce(){
	
		String username=musername.getText().toString();
		String password=mpassword.getText().toString();
		String number=mnumber.getText().toString();
		String verify=mverify.getText().toString();
		if(username.equals("")||password.equals(""))
		{
			Toast.makeText(this, "帐号或密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if(username.length()<6)
		{
			Toast.makeText(this, "帐号小于6位", Toast.LENGTH_LONG).show();
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
		if(verify.length()==0)
		{
			Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		boolean isNetConnected = NetUtil.isNetworkAvailable(this);
		if(!isNetConnected){
			Toast.makeText(this, "请检查网络连接", Toast.LENGTH_LONG).show();
			return;
		}
		BmobSMS.verifySmsCode(this, number, verify, new VerifySMSCodeListener() {
			
			@Override
			public void done(cn.bmob.sms.exception.BmobException e) {
				// TODO Auto-generated method stub
				if (e == null) {  
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                  //验证码正确 添加用户信息
                    String username=musername.getText().toString();
            		String password=mpassword.getText().toString();
//                    final BmobFile image = new BmobFile(new File(img_url1));
                    Users User=new Users();
            		User.setUsername(username);
            		User.setPassword(password);
//                    Users.setImage(image);
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
            		intent2.setClass(RegisterActivity.this, LevelActivity.class);
					SysApplication.getInstance().addActivity(RegisterActivity.this);
					Bundle bundle=new Bundle();
					bundle.putInt("star_judge", star_judge);
					intent2.putExtras(bundle);
            		RegisterActivity.this.startActivity(intent2);
                }  
                else {   
                    Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }  
			}
		});
		
		
		
	}
	

}
