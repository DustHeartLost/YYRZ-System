package com.yyrz.patient.UI.assessmentUI;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yyrz.patient.R;
import com.yyrz.patient.common.viewModel.CommonViewModel;

public class Direction extends Fragment {
    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.direction, container, false);
        textView=root.findViewById(R.id.direction_textView);
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
                    if(!instructions.substring(0,2).equals("09")) return;
                    switch (instructions.substring(3)){
                        case "speech_1":textView.setText(R.string.direction_tips1);
                                        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.direction_tips1),null);break;
                        case "speech_2":textView.setText(R.string.direction_tips2);
                                        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.direction_tips2),null);break;
                        case "speech_3":textView.setText(R.string.direction_tips3);
                                        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.direction_tips3),null);break;
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        if(CommonViewModel.getInstance(null,null).getMyTextToSpeech().isSpeaking()){}
        super.onDestroyView();
    }
}
