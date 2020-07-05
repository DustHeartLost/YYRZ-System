package com.yyrz.doctor.ui.mainUI.patient.pieChart;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.MyChartViewHolder> {
    private List<Integer> colorData = new ArrayList<>();
    private String[]strings;

    ChartAdapter(){
        colorData.add(Color.parseColor("#85B74F"));
        colorData.add(Color.parseColor("#009BDB"));
        colorData.add(Color.parseColor("#FF0000"));
        colorData.add(Color.parseColor("#9569F8"));
        colorData.add(Color.parseColor("#F87C67"));
        colorData.add(Color.parseColor("#F1DA3D"));
        colorData.add(Color.parseColor("#87EA39"));
        colorData.add(Color.parseColor("#48AEFA"));
        colorData.add(Color.parseColor("#4E5052"));
        colorData.add(Color.parseColor("#D36458"));
        strings=new String[8];
        String []temp= CommonViewModel.getInstance().getContext().getResources().getStringArray(R.array.assessment);
        for(int i=0;i<3;i++){
            strings[i]=temp[i];
        }
        for(int i=3;i<8;i++){
            strings[i]=temp[i+1];
        }
    }

    @NonNull
    @Override
    public MyChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.pie_chart_view_item, parent, false);
        return new MyChartViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyChartViewHolder holder, int position) {
        List<SliceValue> values=new ArrayList<>();
        //添加饼模块（数值，模块颜色）
        JsonArray jsonArray=CommonViewModel.getInstance().getChartData().getValue();
        JsonObject jsonObject= (JsonObject)jsonArray.get(position);
        Iterator<String> iterator=jsonObject.keySet().iterator();
        while (iterator.hasNext()){
            String a= iterator.next();
            SliceValue sliceValue=new SliceValue(Float.valueOf(jsonObject.get(a).toString()),colorData.get((int) (10 * Math.random())));
            sliceValue.setLabel(a+"分");
            values.add(sliceValue);
        }
        PieChartData pieChartData=new PieChartData(values);
        pieChartData.setHasLabels(true);
        pieChartData.setHasLabelsOutside(true)
                .setHasCenterCircle(true)
                .setSlicesSpacing(3)
                .setCenterCircleScale(0.4f)
                .setValueLabelTextSize(12);
        holder.pieChartView.setPieChartData(pieChartData);
        holder.pieChartView.setViewportCalculationEnabled(true);
        holder.pieChartView.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            @Override
            public void onValueDeselected() {
            }
            @Override
            public void onValueSelected(int arcIndex, final SliceValue value) {
                final String temp = new String(value.getLabelAsChars());
                Toast.makeText(CommonViewModel.getInstance().getContext(),temp+"的次数为："+value.getValue()+"次",Toast.LENGTH_SHORT).show();
            }});
        holder.textView.setText(strings[position]);
    }

    @Override
    public int getItemCount() {
        return CommonViewModel.getInstance().getChartData().getValue().size();
    }

    class MyChartViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        PieChartView pieChartView;
        public MyChartViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.chart_textView);
            pieChartView=itemView.findViewById(R.id.chart_PieChartView);
            pieChartView.isChartRotationEnabled();
            pieChartView.setZ(180);
            pieChartView.setCircleFillRatio(0.7f);
        }
    }
}
