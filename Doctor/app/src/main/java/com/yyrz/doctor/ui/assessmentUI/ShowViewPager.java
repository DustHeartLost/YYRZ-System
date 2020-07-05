package com.yyrz.doctor.ui.assessmentUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.badoualy.stepperindicator.StepperIndicator;
import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.text.DecimalFormat;

public class ShowViewPager extends Fragment {
    private ViewPager viewPager;
    public static String[] assessment_labels;
    private Button state;
    private StepperIndicator indicator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.show_view_pager, container, false);
        indicator=root.findViewById(R.id.viewpager_stepperIndicator);
        viewPager=root.findViewById(R.id.viewpager_viewPager);
        Button finishButton = root.findViewById(R.id.viewpager_button1);
        ImageButton next = root.findViewById(R.id.viewpager_imageButton2);
        state= root.findViewById(R.id.viewpager_button2);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager(),PagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        indicator.setViewPager(viewPager,true);
        indicator.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                viewPager.setCurrentItem(step,false);
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonViewModel commonViewModel=CommonViewModel.getInstance();
                String alias=commonViewModel.getCurrentPaccount();
                String daccount=commonViewModel.getAccount();
                CommonViewModel.getInstance().getCurrentState().setValue(0);
                Navigation.findNavController(v).navigate(R.id.action_showViewPager_to_finishProcess);
                RequestRepository.getInstance().currentState(alias,"10",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_NAVCONTROLLER,daccount);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonViewModel commonViewModel=CommonViewModel.getInstance();
                String state=new DecimalFormat("00").format(commonViewModel.getCurrentState().getValue()+1);
                if(state.equals("10"))
                    Toast.makeText(getContext(),"最后一个阶段",Toast.LENGTH_SHORT).show();
                else {
                    String alias=commonViewModel.getCurrentPaccount();
                    String daccount=commonViewModel.getAccount();
                    RequestRepository.getInstance().currentState(alias,state,CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_NAVCONTROLLER,daccount);
                }
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance().getCurrentState().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(0<integer&integer<10){
                    state.setText("当前评估阶段："+integer);
                    viewPager.setCurrentItem(integer-1,false);
                    indicator.setCurrentStep(integer-1);
                }
                if(integer.equals(10)){
                    CommonViewModel.getInstance().getNavController().navigate(R.id.action_showViewPager_to_finishProcess);
                }
            }
        });
    }
}