package com.yyrz.patient.UI.assessmentUI;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.yyrz.patient.R;
import com.yyrz.patient.common.viewModel.CommonViewModel;
import java.util.Timer;
import java.util.TimerTask;

public class MemoryAssessment extends Fragment {
    private TextView[] textViews;
    private TableLayout tableLayout;

    //    private  UtteranceProgressListener listener;
    private SynthesizerListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.memory_assessment, container, false);
        textViews=new TextView[6];
        textViews[0]=root.findViewById(R.id.memory_textView1);
        textViews[1]=root.findViewById(R.id.memory_textView2);
        textViews[2]=root.findViewById(R.id.memory_textView3);
        textViews[3]=root.findViewById(R.id.memory_textView4);
        textViews[4]=root.findViewById(R.id.memory_textView5);
        textViews[5]=root.findViewById(R.id.memory_textView6);
        tableLayout=root.findViewById(R.id.memory_tablelayout);
        tableLayout.setVisibility(View.GONE);
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
                    if(!instructions.substring(0,2).equals("04")) return;
                    switch (instructions.substring(3)){
                        case "speech_1":performFirst();break;
                        case "speech_2":performSecond();break;
//                        case "speech_3":performThird();break;
                    }
                }
            }
        });
    }

    private void performFirst(){
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().setValue(0);
        textViews[0].setVisibility(View.VISIBLE);
        textViews[0].setText(R.string.memory);
//
//        listener=new UtteranceProgressListener() {
//            @Override
//            public void onStart(String utteranceId) {
//            }
//
//            @Override
//            public void onDone(String utteranceId) {
//                Timer timer=new Timer();
//                TimerTask timerTask=new TimerTask() {
//                    @Override
//                    public void run() {
//                        CommonViewModel.getInstance(null,null).getIsSpeechFinished().postValue(CommonViewModel.getInstance(null,null).getIsSpeechFinished().getValue()+1);
//                    }
//                };
//                timer.schedule(timerTask,1000);
//            }
//
//            @Override
//            public void onError(String utteranceId) {
//
//            }
//        };
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
        CommonViewModel.getInstance(null,null).getIsSpeechFinished().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer.equals(1)) {
                    textViews[0].setVisibility(View.GONE);
                    tableLayout.setVisibility(View.VISIBLE);
                    for(int i=0;i<=5;i++) textViews[i].setVisibility(View.INVISIBLE);
                    textViews[1].setVisibility(View.VISIBLE);
//                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(label,0.4f,listener,"memory2");
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.memory_tips1),listener,"20");
                }
                else if(integer.equals(2)){
                    textViews[1].setVisibility(View.INVISIBLE);
                    textViews[2].setVisibility(View.VISIBLE);
//                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(label,0.4f,listener,"memory3");
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.memory_tips2),listener,"20");
                }
                else if(integer.equals(3)){
                    textViews[2].setVisibility(View.INVISIBLE);
                    textViews[3].setVisibility(View.VISIBLE);
//                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(label,0.4f,listener,"memory4");
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.memory_tips3),listener,"10");
                }
                else if(integer.equals(4)){
                    textViews[3].setVisibility(View.INVISIBLE);
                    textViews[4].setVisibility(View.VISIBLE);
//                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(label,0.4f,listener,"memory5");
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.memory_tips4),listener,"10");
                }
                else if(integer.equals(5)){
                    textViews[4].setVisibility(View.INVISIBLE);
                    textViews[5].setVisibility(View.VISIBLE);
//                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(label,0.4f,listener,"memory6");
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.memory_tips5),listener,"10");
                }
                else if (integer.equals(6)) {
                    for(int i=1;i<=5;i++) textViews[i].setVisibility(View.INVISIBLE);
                    textViews[0].setVisibility(View.VISIBLE);
                    textViews[0].setText(R.string.memory_tips8);
                    CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.memory_tips8),null);
                    CommonViewModel.getInstance(null,null).getIsSpeechFinished().setValue(0);
                }
                else return;
            }
        });
//        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(language,listener,"memory1");
        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.memory),listener);
    }

    private void performSecond(){
        textViews[0].setVisibility(View.VISIBLE);
        textViews[0].setText(R.string.memory_tips6);
//        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(string,listener,"memory7");
        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.memory_tips6),listener);
    }

//    private void performThird(){
//        textViews[0].setVisibility(View.VISIBLE);
//        textViews[0].setText(R.string.memory_tips7);
//        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.memory_tips7),null,null);
//    }

//    @Override
//    public void onDestroyView() {
//        performThird();
//        while (CommonViewModel.getInstance(null,null).getMyTextToSpeech().isSpeaking()){}
//        super.onDestroyView();
//    }
}
