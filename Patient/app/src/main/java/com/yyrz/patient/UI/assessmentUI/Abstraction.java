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

public class Abstraction extends Fragment {
    private TextView textView;
    private ImageView imageView1;
    private ImageView imageView2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.abstraction, container, false);
        textView=root.findViewById(R.id.abstaction_textView);
        imageView1=root.findViewById(R.id.abstaction_imageView1);
        imageView2=root.findViewById(R.id.abstaction_imageView2);
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
                    if(!instructions.substring(0,2).equals("07")) return;
                    switch (instructions.substring(3)){
                        case "speech_1":perform(R.string.abstraction_tips1,R.drawable.orange,R.drawable.banana);break;
                        case "speech_2":textView.setText(R.string.abstraction_tips2);
                                        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.abstraction_tips2),null);break;
                        case "speech_3":textView.setText(R.string.abstraction_tips3);
                                        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(R.string.abstraction_tips3),null);break;
                        case "speech_4":perform(R.string.abstraction_tips4,R.drawable.train,R.drawable.bike);break;
                        case "speech_5":perform(R.string.abstraction_tips5,R.drawable.watch,R.drawable.rule);break;
                    }
                }
            }
        });
    }

    private void perform(int textID,int imageID1,int imageID2){
        textView.setText(textID);
        imageView1.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_in));
        imageView2.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_in));
        imageView1.setImageResource(imageID1);
        imageView2.setImageResource(imageID2);
        CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech(getString(textID),null);
    }
}
