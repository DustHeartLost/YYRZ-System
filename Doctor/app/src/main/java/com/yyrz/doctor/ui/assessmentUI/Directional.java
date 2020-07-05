package com.yyrz.doctor.ui.assessmentUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

public class Directional extends Fragment {
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private RadioGroup radioGroup4;
    private RadioGroup radioGroup5;
    private RadioGroup radioGroup6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.directional, container, false);
        radioGroup1 =root.findViewById(R.id.directional_radioGroup1);
        radioGroup2 =root.findViewById(R.id.directional_radioGroup2);
        radioGroup3 =root.findViewById(R.id.directional_radioGroup3);
        radioGroup4 =root.findViewById(R.id.directional_radioGroup4);
        radioGroup5 =root.findViewById(R.id.directional_radioGroup5);
        radioGroup6 =root.findViewById(R.id.directional_radioGroup6);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.directional_radioButton2) CommonViewModel.getInstance().getRecord().getDirection()[0]=1;
                else CommonViewModel.getInstance().getRecord().getDirection()[0]=0;
            }});
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.directional_radioButton4) CommonViewModel.getInstance().getRecord().getDirection()[1]=1;
                else CommonViewModel.getInstance().getRecord().getDirection()[1]=0;
            }});
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.directional_radioButton6) CommonViewModel.getInstance().getRecord().getDirection()[2]=1;
                else CommonViewModel.getInstance().getRecord().getDirection()[2]=0;
            }});
        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.directional_radioButton8) CommonViewModel.getInstance().getRecord().getDirection()[3]=1;
                else CommonViewModel.getInstance().getRecord().getDirection()[3]=0;
            }});
        radioGroup5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.directional_radioButton10) CommonViewModel.getInstance().getRecord().getDirection()[4]=1;
                 else CommonViewModel.getInstance().getRecord().getDirection()[4]=0;
            }});
        radioGroup6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.directional_radioButton12) CommonViewModel.getInstance().getRecord().getDirection()[5]=1;
                 else CommonViewModel.getInstance().getRecord().getDirection()[5]=0;
            }});

        ImageView voice1=root.findViewById(R.id.directional_imageView1);
        ImageView voice2=root.findViewById(R.id.directional_imageView2);
        ImageView voice3=root.findViewById(R.id.directional_imageView3);
        voice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"09_speech_1",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"09_speech_2",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"09_speech_3",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel commonViewModel=CommonViewModel.getInstance();
        if(commonViewModel.getIsQueryEnd().getValue()==1&&commonViewModel.getHasRead()[8]==0){
            commonViewModel.getHasRead()[8]=-1;
            if(commonViewModel.getRecord().getDirection()[0]==1) radioGroup1.check(R.id.directional_radioButton2);
            else radioGroup1.check(R.id.directional_radioButton1);
            if(commonViewModel.getRecord().getDirection()[1]==1) radioGroup2.check(R.id.directional_radioButton4);
            else radioGroup2.check(R.id.directional_radioButton3);
            if(commonViewModel.getRecord().getDirection()[2]==1) radioGroup3.check(R.id.directional_radioButton6);
            else radioGroup3.check(R.id.directional_radioButton5);
            if(commonViewModel.getRecord().getDirection()[3]==1) radioGroup4.check(R.id.directional_radioButton8);
            else radioGroup4.check(R.id.directional_radioButton7);
            if(commonViewModel.getRecord().getDirection()[4]==1) radioGroup5.check(R.id.directional_radioButton10);
            else radioGroup5.check(R.id.directional_radioButton9);
            if(commonViewModel.getRecord().getDirection()[5]==1) radioGroup6.check(R.id.directional_radioButton12);
            else radioGroup6.check(R.id.directional_radioButton11);
        }
    }
}
