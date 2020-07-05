package com.yyrz.patient.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;

public class Tts extends Activity {
	// 语音合成对象
	private SpeechSynthesizer speechSynthesizer;
	public static Tts instance;
	private  String voicerXtts="xiaoyan";
	private SharedPreferences mSharedPreferences;
	private  Tts(){}

	public static Tts getInstance(Context context) {
		if(instance==null){
			instance=new Tts();
			instance.init(context);
		}
		return instance;
	};

	private void init(Context context) {
		InitListener mTtsInitListener = new InitListener() {
				@Override
				public void onInit(int code) {
					Log.d("Tts初始化码", "InitListener init() code = " + code);
					if (code != ErrorCode.SUCCESS) {
						Log.d("初始化失败","错误码："+code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
					}
				}
			};
		mSharedPreferences = context.getSharedPreferences("com.yyrz.tts.setting", Activity.MODE_PRIVATE);
		instance.speechSynthesizer =SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);
		instance.speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_XTTS);
		//设置发音人资源路径
		instance.speechSynthesizer.setParameter(ResourceUtil.TTS_RES_PATH,getResourcePath(context));
		//设置发音人
		instance.speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME,voicerXtts);
		speechSynthesizer.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
		//设置合成音调
		speechSynthesizer.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
		//设置合成音量
		speechSynthesizer.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));
		//设置播放器音频流类型
		speechSynthesizer.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
		// 设置播放合成音频打断音乐播放，默认为true
		speechSynthesizer.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		speechSynthesizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
		speechSynthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
	}
	
	//获取发音人资源路径
	private String getResourcePath(Context context){
		StringBuffer tempBuffer = new StringBuffer();
		String type= "tts";
		//合成通用资源
		tempBuffer.append(ResourceUtil.generateResourcePath(context, ResourceUtil.RESOURCE_TYPE.assets, type+"/common.jet"));
		tempBuffer.append(";");
		//发音人资源
		tempBuffer.append(ResourceUtil.generateResourcePath(context, ResourceUtil.RESOURCE_TYPE.assets, type+"/"+ this.voicerXtts+".jet"));
		return tempBuffer.toString();
	}

	public void speech(String language,SynthesizerListener listener){
		instance.speechSynthesizer.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
		instance.speechSynthesizer.startSpeaking(language,listener);
	}

	public void speech(String language,SynthesizerListener listener,String speed){
		instance.speechSynthesizer.setParameter(SpeechConstant.SPEED,speed);
		instance.speechSynthesizer.startSpeaking(language,listener);
	}

	public boolean isSpeaking(){
		return instance.speechSynthesizer.isSpeaking();
	}
}
