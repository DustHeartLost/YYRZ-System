package com.yyrz.patient.model;

import java.util.Date;

public class PatientVo {

    private String paccount;

    private String password;

    private String name;

    private Date birthday;

    private String gender;

    private String department;
    private String bednumber;
    private String hosiptal;
    private String faccount;
    private String daccount;
    private String pressure;

    private String prelocation;

    public PatientVo(String paccount, String password, String name, Date birthday, String gender, String department, String bednumber, String hosiptal, String faccount, String daccount, String pressure, String prelocation) {
        this.paccount = paccount;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.department = department;
        this.bednumber = bednumber;
        this.hosiptal = hosiptal;
        this.faccount = faccount;
        this.daccount = daccount;
        this.pressure = pressure;
        this.prelocation = prelocation;
    }

    public String getPaccount() {
        return paccount;
    }

    public void setPaccount(String paccount) {
        this.paccount = paccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBednumber() {
        return bednumber;
    }

    public void setBednumber(String bednumber) {
        this.bednumber = bednumber;
    }

    public String getHosiptal() {
        return hosiptal;
    }

    public void setHosiptal(String hosiptal) {
        this.hosiptal = hosiptal;
    }

    public String getFaccount() {
        return faccount;
    }

    public void setFaccount(String faccount) {
        this.faccount = faccount;
    }

    public String getDaccount() {
        return daccount;
    }

    public void setDaccount(String daccount) {
        this.daccount = daccount;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getPrelocation() {
        return prelocation;
    }

    public void setPrelocation(String prelocation) {
        this.prelocation = prelocation;
    }
}
