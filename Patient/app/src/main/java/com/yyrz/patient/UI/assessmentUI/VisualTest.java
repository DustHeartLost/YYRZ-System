package com.yyrz.patient.UI.assessmentUI;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyrz.patient.R;
import com.yyrz.patient.common.viewModel.CommonViewModel;

public class VisualTest extends Fragment {

    private ImageView imageView;
    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.visual_test, container, false);
        imageView=root.findViewById(R.id.visual_imageView);
        textView=root.findViewById(R.id.visual_textView);
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
                    if(!instructions.substring(0,2).equals("02")) return;
                    switch (instructions.substring(3)){
                        case "speech_1":textView.setText(R.string.visualSpace_tips1);
                                        imageView.setVisibility(View. VISIBLE);
                                        imageView.setImageResource(R.drawable.square);
                                        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.visualSpace_tips1),null,null);break;
                        case "speech_2":textView.setText(R.string.visualSpace_tips2);
                                        imageView.setVisibility(View.INVISIBLE);
                                        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.visualSpace_tips2),null,null);break;
                    }
                }
            }
        });
    }
}
