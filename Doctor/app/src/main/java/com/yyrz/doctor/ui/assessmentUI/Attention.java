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

public class Attention extends Fragment {
    private CommonViewModel commonViewModel;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.attention, container, false);
        commonViewModel = CommonViewModel.getInstance();
        radioGroup1=root.findViewById(R.id.attention_radioGroup1);
        radioGroup2=root.findViewById(R.id.attention_radioGroup2);
        radioGroup3=root.findViewById(R.id.attention_radioGroup3);
        ImageView voice1=root.findViewById(R.id.attention_imageView1);
        ImageView voice2=root.findViewById(R.id.attention_imageView2);
        ImageView voice3=root.findViewById(R.id.attention_imageView3);
        ImageView voice4=root.findViewById(R.id.attention_imageView4);
        voice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"05_speech_1",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"05_speech_2",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"05_speech_3",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"05_speech_4",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.attention_radioButton1 :commonViewModel.getRecord().getAttention()[0]=0;break;
                    case R.id.attention_radioButton2 :commonViewModel.getRecord().getAttention()[0]=1;break;
                    case R.id.attention_radioButton3 :commonViewModel.getRecord().getAttention()[0]=2;break;
                }
            }});
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.attention_radioButton4 :commonViewModel.getRecord().getAttention()[1]=0;break;
                    case R.id.attention_radioButton5 :commonViewModel.getRecord().getAttention()[1]=1;break;
                }
            }});

        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.attention_radioButton6 :commonViewModel.getRecord().getAttention()[2]=0;break;
                    case R.id.attention_radioButton7 :commonViewModel.getRecord().getAttention()[2]=1;break;
                    case R.id.attention_radioButton8 :commonViewModel.getRecord().getAttention()[2]=2;break;
                    case R.id.attention_radioButton9 :commonViewModel.getRecord().getAttention()[2]=3;break;
                }
            }});
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(commonViewModel.getIsQueryEnd().getValue()==1&&commonViewModel.getHasRead()[4]==0){
            commonViewModel.getHasRead()[4]=-1;
            switch (commonViewModel.getRecord().getAttention()[0]){
                case 0:radioGroup1.check(R.id.attention_radioButton1);break;
                case 1:radioGroup1.check(R.id.attention_radioButton2);break;
                case 2:radioGroup1.check(R.id.attention_radioButton3);break;}
            switch (commonViewModel.getRecord().getAttention()[1]){
                case 0:radioGroup2.check(R.id.attention_radioButton4);break;
                case 1:radioGroup2.check(R.id.attention_radioButton5);break;}
            switch (commonViewModel.getRecord().getAttention()[2]){
                case 0:radioGroup3.check(R.id.attention_radioButton6);break;
                case 1:radioGroup3.check(R.id.attention_radioButton7);break;
                case 2:radioGroup3.check(R.id.attention_radioButton8);break;
                case 3:radioGroup3.check(R.id.attention_radioButton9);break;}
        }
    }
}
