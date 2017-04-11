package utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.ilzxm.peppapig.R;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

public class VoicesActivity extends Activity {

	private SpeechSynthesizer mTts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏

		setContentView(R.layout.activity_recording);
		SpeechUtility.createUtility(this, SpeechConstant.APPID + "586da44a");
		mTts = SpeechSynthesizer.createSynthesizer(this, null);
		set_mTts();
		ImageButton btn = (ImageButton) findViewById(R.id.voiceBtn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mTts.startSpeaking(
						"7月14日，2014年英国范堡罗航展正式开幕。瑞典萨伯公司研制的最新型“鹰狮NG”战斗机首次亮相该航展。该机在Jas-39“鹰狮”基础上进行全面改进，更换了最新型的AESA雷达，并配备有“流星”中程空空导弹、IRIS-T近距格斗导弹、KEPD-350远程防区外空地导弹等最新一代机载武器，战斗力得到空前飞跃。",
						mTtsListener);

			}
		});

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

	@Override
	protected void onDestroy() {
		mTts.stopSpeaking();
		mTts.destroy();// 退出时释放连接
		super.onDestroy();
	}
}
