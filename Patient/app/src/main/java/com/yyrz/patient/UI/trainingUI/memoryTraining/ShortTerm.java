package com.yyrz.patient.UI.trainingUI.memoryTraining;


import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.yyrz.patient.R;
import com.yyrz.patient.common.viewModel.CommonViewModel;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;


public class ShortTerm extends Fragment {
    private TextView textView0;
    private ImageView imageView0;
    private TextView[]textViews;
    private ImageView[]imageViews;
    private TableLayout tableLayout;
    private SynthesizerListener listener1;
    private SynthesizerListener listener2;
    private SynthesizerListener listener3;
    private int[]imageSet;
    private String[]tipsSet;
    private final int count=5;
    private int[] math;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.short_term, container, false);
        textViews=new TextView[5];
        imageViews=new ImageView[5];
        textView0=root.findViewById(R.id.textView0);
        imageView0=root.findViewById(R.id.imageView0);
        textViews=new TextView[5];
        imageViews=new ImageView[5];
        tableLayout=root.findViewById(R.id.tablelayout);
        textViews[0]=root.findViewById(R.id.textView1);
        textViews[1]=root.findViewById(R.id.textView2);
        textViews[2]=root.findViewById(R.id.textView3);
        textViews[3]=root.findViewById(R.id.textView4);
        textViews[4]=root.findViewById(R.id.textView5);
        imageViews[0]=root.findViewById(R.id.imageView1);
        imageViews[1]=root.findViewById(R.id.imageView2);
        imageViews[2]=root.findViewById(R.id.imageView3);
        imageViews[3]=root.findViewById(R.id.imageView4);
        imageViews[4]=root.findViewById(R.id.imageView5);
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
        math=new int[5];
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
                    if(!s.substring(0,2).equals("02")) return;
                    switch (s.substring(3)){
                        case "speech_1":break;
                        case "speech_2":
                            createDataSet(R.array.life);
                            tipsSet=getResources().getStringArray(R.array.life_tips);break;
                        case "speech_3":
                            createDataSet(R.array.scenery);
                            tipsSet=getResources().getStringArray(R.array.scenery_tips);break;
                        case "speech_4":
                            createDataSet(R.array.animal);
                            tipsSet=getResources().getStringArray(R.array.animal_tips);break;
                    }
                }
                CommonViewModel.getInstance(null,null).getIsSpeechFinished().setValue(0);
            }
        });
        perform();
    }

    private void createMath(){
        int start=0;
        int end=imageSet.length-1;
        HashSet<Integer>set=new HashSet<>();
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
                    tableLayout.setVisibility(View.GONE);
                    textView0.setVisibility(View.VISIBLE);
                    imageView0.setVisibility(View.INVISIBLE);
                    textView0.setText(R.string.training_short_memory1);
                    commonViewModel.getMyTextToSpeech().speech(getString(R.string.training_short_memory1), listener1);
                }else if(integer>0&integer<=count){
                    imageView0.setVisibility(View.VISIBLE);
                    textView0.setText(tipsSet[math[integer-1]]);
                    imageView0.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_in));
                    imageView0.setImageResource(imageSet[math[integer-1]]);
                    commonViewModel.getMyTextToSpeech().speech(tipsSet[math[integer-1]], listener1);
                } else if(integer.equals(count+1)){
                    imageView0.setVisibility(View.INVISIBLE);
                    textView0.setText(R.string.training_short_memory2);
                    commonViewModel.getMyTextToSpeech().speech(getString(R.string.training_short_memory2),listener2);
                } else if(integer.equals(count+2)){
                    tableLayout.setVisibility(View.VISIBLE);
                    textView0.setVisibility(View.GONE);
                    imageView0.setVisibility(View.GONE);
                    for(int i=0;i<count;i++){
                        textViews[i].setText(tipsSet[math[i]]);
                        imageViews[i].setImageResource(imageSet[math[i]]);
                    }
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append("正确答案是：");
                    for(int i=0;i<count;i++){
                        stringBuilder.append(tipsSet[math[i]]);
                        stringBuilder.append(" ");
                    }
                    commonViewModel.getMyTextToSpeech().speech(stringBuilder.toString(), listener3);
                    createMath();
                }
            }
        });
    }

    private void createDataSet(int res){
        TypedArray ar = getContext().getResources().obtainTypedArray(res);
        int len = ar.length();
        imageSet= new int[len];
        for (int i = 0; i < len; i++)
            imageSet[i] = ar.getResourceId(i, 0);
        ar.recycle();
    }
}
