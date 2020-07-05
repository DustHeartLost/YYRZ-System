package com.yyrz.patient.UI.assessmentUI;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.yyrz.patient.R;
import com.yyrz.patient.common.viewModel.CommonViewModel;
import java.util.Timer;
import java.util.TimerTask;

public class Attention extends Fragment {
    private TextView textView;
    private SynthesizerListener listener;
    private int number;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.attention, container, false);
        textView=root.findViewById(R.id.attention_textView);
        listener=new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {

            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {
                Timer timer=new Timer();
                TimerTask timerTask=new TimerTask() {
                    @Override
                    public void run() {
                        CommonViewModel.getInstance(null,null).getIsSpeechFinished().postValue(CommonViewModel.getInstance(null,null).getIsSpeechFinished().getValue()+1);
                    }
                };
                timer.schedule(timerTask,1000);
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance(null,null).getAssessmentInstructions().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String instructions=CommonViewModel.getInstance(null,null).getAssessmentInstructions().getValue();
                if(s!=null) {
                    if(!instructions.substring(0,2).equals("05")) return;
                    switch (instructions.substring(3)){
                        case "speech_1":performFirst();break;
                        case "speech_2":performSecond();break;
                        case "speech_3":performThird();break;
                        case "speech_4":performForth();break;
                    }
                }
            }
        });
    }

    private void performFirst(){
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().removeObservers(getViewLifecycleOwner());
        textView.setTextSize(36);
        textView.setText(R.string.attention_tips1);
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().setValue(0);
        String language=getString(R.string.attention_tips1);
        Observer observer= new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                String language=getString(R.string.attention_tips2);
                if(0<integer&&integer<(language.length()+1)){
                    String temp=language.substring(integer-1,integer);
                    textView.setTextSize(100);
                    textView.setText(temp);
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(temp,listener,"20");
                    return;
                }if(integer.equals(language.length()+1)){
                    String temp=getString(R.string.attention_tips3);
                    textView.setTextSize(36);
                    textView.setText(R.string.attention_tips3);
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(temp,null);
                }
            }
        };
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().observe(getViewLifecycleOwner(),observer);
        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(language,listener);
    }

    private void performSecond(){
        textView.setTextSize(36);
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().removeObservers(getViewLifecycleOwner());
        textView.setText(R.string.attention_tips4);
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().setValue(0);
        Observer observer=new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                String language=getString(R.string.attention_tips5);
                if(0<integer&&integer<(language.length()+1)){
                    String temp=language.substring(integer-1,integer);
                    textView.setTextSize(100);
                    textView.setText(temp);
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(temp,listener,"20");
                    return;
                }if(integer.equals(language.length()+1)){
                    String temp=getString(R.string.attention_tips6);
                    textView.setTextSize(36);
                    textView.setText(R.string.attention_tips6);
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(temp,null);
                }
            }
        };
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().observe(getViewLifecycleOwner(),observer);
        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.attention_tips4),listener);
    }

    private void performThird(){
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().removeObservers(getViewLifecycleOwner());
        textView.setTextSize(36);
        textView.setText(R.string.attention_tips7);
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().setValue(0);
        Observer observer=new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                String language=getString(R.string.attention_tips8);
                if(0<integer&&integer<(language.length()+1)){
                    String temp=language.substring(integer-1,integer);
                    textView.setTextSize(100);
                    textView.setText(temp);
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(temp,listener,"20");
                    return;
                }if(integer.equals(language.length()+1)){
                    String temp=getString(R.string.attention_tips8);
                    textView.setTextSize(36);
                    textView.setText(R.string.attention_tips8);
                }
            }
        };
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().observe(getViewLifecycleOwner(),observer);
        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.attention_tips7),listener);
    }

    private void performForth(){
        this.number=100;
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().removeObservers(getViewLifecycleOwner());
        textView.setTextSize(36);
        textView.setText(R.string.attention_tips9);
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().setValue(0);
        Observer observer=new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(0<integer&&integer<6){
                    String temp=number+"减7等于几？";
                    textView.setTextSize(100);
                    textView.setText(number+"- 7 = ??");
                    number-=7;
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(temp,listener,"30");
                    return;
                }if(integer.equals(6)){
                    String temp="正确答案为："+getString(R.string.attention_tips10);
                    textView.setTextSize(36);
                    textView.setText(temp);
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(temp,listener);
                }
            }
        };
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().observe(getViewLifecycleOwner(),observer);
        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.attention_tips9),listener);

    }
}
