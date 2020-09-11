package com.yyrz.doctor.ui.commonUi.patientlist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.Timer;
import java.util.TimerTask;

public class PatientList extends Fragment {
    private ConstraintLayout progressBar;
    private RecyclerView recyclerView;
    private CommonViewModel commonViewModel;
    private char state;
    private int flag;

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
                    flag=1;
                    progressBar.setVisibility(View.GONE);
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_patientList_to_showViewPager);
                }
                if(integer.equals(20)){
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_patientList_to_training_home_page);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        @SuppressLint("HandlerLeak")
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("请确保患者端登录到主界面").create().show();
            }
        };
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
                flag=0;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(flag!=1){
                            handler.sendEmptyMessage(0);
                        }
                    }
                },10000);
            }});
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CommonViewModel.getInstance().getPatientInformation().setValue(null);
    }
}

