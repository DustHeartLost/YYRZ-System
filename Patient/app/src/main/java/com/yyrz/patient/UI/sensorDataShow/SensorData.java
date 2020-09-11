package com.yyrz.patient.UI.sensorDataShow;

public class SensorData {
    /**
     * 数据点
     */
    private String press;
    /**
     * 时间
     */
    private String position;


    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public SensorData(String press, String position) {
        this.press = press;
        this.position = position;
    }
}
