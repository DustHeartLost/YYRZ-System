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

public class FluentLanguage extends Fragment {
    private  TextView textView;
    private SynthesizerListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fluent_language, container, false);
        textView=root.findViewById(R.id.fluentLanguage_textView);
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
                    if(!instructions.substring(0,2).equals("06")) return;
                    switch (instructions.substring(3)){
                        case "speech_1":
                            performFirst(getString(R.string.fluentLanguage_tips1),getString(R.string.fluentLanguage_tips2));break;
                        case "speech_2":performSecond();
                            performFirst(getString(R.string.fluentLanguage_tips3),getString(R.string.fluentLanguage_tips4));break;
                        case "speech_3":performSecond();
                    }
                }
            }
        });
    }

    private void performFirst(final String first,final String second){
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().setValue(0);
        textView.setText(first);
        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(first,listener);
        Observer observer= new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer.equals(1)){
                    textView.setText(second);
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(second,listener);
                }
                if(integer.equals(2)) {
                    textView.setText(getString(R.string.fluentLanguage_tips6));
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.fluentLanguage_tips6),null);
                }
            };
        };
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().observe(getViewLifecycleOwner(),observer);
        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(first,listener);

    }

    private void performSecond(){
        textView.setText(R.string.fluentLanguage_tips5);
        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.fluentLanguage_tips5),null);
    }

}
