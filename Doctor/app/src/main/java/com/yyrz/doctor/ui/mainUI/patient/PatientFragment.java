package com.yyrz.doctor.ui.mainUI.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.model.PatientInformation;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.util.ArrayList;

public class PatientFragment extends Fragment {
    private RecyclerView recyclerView;
    private ConstraintLayout progressBar;
    private PatientAdapt patientAdapt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.addPatient).setVisible(true);
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.search).setVisible(false);
        CommonViewModel.getInstance().getPatientInformation().setValue(null);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.patient, container, false);
        recyclerView = root.findViewById(R.id.information_recyclerView);
        recyclerView.setVisibility(View.GONE);
        progressBar=root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        patientAdapt =new PatientAdapt();
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.addPatient).setVisible(true);
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.search).setVisible(false);
        CommonViewModel.getInstance().getCodeAndMsg().setValue(null);
        CommonViewModel.getInstance().getFloatBottom().setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance().getPatientInformation().observe(this, new Observer<ArrayList<PatientInformation>>() {
            @Override
            public void onChanged(ArrayList<PatientInformation> patientInformations) {
                if(patientInformations==null){
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    RequestRepository.getInstance().getPatientInformationFromNet(CommonViewModel.getInstance().getAccount());
                }else{
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(patientAdapt);
                }
            }
        });
        CommonViewModel.getInstance().getCodeAndMsg().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s==null)return;
                if(s.substring(0,3).equals("100")){
                    patientAdapt.delete(Integer.parseInt(s.substring(4)));
                    CommonViewModel.getInstance().getCodeAndMsg().postValue(null);
                }else{
                    Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.addPatient).setVisible(false);
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.search).setVisible(false);
        CommonViewModel.getInstance().getPatientInformation().setValue(null);
        CommonViewModel.getInstance().setCurrentPaccount(null);
    }
}