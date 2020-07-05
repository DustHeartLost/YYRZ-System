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

public class Abstraction extends Fragment {
    private CommonViewModel commonViewModel;
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.abstraction, container, false);
        radioGroup1 = root.findViewById(R.id.abstract_radioGroup1);
        radioGroup2 = root.findViewById(R.id.abstract_radioGroup2);
        commonViewModel=CommonViewModel.getInstance();

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.abstract_radioButton2) commonViewModel.getRecord().getAbstraction()[0]=1;
                else commonViewModel.getRecord().getAbstraction()[0]=0;
        }});
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.abstract_radioButton4) commonViewModel.getRecord().getAbstraction()[1]=1;
                else commonViewModel.getRecord().getAbstraction()[1]=0;
            }
        });
        ImageView voice1=root.findViewById(R.id.abstract_imageView1);
        ImageView voice2=root.findViewById(R.id.abstract_imageView2);
        ImageView voice3=root.findViewById(R.id.abstract_imageView3);
        ImageView voice4=root.findViewById(R.id.abstract_imageView4);
        ImageView voice5=root.findViewById(R.id.abstract_imageView5);
        voice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"07_speech_1",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"07_speech_2",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"07_speech_3",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"07_speech_4",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"07_speech_5",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(commonViewModel.getIsQueryEnd().getValue()==1&commonViewModel.getHasRead()[6]==0){
            commonViewModel.getHasRead()[6]=-1;
            if(commonViewModel.getRecord().getAbstraction()[0]==1) radioGroup1.check(R.id.abstract_radioButton2);
            else radioGroup1.check(R.id.abstract_radioButton1);
            if(commonViewModel.getRecord().getAbstraction()[1]==1) radioGroup2.check(R.id.abstract_radioButton4);
            else radioGroup2.check(R.id.abstract_radioButton3);
        }
    }
}
