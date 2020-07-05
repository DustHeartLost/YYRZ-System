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

public class Named extends Fragment {
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private CommonViewModel commonViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.named, container, false);
        commonViewModel=CommonViewModel.getInstance();
        radioGroup1 = root.findViewById(R.id.named_radioGroup1);
        radioGroup2 = root.findViewById(R.id.named_radioGroup2);
        radioGroup3 = root.findViewById(R.id.named_radioGroup3);
        ImageView voice1=root.findViewById(R.id.named_imageView1);
        ImageView voice2=root.findViewById(R.id.named_imageView4);
        ImageView voice3=root.findViewById(R.id.named_imageView3);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.named_radioButton2) commonViewModel.getRecord().getName()[0]=1;
                else commonViewModel.getRecord().getName()[0]=0;
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.named_radioButton4)commonViewModel.getRecord().getName()[1]=1;
                else commonViewModel.getRecord().getName()[1]=0;
        }});
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.named_radioButton6) commonViewModel.getRecord().getName()[2]=1;
                else commonViewModel.getRecord().getName()[2]=0;
            }
        });
        voice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"03_speech_2",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);                                                                                                                                                                                                                                                                                                     RequestRepository.getInstance().currentState(alias,"03_speech_1",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"03_speech_1",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });

        voice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"03_speech_3",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(commonViewModel.getIsQueryEnd().getValue()==1&commonViewModel.getHasRead()[2]==0){
            commonViewModel.getHasRead()[2]=-1;
            if(commonViewModel.getRecord().getName()[0]==1) radioGroup1.check(R.id.named_radioButton2);
            else radioGroup1.check(R.id.named_radioButton1);
            if(commonViewModel.getRecord().getName()[1]==1) radioGroup2.check(R.id.named_radioButton4);
            else radioGroup2.check(R.id.named_radioButton3);
            if(commonViewModel.getRecord().getName()[2]==1) radioGroup3.check(R.id.named_radioButton6);
            else radioGroup3.check(R.id.named_radioButton5);
        }
    }
}
