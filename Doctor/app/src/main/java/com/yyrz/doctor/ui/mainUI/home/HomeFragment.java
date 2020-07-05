package com.yyrz.doctor.ui.mainUI.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

public class HomeFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!CommonViewModel.getInstance().getIsFirstEnter()) {
            CommonViewModel.getInstance().getNavController().navigate(R.id.action_nav_home_to_login);
            CommonViewModel.getInstance().setIsFirstEnter(true);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home, container, false);
        Button assessmentButton = root.findViewById(R.id.home_assessment_button);
        Button trainingButton = root.findViewById(R.id.home_training_button);
        //添加配置
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putChar("control",'1');
                CommonViewModel.getInstance().getNavController().navigate(R.id.action_nav_home_to_patientList,bundle);

            }
        });
        trainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putChar("control",'2');
                CommonViewModel.getInstance().getNavController().navigate(R.id.action_nav_home_to_patientList,bundle);
            }
        });
        return root;
    }
}
