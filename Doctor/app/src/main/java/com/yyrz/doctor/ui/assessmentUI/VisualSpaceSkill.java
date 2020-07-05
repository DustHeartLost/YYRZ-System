package com.yyrz.doctor.ui.assessmentUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

public class VisualSpaceSkill extends Fragment {
    private RadioGroup radioGroup;
    private CheckBox[] checkBoxes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.visual_space_skill, container, false);
        checkBoxes= new CheckBox[3];
        checkBoxes[0]=root.findViewById(R.id.visualSpaceSkill_checkBox1);
        checkBoxes[1]=root.findViewById(R.id.visualSpaceSkill_checkBox2);
        checkBoxes[2]=root.findViewById(R.id.visualSpaceSkill_checkBox3);
        ImageView voice1=root.findViewById(R.id.visualSpaceSkill_imageView1);
        ImageView voice2=root.findViewById(R.id.visualSpaceSkill_imageView2);
        radioGroup= root.findViewById(R.id.visualSpaceSkill_radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                CommonViewModel commonViewModel= CommonViewModel.getInstance();
                if(i==R.id.visualSpaceSkill_radioButton1) commonViewModel.getRecord().getVisual()[0]=0;
                else commonViewModel.getRecord().getVisual()[0]=1;
            }
        });
        for(int i=0;i<3;i++){
            checkBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    CommonViewModel commonViewModel=CommonViewModel.getInstance();
                    if(b) commonViewModel.getRecord().getVisual()[Integer.valueOf(compoundButton.getContentDescription().toString())]=1;
                    else commonViewModel.getRecord().getVisual()[Integer.valueOf(compoundButton.getContentDescription().toString())]=0;
                }
            });
        }
        voice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"02_speech_1",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });

        voice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"02_speech_2",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel commonViewModel=CommonViewModel.getInstance();
        if(commonViewModel.getIsQueryEnd().getValue()==1&&commonViewModel.getHasRead()[1]==0){
            commonViewModel.getHasRead()[1]=-1;
            if (commonViewModel.getRecord().getVisual()[0] == 0) radioGroup.check(R.id.visualSpaceSkill_radioButton1);
            else radioGroup.check(R.id.visualSpaceSkill_radioButton2);
            for(int i=0;i<3;i++){
                if(commonViewModel.getRecord().getVisual()[i+1]==1) checkBoxes[i].setChecked(true);
                else checkBoxes[i].setChecked(false);
            }
        }
    }
}
