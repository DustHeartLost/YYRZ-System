package com.yyrz.doctor.ui.assessmentUI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

public class FluentLanguge extends Fragment {
    private CommonViewModel commonViewModel;
    private EditText editText;
    private CheckBox[] checkBoxes;
    private RadioGroup radioGroup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fluent_languge, container, false);
        checkBoxes = new CheckBox[2];
        checkBoxes[0]=root.findViewById(R.id.fluentLanguage_checkBox1);
        checkBoxes[1]=root.findViewById(R.id.fluentLanguage_checkBox2);
        radioGroup = root.findViewById(R.id.fluentLanguage_radioGroup);
        commonViewModel=CommonViewModel.getInstance();
        editText=root.findViewById(R.id.fluentLanguage_editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                CommonViewModel.getInstance().getRecord().setLanguage_record(s.toString());
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.fluentLanguage_radioButton2) commonViewModel.getRecord().getLanguage()[2] = 1;
            }
        });
        for(int i=0;i<2;i++){
            checkBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) commonViewModel.getRecord().getLanguage()[Integer.valueOf(compoundButton.getContentDescription().toString())]=1;
                    else commonViewModel.getRecord().getLanguage()[Integer.valueOf(compoundButton.getContentDescription().toString())]=0;
                }
            });
        }
        ImageView voice1=root.findViewById(R.id.fluentLanguage_imageView1);
        ImageView voice2=root.findViewById(R.id.fluentLanguage_imageView2);
        ImageView voice3=root.findViewById(R.id.fluentLanguage_imageView3);
        voice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"06_speech_1",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"06_speech_2",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        voice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=CommonViewModel.getInstance().getCurrentPaccount();
                String daccount=CommonViewModel.getInstance().getAccount();
                RequestRepository.getInstance().currentState(alias,"06_speech_3",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(commonViewModel.getIsQueryEnd().getValue()==1&&commonViewModel.getHasRead()[5]==0){
            commonViewModel.getHasRead()[5]=-1;
            for(int i=0;i<2;i++){
                if(commonViewModel.getRecord().getLanguage()[i]==1) checkBoxes[i].setChecked(true);
                else checkBoxes[i].setChecked(false);
            }
            if(commonViewModel.getRecord().getLanguage()[2]==1) radioGroup.check(R.id.fluentLanguage_radioButton2);
            else radioGroup.check(R.id.fluentLanguage_radioButton1);
            editText.setText(commonViewModel.getRecord().getLanguage_record());
        }
    }
}
