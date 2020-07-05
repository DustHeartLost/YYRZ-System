package com.yyrz.doctor.ui.mainUI.patient.pieChart;

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

import com.google.gson.JsonArray;
import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

public class PieChartView extends Fragment  {
    private RecyclerView recyclerView;
    private ConstraintLayout progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.search).setVisible(false);
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.addPatient).setVisible(false);
        CommonViewModel.getInstance().getChartData().setValue(null);
        RequestRepository.getInstance().getChartDataFromNet(CommonViewModel.getInstance().getCurrentPaccount());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root=inflater.inflate(R.layout.pie_chart_view, container, false);
        recyclerView = root.findViewById(R.id.information_recyclerView);
        recyclerView.setVisibility(View.GONE);
        progressBar=root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        CommonViewModel.getInstance().getFloatBottom().setVisibility(View.VISIBLE);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance().getChartData().observe(getViewLifecycleOwner(), new Observer<JsonArray>() {
            @Override
            public void onChanged(JsonArray jsonElements) {
                if (jsonElements!=null) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new ChartAdapter());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CommonViewModel.getInstance().getChartData().setValue(null);
    }
}
