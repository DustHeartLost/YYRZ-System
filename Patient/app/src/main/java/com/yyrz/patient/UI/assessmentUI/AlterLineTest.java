package com.yyrz.patient.UI.assessmentUI;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yyrz.patient.R;
import com.yyrz.patient.common.viewModel.CommonViewModel;

public class AlterLineTest extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.alter_line_test, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String language=getString(R.string.alterLineTest_tips1);
        CommonViewModel.getInstance(null,null).getAssessmentInstructions().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String instructions = CommonViewModel.getInstance(null, null).getAssessmentInstructions().getValue();
                if (s != null) {
                    if (!instructions.substring(0, 2).equals("01")) return;
                    switch (instructions.substring(3)) {
                        case "speech_1":
                            CommonViewModel.getInstance(null, null).getMyTextToSpeech().speech(language,null,null);break;
                    }
                }
            }
        });
    }
}
