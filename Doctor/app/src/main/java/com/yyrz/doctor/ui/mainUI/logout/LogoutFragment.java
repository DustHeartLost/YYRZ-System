package com.yyrz.doctor.ui.mainUI.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yyrz.doctor.R;


public class LogoutFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.logout, container, false);

        return root;
    }
//    private Viewport initViewPort() {
//        Viewport viewport = new Viewport();
//        viewport.top = 100;
//        viewport.bottom = 0;
//        viewport.left = 0;
//        viewport.right = 90;
//
//        return viewport;
//    }
//    private List<Line> initLine() {
//        //图表上的曲线集合
//        List<Line> lineList = new ArrayList<>();
//
//        List<PointValue> pointValueList = new ArrayList<>();
//        //每个point对应线上的一个点
//        PointValue pointValue1 = new PointValue(10, 30);
//        pointValueList.add(pointValue1);
//        PointValue pointValue2 = new PointValue(20, 20);
//        pointValueList.add(pointValue2);
//        PointValue pointValue3 = new PointValue(30, 70);
//        pointValueList.add(pointValue3);
//        PointValue pointValue4 = new PointValue(40, 69);
//        pointValueList.add(pointValue4);
//        PointValue pointValue5 = new PointValue(50, 64);
//        pointValueList.add(pointValue5);
//        PointValue pointValue6 = new PointValue(60, 31);
//        pointValueList.add(pointValue6);
//        PointValue pointValue7 = new PointValue(70, 22);
//        pointValueList.add(pointValue7);
//        PointValue pointValue8 = new PointValue(80, 100);
//        pointValueList.add(pointValue8);
//
//
//        Line line = new Line(pointValueList);
//        //设置线条颜色
//        line.setColor(ChartUtils.COLORS[i]);
//        //设置点的类型（圆形，方形，菱形）
//        line.setShape(ValueShape.CIRCLE);
//        //设置线的类型是否为圆滑的曲线
//        line.setCubic(true);
//        //设置线上点的标注是否仅在被选中时出现
//        line.setHasLabelsOnlyForSelected(false);
//        //设置是否显示线上点的标注
//        line.setHasLabels(false);
//        //是否显示线条
//        line.setHasLines(true);
//        //是否显示点
//        line.setHasPoints(true);
//        //这一步是能让标注数据显示带小数的重要一步
//        LineChartValueFormatter chartValueFormatter = new SimpleLineChartValueFormatter(2);//2代表是2位小数点
//        line.setFormatter(chartValueFormatter);
//        lineList.add(line);
//
//        return lineList;
//    }
//    private LineChartData initData(List<Line> lines) {
//
//        LineChartData data = new LineChartData(lines);
//        //初始化轴
//        Axis axisX = new Axis();
//        Axis axisY = new Axis().setHasLines(true);
//        axisX.setName("时间");
//        //前加字符 注意，加字符以最后设置一次的为准
//        //axisX.setFormatter(new SimpleAxisValueFormatter().setPrependedText("a".toCharArray()));
//        //后加字符
//        //axisX.setFormatter(new SimpleAxisValueFormatter().setAppendedText("b".toCharArray()));
//
//
//        axisY.setName("销量");
//        axisY.hasLines();//是否显示网格线
//        axisY.setTextColor(Color.BLACK);//颜色
//
//        //设置轴
//        data.setAxisYLeft(axisY);
//        data.setAxisXBottom(axisX);
//
//        //设置负值 设置为负无穷 默认为0
//        data.setBaseValue(Float.NEGATIVE_INFINITY);
//
//        return data;
//    }
//    private void linechartview(){
//
//        LineChartView lcv=root.findViewById(R.id.linechartview);
//        //初始化线条集合
//        List<Line> lines = initLine();
//        //初始化折线图的数据
//        LineChartData data = initData(lines);
//        //设置数据
//        lcv.setLineChartData(data);
//        //限定坐标系边界
//        Viewport viewport = initViewPort();
//        lcv.setMaximumViewport(viewport);
//        lcv.setCurrentViewport(viewport);//注意，此方法需在给图表设置数据后方见效
//        //当折线上点被触摸时的事件
//        lcv.setOnValueTouchListener(new LineChartOnValueSelectListener() {
//            @Override
//            public void onValueSelected(int i, int i1, PointValue pointValue) {
//                //i表示的是第几根折线，而i1表示的是这根折线上的第几个点（以0为基准）
//                Toast.makeText(MyLineCharts.this, "i:" + i + ",i1:" + i1 + ",point:" + pointValue.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onValueDeselected() {
//                //当点失去焦点时
//                Toast.makeText(MyLineCharts.this, "???", Toast.LENGTH_SHORT).show();
//            }
//        });
//        //设置点可以被选中
//        lcv.setValueSelectionEnabled(true);
//    }
}