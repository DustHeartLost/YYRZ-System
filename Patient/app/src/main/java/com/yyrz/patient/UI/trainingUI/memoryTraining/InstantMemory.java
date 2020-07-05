package com.yyrz.patient.UI.trainingUI.memoryTraining;

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
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class InstantMemory extends Fragment {

    private TextView textView;
    private int[]math;
    private SynthesizerListener listener1;
    private SynthesizerListener listener2;
    private SynthesizerListener listener3;
    private int size;
    private int number;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.instant_memory, container, false);
        textView=root.findViewById(R.id.instant_memory_textView);
        math=new int[5];
        listener1 =new SynthesizerListener() {
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
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        CommonViewModel.getInstance(null, null).getIsSpeechFinished().postValue(CommonViewModel.getInstance(null, null).getIsSpeechFinished().getValue() + 1);
                    }
                };
                timer.schedule(timerTask, 1000);
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };
        listener2 =new SynthesizerListener() {
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
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        CommonViewModel.getInstance(null, null).getIsSpeechFinished().postValue(CommonViewModel.getInstance(null, null).getIsSpeechFinished().getValue() + 1);
                    }
                };
                timer.schedule(timerTask, 8000);
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };
        listener3 =new SynthesizerListener() {
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
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        CommonViewModel.getInstance(null,null).getIsSpeechFinished().postValue(0);
                    }
                };
                timer.schedule(timerTask, 5000);
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().setValue(-1);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().setValue(-1);
        CommonViewModel.getInstance(null,null).getTrainingInstructions().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null) {
                    if(!s.substring(0,2).equals("01")) return;
                    switch (s.substring(3)){
                        case "speech_1":number=2;break;
                        case "speech_2":number=3;break;
                        case "speech_3":number=4;break;
                    }
                }
                CommonViewModel.getInstance(null,null).getIsSpeechFinished().setValue(0);
            }
        });
        perform();
    }

    private void createMath(){
        int start=(int)Math.pow(10,number-1);
        int end=(int)Math.pow(10,number);
        size=7-number;
        HashSet<Integer> set=new HashSet<>();
        while(set.size()!=5){
            int num=start+(int)(Math.random()*(end-start));
            if(!set.contains(num)){
                set.add(num);
            }
        }
        int j=0;
        for (int i:set) {
            math[j]=i;
            j++;
        }
    }

    private void perform(){
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                CommonViewModel commonViewModel=CommonViewModel.getInstance(null,null);
                if(integer.equals(0)){
                    createMath();
                    textView.setTextSize(36);
                    textView.setText(R.string.training_instant_memory1);
                    commonViewModel.getMyTextToSpeech().speech(getString(R.string.training_instant_memory1), listener1);
                }else if(integer>0&integer<=size){
                    textView.setTextSize(100);
                    textView.setText(String.valueOf(math[integer-1]));
                    commonViewModel.getMyTextToSpeech().speech(String.valueOf(math[integer-1]), listener1);
                } else if(integer.equals(size+1)){
                    textView.setTextSize(36);
                    textView.setText(R.string.training_instant_memory2);
                    commonViewModel.getMyTextToSpeech().speech(getString(R.string.training_instant_memory2),listener2);
                } else if(integer.equals(size+2)){
                    StringBuilder temp=new StringBuilder(20);
                    for (int i=0;i<size;i++){
                        temp.append(math[i]);
                        temp.append(" ");
                    }
                    textView.setTextSize(36);
                    String data="正确答案为："+temp;
                    textView.setText(data);
                    commonViewModel.getMyTextToSpeech().speech(data, listener3);
                    createMath();
                }
            }
        });
    }
}
