package com.yyrz.doctor.Util.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class PatientInformation {
    private String paccount;
    private String name;
    private String birthday;
    private String gender;
    private String department;
    private String bednumber;
    private String hosiptal;

    public String getHosiptal() {
        return hosiptal;
    }

    public void setHosiptal(String hosiptal) {
        this.hosiptal = hosiptal;
    }

    public String getAccount() {
        return paccount;
    }

    public String getName() {
        return name;
    }

    public void setAccount(String account) {
        this.paccount = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setBed_number(String bed_number) {
        this.bednumber = bed_number;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }

    public String getBed_number() {
        return bednumber;
    }

    public String getAge(){
        LocalDate date1 = LocalDate.of(Integer.valueOf(this.birthday.substring(0,4)),Integer.valueOf(birthday.substring(5,7)), Integer.valueOf(this.birthday.substring(8,10)));
        String now= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        LocalDate date2 = LocalDate.of(Integer.valueOf(now.substring(0,4)),Integer.valueOf(now.substring(5,7)), Integer.valueOf(now.substring(8,10)));
        int age = date1.until(date2).getYears();
        return String.valueOf(age);
    }
}
