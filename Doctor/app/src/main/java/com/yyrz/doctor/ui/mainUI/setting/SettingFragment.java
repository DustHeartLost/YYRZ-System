package com.yyrz.doctor.ui.mainUI.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import cn.jpush.android.api.JPushInterface;

public class SettingFragment extends Fragment {

    Button button;
    TextView textView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.setting, container, false);
        textView=root.findViewById(R.id.textView);
        button=root.findViewById(R.id.button);
        textView.setText(CommonViewModel.getInstance().getJPushAlias().getValue());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JPushInterface.deleteAlias(CommonViewModel.getInstance().getContext(),2);
            }
        });
        CommonViewModel.getInstance().getJPushAlias().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(CommonViewModel.getInstance().getContext(),s,Toast.LENGTH_LONG).show();
                if(s!=null)
                    textView.setText(s);
            }
        });
    }
}