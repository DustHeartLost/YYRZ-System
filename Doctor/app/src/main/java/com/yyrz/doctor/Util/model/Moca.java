package com.yyrz.doctor.Util.model;

import androidx.annotation.NonNull;

import com.yyrz.doctor.Util.util.BeanFieldAnnotation;

public class Moca {
    @BeanFieldAnnotation(order = 1)
    private int moca_id;

    @BeanFieldAnnotation(order = 2)
    private int alternateLineTest_Score;

    @BeanFieldAnnotation(order = 3)
    private int visualSpaceSkills_Score;

    @BeanFieldAnnotation(order = 4)
    private int named_Score;

    @BeanFieldAnnotation(order = 5)
    private int attention_Score;

    @BeanFieldAnnotation(order = 6)
    private int fluentLanguage_Score;

    @BeanFieldAnnotation(order = 7)
    private String fluentLanguage_record;

    @BeanFieldAnnotation(order = 8)
    private int abstract_Score;

    @BeanFieldAnnotation(order = 9)
    private int memory_Score;

    @BeanFieldAnnotation(order =10)
    private int directional_Score;

    @BeanFieldAnnotation(order = 11)
    private String assessmentRecords;

    @BeanFieldAnnotation(order = 12)
    private String daccount;

    @BeanFieldAnnotation(order = 13)
    private String paccount;

    @BeanFieldAnnotation(order = 14)
    private String date;

    public String getDaccount() {
        return daccount;
    }

    public void setDaccount(String daccount) {
        this.daccount = daccount;
    }

    @NonNull
    public String getPaccount() {
        return paccount;
    }

    public void setPaccount(@NonNull String paccount) {
        this.paccount = paccount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAlternateLineTest_Score() {
        return alternateLineTest_Score;
    }

    public void setAlternateLineTest_Score(int alternateLineTest_Score) {
        this.alternateLineTest_Score = alternateLineTest_Score;
    }

    public int getNamed_Score() {
        return named_Score;
    }

    public void setNamed_Score(int named_Score) {
        this.named_Score = named_Score;
    }

    public int getAttention_Score() {
        return attention_Score;
    }

    public void setAttention_Score(int attention_Score) {
        this.attention_Score = attention_Score;
    }

    public int getFluentLanguage_Score() {
        return fluentLanguage_Score;
    }

    public void setFluentLanguage_Score(int fluentLanguage_Score) {
        this.fluentLanguage_Score = fluentLanguage_Score;
    }

    public int getAbstract_Score() {
        return abstract_Score;
    }

    public void setAbstract_Score(int abstract_Score) {
        this.abstract_Score = abstract_Score;
    }

    public int getMemory_Score() {
        return memory_Score;
    }

    public void setMemory_Score(int memory_Score) {
        this.memory_Score = memory_Score;
    }

    public int getDirectional_Score() {
        return directional_Score;
    }

    public void setDirectional_Score(int directional_Score) {
        this.directional_Score = directional_Score;
    }

    public String getAssessmentRecords() {
        return assessmentRecords;
    }

    public void setAssessmentRecords(String assessmentRecords) {
        this.assessmentRecords = assessmentRecords;
    }

    public int getVisualSpaceSkills_Score() {
        return visualSpaceSkills_Score;
    }

    public void setVisualSpaceSkills_Score(int visualSpaceSkills_Score) {
        this.visualSpaceSkills_Score = visualSpaceSkills_Score;
    }

    public String getFluentLanguage_record() {
        return fluentLanguage_record;
    }

    public void setFluentLanguage_record(String fluentLanguage_record) {
        this.fluentLanguage_record = fluentLanguage_record;
    }

    public int getMoca_id() {
        return moca_id;
    }

    public void setMoca_id(Integer moca_id) {
        this.moca_id = moca_id;
    }

    public String[] getValues() {
        String[] temp=new String[8];
        temp[0]=String.valueOf(alternateLineTest_Score);
        temp[1]=String.valueOf(visualSpaceSkills_Score);
        temp[2]=String.valueOf(named_Score);
        temp[3]=String.valueOf(attention_Score);
        temp[4]=String.valueOf(fluentLanguage_Score);
        temp[5]=String.valueOf(abstract_Score);
        temp[6]=String.valueOf(memory_Score);
        temp[7]=String.valueOf(directional_Score);
        return temp;
    }
}
