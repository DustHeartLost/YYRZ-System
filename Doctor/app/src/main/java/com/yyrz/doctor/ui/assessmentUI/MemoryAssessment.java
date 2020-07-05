package com.yyrz.doctor.ui.assessmentUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

public class MemoryAssessment extends Fragment {
    private CheckBox[]checkBoxes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         final View root=inflater.inflate(R.layout.memory_assessment, container, false);
        checkBoxes=new CheckBox[10];
        checkBoxes[0]=root.findViewById(R.id.memory_assessment_checkBox1);
        checkBoxes[1]=root.findViewById(R.id.memory_assessment_checkBox2);
        checkBoxes[2]=root.findViewById(R.id.memory_assessment_checkBox3);
        checkBoxes[3]=root.findViewById(R.id.memory_assessment_checkBox4);
        checkBoxes[4]=root.findViewById(R.id.memory_assessment_checkBox5);
        checkBoxes[5]=root.findViewById(R.id.memory_assessment_checkBox6);
        checkBoxes[6]=root.findViewById(R.id.memory_assessment_checkBox7);
        checkBoxes[7]=root.findViewById(R.id.memory_assessment_checkBox8);
        checkBoxes[8]=root.findViewById(R.id.memory_assessment_checkBox9);
        checkBoxes[9]=root.findViewById(R.id.memory_assessment_checkBox10);
        ImageView voice1=root.findViewById(R.id.memory_assessment_imageView1);
        ImageView voice2=root.findViewById(R.id.memory_assessment_imageView2);
        voice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"04_speech_1",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"04_speech_2",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        for(int i=0;i<10;i++){
            checkBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        CommonViewModel.getInstance().getRecord().getMemory()[Integer.valueOf(buttonView.getContentDescription().toString())]=1;
                    else
                        CommonViewModel.getInstance().getRecord().getMemory()[Integer.valueOf(buttonView.getContentDescription().toString())]=0;
                }
            });
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel commonViewModel=CommonViewModel.getInstance();
        if(commonViewModel.getIsQueryEnd().getValue()==1&&commonViewModel.getHasRead()[3]==0){
            commonViewModel.getHasRead()[3]=-1;
            for(int i=0;i<10;i++){
                if(commonViewModel.getRecord().getMemory()[Integer.parseInt(checkBoxes[i].getContentDescription().toString())]==1) checkBoxes[i].setChecked(true);
                else checkBoxes[i].setChecked(false);
            }
        }
    }
}
