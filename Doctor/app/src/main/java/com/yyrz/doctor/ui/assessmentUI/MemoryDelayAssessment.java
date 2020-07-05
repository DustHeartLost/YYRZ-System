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

public class MemoryDelayAssessment extends Fragment {
    private CheckBox []checkBox;
    private ImageView[] imageView;
    private String[] classify;
    private String[] choice;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root=inflater.inflate(R.layout.memory_delay_assessment, container, false);
        checkBox=new CheckBox[15];
        imageView =new ImageView[11];
        checkBox[0]=root.findViewById(R.id.assessment_delay_memory_checkBox1);
        checkBox[1]=root.findViewById(R.id.assessment_delay_memory_checkBox2);
        checkBox[2]=root.findViewById(R.id.assessment_delay_memory_checkBox3);
        checkBox[3]=root.findViewById(R.id.assessment_delay_memory_checkBox4);
        checkBox[4]=root.findViewById(R.id.assessment_delay_memory_checkBox5);
        checkBox[5]=root.findViewById(R.id.assessment_delay_memory_checkBox6);
        checkBox[6]=root.findViewById(R.id.assessment_delay_memory_checkBox7);
        checkBox[7]=root.findViewById(R.id.assessment_delay_memory_checkBox8);
        checkBox[8]=root.findViewById(R.id.assessment_delay_memory_checkBox9);
        checkBox[9]=root.findViewById(R.id.assessment_delay_memory_checkBox10);
        checkBox[10]=root.findViewById(R.id.assessment_delay_memory_checkBox11);
        checkBox[11]=root.findViewById(R.id.assessment_delay_memory_checkBox12);
        checkBox[12]=root.findViewById(R.id.assessment_delay_memory_checkBox13);
        checkBox[13]=root.findViewById(R.id.assessment_delay_memory_checkBox14);
        checkBox[14]=root.findViewById(R.id.assessment_delay_memory_checkBox15);
        imageView[0]=root.findViewById(R.id.assessment_delay_memory_imageView1);
        imageView[1]=root.findViewById(R.id.assessment_delay_memory_imageView2);
        imageView[2]=root.findViewById(R.id.assessment_delay_memory_imageView3);
        imageView[3]=root.findViewById(R.id.assessment_delay_memory_imageView4);
        imageView[4]=root.findViewById(R.id.assessment_delay_memory_imageView5);
        imageView[5]=root.findViewById(R.id.assessment_delay_memory_imageView6);
        imageView[6]=root.findViewById(R.id.assessment_delay_memory_imageView7);
        imageView[7]=root.findViewById(R.id.assessment_delay_memory_imageView8);
        imageView[8]=root.findViewById(R.id.assessment_delay_memory_imageView9);
        imageView[9]=root.findViewById(R.id.assessment_delay_memory_imageView10);
        imageView[10]=root.findViewById(R.id.assessment_delay_memory_imageView11);
        classify=new String[5];
        choice=new String[5];
        for (int i=1;i<=5;i++)classify[i-1]="08_speech_2_"+i;
        for (int i=1;i<=5;i++)choice[i-1]="08_speech_3_"+i;
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for(int i=0;i<15;i++){
            checkBox[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    CommonViewModel commonViewModel= CommonViewModel.getInstance();
                    if(b)
                        commonViewModel.getRecord().getDelayMemory()[Integer.valueOf(compoundButton.getContentDescription().toString())]=1;
                    else
                        commonViewModel.getRecord().getDelayMemory()[Integer.valueOf(compoundButton.getContentDescription().toString())]=0;
                }
            });
        }
        CommonViewModel commonViewModel= CommonViewModel.getInstance();
        if(commonViewModel.getIsQueryEnd().getValue()==1&&commonViewModel.getHasRead()[7]==0){
            commonViewModel.getHasRead()[7]=-1;
            for(int i=0;i<15;i++){
                if(commonViewModel.getRecord().getDelayMemory()[Integer.parseInt(checkBox[i].getContentDescription().toString())]==1) checkBox[i].setChecked(true);
                else checkBox[i].setChecked(false);
            }
        }
        for(int i=1;i<=5;i++){
            imageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String alias=CommonViewModel.getInstance().getCurrentPaccount();
                    String daccount=CommonViewModel.getInstance().getAccount();
                    int i= Integer.parseInt(v.getContentDescription().toString());
                    RequestRepository.getInstance().currentState(alias,classify[i],CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
                }
            });
        }
        for(int i=6;i<=10;i++){
            imageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String alias=CommonViewModel.getInstance().getCurrentPaccount();
                    String daccount=CommonViewModel.getInstance().getAccount();
                    int i= Integer.parseInt(v.getContentDescription().toString());
                    RequestRepository.getInstance().currentState(alias,choice[i],CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
                }
            });
        }
        imageView[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"08_speech_1",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
    }
}
