package com.yyrz.patient.UI.sensorDataShow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.gson.JsonObject;
import com.yyrz.patient.R;
import com.yyrz.patient.common.viewModel.CommonViewModel;

import java.util.ArrayList;

import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class SensorFragment extends Fragment {

    private LineChartView lineChart;
    private ArrayList<PointValue> sensorList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View root=inflater.inflate(R.layout.sensor_data, container, false);
        //setContentView(R.layout.sensor_data);
        lineChart =root.findViewById(R.id.chart);

        //SensorDataShow.setChartViewData(jsonObject, lineChart);
        SensorDataShow.initLineChartView(lineChart);
        //SensorDataShow.setChartViewData(sensorList, lineChart);
//
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            int i;
//            int j;
//            @Override
//            public void run() {
//                sensorList.add(new PointValue(i++,j++));
//                SensorDataShow.loadData(sensorList,lineChart);
//            }
//        },1000,1000);
        return root;
        //return inflater.inflate(R.layout.sensor_data, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance().getSensor().observe(getViewLifecycleOwner(), new Observer<JsonObject>() {
           int i=0;
            @Override
            public void onChanged(JsonObject jsonObject) {
                //System.out.println(jsonObject.get("press"));
                sensorList.add(new PointValue(i++,Float.parseFloat(jsonObject.get("press").toString())));
                SensorDataShow.loadData(sensorList,lineChart);
            }
        });
    }
}