package com.yyrz.patient.UI.sensorDataShow;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class SensorDataShow {
    private static final String TAG = "Tools";
    private static Axis axisY;
    private static Axis axisX;
    //public static void setChartViewData(JsonObject jsonObject,LineChartView lineChart) {
    public static void initLineChartView(LineChartView lineChartView) {

            /** 初始化Y轴 */
            axisY = new Axis();
            axisY.setName("压力");//添加Y轴的名称
            axisY.setHasLines(true);//Y轴分割线
            axisY.setTextSize(15);//设置字体大小
//        axisY.setTextColor(Color.parseColor("#AFEEEE"));//设置Y轴颜色，默认浅灰色
            LineChartData lineChartData = new LineChartData();
            lineChartData.setAxisYLeft(axisY);//设置Y轴在左边

            /** 初始化X轴 */
            Axis axisX = new Axis();
            axisX.setHasTiltedLabels(false);//X坐标轴字体是斜的显示还是直的，true是斜的显示
//        axisX.setTextColor(Color.CYAN);//设置X轴颜色
            axisX.setName("位置");//X轴名称
            axisX.setHasLines(true);//X轴分割线
            axisX.setTextSize(15);//设置字体大小
            axisX.setMaxLabelChars(1);//设置0的话X轴坐标值就间隔为1
            List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
            for (int i = 0; i < 61; i++) {
                mAxisXValues.add(new AxisValue(i).setLabel(i+""));
            }
            axisX.setValues(mAxisXValues);//填充X轴的坐标名称
            lineChartData.setAxisXBottom(axisX);//X轴在底部

            lineChartView.setLineChartData(lineChartData);

            Viewport port = initViewPort(0,10);//初始化X轴10个间隔坐标
            lineChartView.setCurrentViewportWithAnimation(port);
            lineChartView.setInteractive(true);//设置不可交互
            lineChartView.setScrollEnabled(true);
            lineChartView.setValueTouchEnabled(false);
            lineChartView.setFocusableInTouchMode(false);
            lineChartView.setViewportCalculationEnabled(false);
            lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
            lineChartView.startDataAnimation();

//            loadData();//加载待显示数据
    }

    private static Viewport initViewPort(float left,float right) {
        Viewport port = new Viewport();
        port.top = 40;//Y轴上限，固定(不固定上下限的话，Y轴坐标值可自适应变化)
        port.bottom = 0;//Y轴下限，固定
        port.left = left;//X轴左边界，变化
        port.right = right;//X轴右边界，变化
        return port;
    }
    public static void loadData(ArrayList<PointValue> pointValueList,LineChartView lineChartView ) {

        //根据新的点的集合画出新的线
        Line line = new Line(pointValueList);
        line.setColor(Color.parseColor("#FFCD41"));//设置折线颜色
        line.setShape(ValueShape.CIRCLE);//设置折线图上数据点形状为 圆形 （共有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，true是平滑曲线，false是折线
        line.setHasLabels(true);//数据是否有标注
//        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据,设置了line.setHasLabels(true);之后点击无效
        line.setHasLines(true);//是否用线显示，如果为false则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 ，如果为false则没有原点只有点显示（每个数据点都是个大圆点）
        ArrayList linesList = new ArrayList<Line>();
        linesList.add(line);
        LineChartData lineChartData = new LineChartData(linesList);
        lineChartData.setAxisYLeft(axisY);//设置Y轴在左
        lineChartData.setAxisXBottom(axisX);//X轴在底部
       // axisX.setName("位置");//X轴名称
        lineChartView.setLineChartData(lineChartData);

        float xAxisValue = pointValueList.size();
        //根据点的横坐标实时变换X坐标轴的视图范围
        Viewport port;
        if (xAxisValue > 10) {
            port = initViewPort(xAxisValue - 10, xAxisValue);
        } else {
            port = initViewPort(0, 10);
        }
        lineChartView.setMaximumViewport(port);
        lineChartView.setCurrentViewport(port);

    }
}