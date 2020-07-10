package com.yyrz.doctor.Util.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.gson.JsonArray;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.model.DoctorInformation;
import com.yyrz.doctor.Util.model.Moca;
import com.yyrz.doctor.Util.model.PatientInformation;
import com.yyrz.doctor.Util.model.Record;

import java.util.ArrayList;
import java.util.HashMap;

public class CommonViewModel extends ViewModel {
    //常量区
    public static final String CON_ASSESSMENT="assessment";
    public static final String CON_TRAINING="training";
    public static final String TYPE_NAVCONTROLLER="navController";
    public static final String TYPE_INSTRUCTIONS="instructions";
    public static final String TYPE_CONFIRMCONTROLLER="confirmController";
    public static final String TYPE_DESTINATION="destination";
    public static final String REQUESTBIND="requestBind";
    public static final String RESPONDBIND="respondBind";
    public static final String TYPE_SENSORDATA="sensorData";
    //个人账号
    private String account;
    private String currentPaccount;

    //公共变量区
    private Context context;//Integer值为0是表示为不在任何状态,1表示在评估阶段，2表示在训练阶段。
    private int []hasRead;
    private Record record;
    private Moca moca;
    @SuppressLint("StaticFieldLeak")
    private static CommonViewModel instance;
    private NavController navController;
    private Fragment fragment;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private HashMap<String,String> bindPatient;
    private RapidFloatingActionButton floatBottom;
    private boolean isFirstEnter;

    //LivaData区
    private MutableLiveData<Bitmap> bitmap;//记录图片
    private MutableLiveData<String>codeAndMsg;
    private MutableLiveData<Integer> isQueryEnd;//监视的值为0表示初始状态，1表示查询结束并且有记录，2表示没有记录
    private MutableLiveData<JsonArray> chartData;
    private MutableLiveData<Integer> currentState;
    private MutableLiveData<ArrayList<Moca>> historyRecord;
    private MutableLiveData<Integer>isImageBackgroundExist;//0表示toolbar存在，1表示toolbar消失
    private MutableLiveData<DoctorInformation> doctorInformation;
    private MutableLiveData<ArrayList<PatientInformation>> patientInformation;
    private MutableLiveData<String>login;
    private MutableLiveData<String>JPushAlias;

    private CommonViewModel(){}

    public static CommonViewModel getInstance(Activity activity,Context context){
        if(instance==null){
            instance=new CommonViewModel();
            instance.isQueryEnd=new MutableLiveData<>();
            instance.isQueryEnd.setValue(0);
            instance.currentPaccount=null;
            instance.login=new MutableLiveData<>();
            instance.patientInformation=new MutableLiveData<>();
            instance.patientInformation.setValue(new ArrayList<PatientInformation>());
            instance.codeAndMsg=new MutableLiveData<>();
            instance.bitmap=new MutableLiveData<>();
            instance.chartData=new MutableLiveData<>();
            instance.currentState=new MutableLiveData<>();
            instance.historyRecord=new MutableLiveData<>();
            instance.isImageBackgroundExist=new MutableLiveData<>();
            instance.currentState.setValue(0);
            instance.context=context;
            instance.isFirstEnter=false;
            instance.record=new Record();
            instance.hasRead=new int[9];
            instance.navController= Navigation.findNavController(activity, R.id.nav_host_fragment);
            instance.JPushAlias=new MutableLiveData<>();
        }
        return instance;
    }
    public static CommonViewModel getInstance(){
        return instance;
    }

    public void clear() {
        instance.record=new Record();
        instance.hasRead=new int[9];
        instance.bitmap.setValue(null);
        instance.isQueryEnd.setValue(0);
        instance.currentState.setValue(0);
        instance.currentPaccount=null;
    }
    public void setMoca(Moca moca) {
        this.moca = moca;
    }
    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
    public void setRecord(Record record) {
        this.record = record;
    }
    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }
    public void setCurrentPaccount(String currentPaccount) {
        this.currentPaccount = currentPaccount;
    }
    public void setBindPatient(HashMap<String, String> bindPatient) {
        this.bindPatient = bindPatient;
    }
    public void setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
    }
    public Fragment getFragment() {
        return fragment;
    }
    public NavController getNavController() {
        return navController;
    }
    public String getAccount() {
        return account;
    }
    public Record getRecord() {
        return record;
    }
    public Context getContext() {
        return context;
    }
    public Toolbar getToolbar() {
        return toolbar;
    }
    public String getCurrentPaccount() {
        return currentPaccount;
    }
    public MutableLiveData<Integer> getIsQueryEnd() {
        return isQueryEnd;
    }
    public MutableLiveData<Integer> getCurrentState() {
        return currentState;
    }
    public MutableLiveData<JsonArray> getChartData() {
        return chartData;
    }
    public MutableLiveData<DoctorInformation> getDoctorInformation() {
        if(instance.doctorInformation==null){
            instance.doctorInformation=new MutableLiveData<>();
            instance.doctorInformation.setValue(new DoctorInformation());
        }
        return instance.doctorInformation;
    }
    public MutableLiveData<ArrayList<Moca>> getHistoryRecord() {
        return historyRecord;
    }
    public MutableLiveData<ArrayList<PatientInformation>> getPatientInformation() {
        return instance.patientInformation; }
    public MutableLiveData<String> getCodeAndMsg() {
        return codeAndMsg;
    }
    public MutableLiveData<String> getLogin() {
        return login;
    }
    public HashMap getBindPatient() {
        return bindPatient;
    }
    public MutableLiveData<Bitmap> getBitmap() {
        return bitmap;
    }
    public MutableLiveData<Integer> getIsImageBackgroundExist() {
        return isImageBackgroundExist;
    }
    public Moca getMoca() {
        return moca;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public boolean getIsFirstEnter() {
        return isFirstEnter;
    }
    public void setIsFirstEnter(boolean firstEnter) {
        isFirstEnter = firstEnter;
    }
    public ActionBar getActionBar() {
        return actionBar;
    }
    public int[] getHasRead() {
        return hasRead;
    }
    public RapidFloatingActionButton getFloatBottom() {
        return floatBottom;
    }
    public void setFloatBottom(RapidFloatingActionButton floatBottom) {
        this.floatBottom = floatBottom;
    }
    public MutableLiveData<String> getJPushAlias() {
        return JPushAlias;
    }
}
