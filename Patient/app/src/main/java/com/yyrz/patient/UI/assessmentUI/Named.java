package com.yyrz.patient.UI.assessmentUI;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyrz.patient.R;
import com.yyrz.patient.common.viewModel.CommonViewModel;

public class Named extends Fragment {

    private ImageView imageView;
    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.named, container, false);
        imageView =root.findViewById(R.id.imageView);
        textView=root.findViewById(R.id.name_textView);
        textView=root.findViewById(R.id.name_textView);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance(null,null).getAssessmentInstructions().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String instructions=CommonViewModel.getInstance(null,null).getAssessmentInstructions().getValue();
                String language1=getString(R.string.named_tips);
                if(s!=null) {
                    if(!instructions.substring(0,2).equals("03")) return;
                    switch (instructions.substring(3)){
                        case "speech_1":
                            imageView.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_in));
                            imageView.setImageResource(R.drawable.lion);
                            CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(language1,null,null);break;
                        case "speech_2":
                            imageView.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_in));
                            imageView.setImageResource(R.drawable.rhino);
                             CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(language1,null,null);break;
                        case "speech_3":
                            imageView.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_in));
                            imageView.setImageResource(R.drawable.camel);
                            CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(language1,null,null);break;
                    }
                }
            }
        });
    }
}
