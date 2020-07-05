package com.yyrz.doctor.ui.commonUi.patientlist;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.DataRestored.Repository;
import com.yyrz.doctor.Util.model.PatientInformation;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.util.ArrayList;

public class MyItemAdapter extends RecyclerView.Adapter<MyItemAdapter.ViewHolder> {
    private ArrayList<PatientInformation> arrayList;
    private ConstraintLayout progressBar;
    private RecyclerView recyclerView;
    private char state;

    MyItemAdapter(ArrayList<PatientInformation> patientInformation, RecyclerView recyclerView, ConstraintLayout progressBar,char state) {
        this.arrayList=patientInformation;
        this.recyclerView=recyclerView;
        this.progressBar=progressBar;
        this.state=state;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if(position==0){
            Resources res= CommonViewModel.getInstance().getContext().getResources();
            String [] temp=res.getStringArray(R.array.patient_information);
            holder.textView1.setText(temp[0]);
            holder.textView2.setText(temp[1]);
            holder.textView3.setText(temp[2]);
            holder.textView4.setText(temp[3]);
            holder.textView5.setText(temp[4]);
            return;
        }
        Gson gson=new Gson();
        PatientInformation patientInformation =gson.fromJson(gson.toJson(arrayList.get(position-1)),PatientInformation.class);
        holder.textView1.setText(patientInformation.getName());
        holder.textView2.setText(patientInformation.getGender());
        holder.textView3.setText(patientInformation.getAge());
        holder.textView4.setText(patientInformation.getDepartment());
        holder.textView5.setText(patientInformation.getBed_number());
        holder.setPatientAccount(patientInformation.getAccount());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonViewModel commonViewModel=CommonViewModel.getInstance();
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                commonViewModel.setCurrentPaccount(holder.getPatientAccount());
                switch (state){
                    case '1':
                        CommonViewModel.getInstance().setRecord(null);
                        CommonViewModel.getInstance().getIsQueryEnd().setValue(0);
                        Repository.getInstance().querydRecord(commonViewModel.getCurrentPaccount());
                        break;
                    case '2':
                        CommonViewModel.getInstance().getCurrentState().postValue(0);
                        RequestRepository.getInstance().currentState(commonViewModel.getCurrentPaccount(),"20", CommonViewModel.CON_TRAINING, CommonViewModel.TYPE_NAVCONTROLLER,commonViewModel.getAccount());
                        break;
                }
               
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size()+1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        private String patientAccount;

        ViewHolder(View view) {
            super(view);
            mView = view;
            textView1 = view.findViewById(R.id.assessment_patient_information_texView1);
            textView2 = view.findViewById(R.id.assessment_patient_information_texView2);
            textView3 = view.findViewById(R.id.assessment_patient_information_texView3);
            textView4 = view.findViewById(R.id.assessment_patient_information_texView4);
            textView5 = view.findViewById(R.id.assessment_patient_information_texView5);
        }

        String getPatientAccount() {
            return patientAccount;
        }

        void setPatientAccount(String account) {
            this.patientAccount = account;
        }
    }
}
