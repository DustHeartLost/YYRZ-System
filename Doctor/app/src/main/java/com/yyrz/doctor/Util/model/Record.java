package com.yyrz.doctor.Util.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;

public class Record implements Serializable {

    private int line;
    private int[]visual;
    private int[]name;
    private int[]memory;
    private int[]attention;
    private int[]language;
    private int[]abstraction;
    private int[]delayMemory;
    private int[]direction;
    private String language_record;
    private String daccount;
    private String paccount;
    private String photo;
    private int state;
    public Record() {
        line=0;
        visual=new int[4];
        name=new int[3];
        memory=new int[10];
        attention=new int[3];
        language=new int[3];
        abstraction=new int[2];
        delayMemory=new int[15];
        direction=new int[6];
        paccount=null;
        language_record=null;
        state=0;
        daccount=null;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int[] getVisual() {
        return visual;
    }

    public void setVisual(int[] visual) {
        this.visual = visual;
    }

    public int[] getName() {
        return name;
    }

    public void setName(int[] name) {
        this.name = name;
    }

    public int[] getMemory() {
        return memory;
    }

    public void setMemory(int[] memory) {
        this.memory = memory;
    }

    public int[] getAttention() {
        return attention;
    }

    public void setAttention(int[] attention) {
        this.attention = attention;
    }

    public int[] getLanguage() {
        return language;
    }

    public void setLanguage(int[] language) {
        this.language = language;
    }

    public int[] getAbstraction() {
        return abstraction;
    }

    public void setAbstraction(int[] abstraction) {
        this.abstraction = abstraction;
    }

    public int[] getDelayMemory() {
        return delayMemory;
    }

    public void setDelayMemory(int[] delayMemory) {
        this.delayMemory = delayMemory;
    }

    public int[] getDirection() {
        return direction;
    }

    public void setDirection(int[] direction) {
        this.direction = direction;
    }

    public String getDaccount() {
        return daccount;
    }

    public void setDaccount(String daccount) {
        this.daccount = daccount;
    }

    public String getPaccount() {
        return paccount;
    }

    public void setPaccount(String paccount) {
        this.paccount = paccount;
    }

    public String getLanguage_record() {
        return language_record;
    }

    public void setLanguage_record(String language_record) {
        this.language_record = language_record;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @NonNull
    @Override
    public String toString() {
        return Arrays.toString(abstraction);
    }
}
