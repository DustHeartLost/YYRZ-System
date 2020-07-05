package com.yyrz.patient.common.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.widget.Toast;
import java.util.Locale;

public class MyTextToSpeech {
    private static MyTextToSpeech instance;
    private TextToSpeech textToSpeech;
    private Tts mTTs;
    public static MyTextToSpeech getInstance(final Context context){
        if(instance==null){
            instance=new MyTextToSpeech();
            instance.textToSpeech=new TextToSpeech(context,new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = instance.textToSpeech.setLanguage(Locale.CHINA);
                        if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result != TextToSpeech.LANG_AVAILABLE){
                            Toast.makeText(context, "TTS暂时不支持这种语音的朗读！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }});
        }
        instance.textToSpeech.setVoice(new Voice(null, null, Voice.QUALITY_VERY_HIGH, Voice.LATENCY_NORMAL, false, null));
        return instance;
    }

    private MyTextToSpeech(){}

    public void speech(String string ,UtteranceProgressListener listener,String ID){
        textToSpeech.setOnUtteranceProgressListener(listener);
        instance.textToSpeech.setSpeechRate(0.9f);
        instance.textToSpeech.speak(string, TextToSpeech.QUEUE_ADD, null,ID);
    }

    public void speech(String string,float speed,UtteranceProgressListener listener,String ID){
     textToSpeech.setOnUtteranceProgressListener(listener);
        instance.textToSpeech.setSpeechRate(speed);
        instance.textToSpeech.speak(string, TextToSpeech.QUEUE_ADD, null,ID);
    }

}
