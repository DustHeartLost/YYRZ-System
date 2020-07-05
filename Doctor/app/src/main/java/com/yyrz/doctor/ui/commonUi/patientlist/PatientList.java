package com.yyrz.doctor.ui.commonUi.patientlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.model.PatientInformation;
import com.yyrz.doctor.Util.model.Record;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PatientList extends Fragment {
    private ConstraintLayout progressBar;
    private RecyclerView recyclerView;
    private CommonViewModel commonViewModel;
    private char state;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state=getArguments().getChar("control");
        CommonViewModel.getInstance().getPatientInformation().setValue(null);
        RequestRepository.getInstance().getPatientInformationFromNet(CommonViewModel.getInstance().getAccount());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.patient_list, container, false);
        progressBar=root.findViewById(R.id.progressBar);
        recyclerView=root.findViewById(R.id.assessment_patient_recyclerview);
        commonViewModel=CommonViewModel.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance().getPatientInformation().observe(getViewLifecycleOwner(), new Observer<ArrayList<PatientInformation>>() {
            @Override
            public void onChanged(ArrayList<PatientInformation> patientInformation) {
                if(patientInformation!=null){
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new MyItemAdapter(patientInformation,recyclerView,progressBar,state));
                }
            }
        });
        CommonViewModel.getInstance().getCurrentState().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(0<integer&&integer<=10){
                    progressBar.setVisibility(View.GONE);
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_patientList_to_showViewPager);
                }
                if(integer.equals(20)){
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_patientList_to_training_home_page);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        commonViewModel.getIsQueryEnd().observe(this,new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==0)return;
                CommonViewModel commonViewModel=CommonViewModel.getInstance();
                if (integer == 1) {
                    String state=new DecimalFormat("00").format(CommonViewModel.getInstance().getRecord().getState());
                    RequestRepository.getInstance().currentState(commonViewModel.getCurrentPaccount(),state, CommonViewModel.CON_ASSESSMENT, CommonViewModel.TYPE_DESTINATION, commonViewModel.getAccount());
                }
                if(integer==2){
                    CommonViewModel.getInstance().setRecord(new Record());
                    commonViewModel.getRecord().setDaccount(commonViewModel.getAccount());
                    commonViewModel.getRecord().setPaccount(commonViewModel.getCurrentPaccount());
                    RequestRepository.getInstance().currentState(commonViewModel.getCurrentPaccount(),"01", CommonViewModel.CON_ASSESSMENT, CommonViewModel.TYPE_NAVCONTROLLER, commonViewModel.getAccount());
                }
            }});
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CommonViewModel.getInstance().getPatientInformation().setValue(null);
    }
}

