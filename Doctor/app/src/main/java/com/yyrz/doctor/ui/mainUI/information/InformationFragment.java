package com.yyrz.doctor.ui.mainUI.information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.model.DoctorInformation;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

public class InformationFragment extends Fragment {

    private RecyclerView recyclerView;
    private ConstraintLayout progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestRepository.getInstance().getDoctorInformation();
        CommonViewModel.getInstance().getDoctorInformation().setValue(null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.information, container, false);
        recyclerView = root.findViewById(R.id.information_recyclerView);
        recyclerView.setVisibility(View.GONE);
        progressBar=root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance().getDoctorInformation().observe(getViewLifecycleOwner(), new Observer<DoctorInformation>() {
            @Override
            public void onChanged(DoctorInformation doctorInformation) {
                if (doctorInformation != null) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new MyRecyclerViewAdapt(doctorInformation.getKey(), doctorInformation.getValue()));
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CommonViewModel.getInstance().getDoctorInformation().setValue(null);
    }
}