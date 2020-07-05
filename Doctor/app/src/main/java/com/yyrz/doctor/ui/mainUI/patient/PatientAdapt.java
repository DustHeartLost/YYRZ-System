package com.yyrz.doctor.ui.mainUI.patient;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.model.PatientInformation;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.util.ArrayList;

public class PatientAdapt extends RecyclerView.Adapter<PatientAdapt.MyViewHolder> {

    private String[] strings;
    private ArrayList<PatientInformation> arrayList;

    PatientAdapt(){
        strings=CommonViewModel.getInstance().getContext().getResources().getStringArray(R.array.patient_information);
    }

    void delete(int position){
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_adapter, parent, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        arrayList=CommonViewModel.getInstance().getPatientInformation().getValue();
        Gson gson=new Gson();
        final PatientInformation patientInformation =gson.fromJson(gson.toJson(arrayList.get(position)),PatientInformation.class);
        for(int i=1;i<6;i++){
            holder.textView[i].setText(strings[i-1]);}
        holder.divider.setContentDescription(patientInformation.getAccount());
        holder.textView[0].setText(patientInformation.getHosiptal());
        holder.textView[6].setText(patientInformation.getName());
        holder.textView[7].setText(patientInformation.getGender());
        holder.textView[8].setText(patientInformation.getAge());
        holder.textView[9].setText(patientInformation.getDepartment());
        holder.textView[10].setText(patientInformation.getBed_number());
        holder.divider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonViewModel.getInstance().setCurrentPaccount(v.getContentDescription().toString());
                CommonViewModel.getInstance().getNavController().navigate(R.id.action_nav_patient_to_pieChartView);
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(CommonViewModel.getInstance().getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.delete,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        CommonViewModel.getInstance().getCodeAndMsg().postValue(null);
                        RequestRepository.getInstance().noBind(holder.divider.getContentDescription().toString(),position);
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return CommonViewModel.getInstance().getPatientInformation().getValue().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView[] textView;
        View divider;
        ImageButton imageButton;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=new TextView[11];
            this.textView[0]= itemView.findViewById(R.id.my_patient_textView1);
            this.textView[1]= itemView.findViewById(R.id.my_patient_textView2);
            this.textView[2]= itemView.findViewById(R.id.my_patient_textView3);
            this.textView[3]= itemView.findViewById(R.id.my_patient_textView4);
            this.textView[4]= itemView.findViewById(R.id.my_patient_textView5);
            this.textView[5]= itemView.findViewById(R.id.my_patient_textView6);
            this.textView[6]= itemView.findViewById(R.id.my_patient_textView7);
            this.textView[7]= itemView.findViewById(R.id.my_patient_textView8);
            this.textView[8]= itemView.findViewById(R.id.my_patient_textView9);
            this.textView[9]= itemView.findViewById(R.id.my_patient_textView10);
            this.textView[10]= itemView.findViewById(R.id.my_patient_textView11);
            this.divider=itemView.findViewById(R.id.divider);
            this.imageButton=itemView.findViewById(R.id.imageButton2);
        }
    }
}
