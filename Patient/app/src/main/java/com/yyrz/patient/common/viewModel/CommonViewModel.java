package com.yyrz.patient.common.viewModel;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.yyrz.patient.R;
import com.yyrz.patient.common.util.MyTextToSpeech;
import com.yyrz.patient.common.util.Tts;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommonViewModel extends ViewModel {
    private static  CommonViewModel ourInstance;
    private  String paccount;
    //共用的变量区
    private MyTextToSpeech myTextToSpeech;
    private NavController navController;
    private Tts mTts;
    private FragmentManager fragmentManager;
    private boolean isFirstEnter;

    //LiveData区
    private MutableLiveData<Boolean> isNoActionBar;       //true表示没有，false表示有
    private MutableLiveData<String> assessmentInstructions;
    private MutableLiveData<String> trainingInstructions;
    private MutableLiveData<Integer>isSpeechFinished;    //0不表示任何意义，其他根据各部分而定
    private MutableLiveData<HashMap<String,String>>patientInfo;
    private MutableLiveData<String>login;

    //常量区
    public static final String CON_ASSESSMENT="assessment";
    public static final String CON_TRAINING="training";
    public static final String TYPE_NAVCONTROLLER="navController";
    public static final String TYPE_INSTRUCTIONS="instructions";
    public static final String TYPE_CONFIRMCONTROLLER="confirmController";
    public static final String TYPE_DESTINATION="destination";
    public static final String REQUESTBIND="requestBind";
    public static final String RESPONDBIND="respondBind";

    public static CommonViewModel getInstance(Activity activity, Context context) {
        if(ourInstance==null){
            ourInstance= new CommonViewModel();
            ourInstance.navController= Navigation.findNavController(activity, R.id.nav_host_fragment);
            ourInstance.myTextToSpeech=MyTextToSpeech.getInstance(context);
            ourInstance.isNoActionBar =new MutableLiveData<>();
            ourInstance.assessmentInstructions =new MutableLiveData<>();
            ourInstance.isSpeechFinished=new MutableLiveData<>();
            ourInstance.trainingInstructions=new MutableLiveData<>();
            ourInstance.patientInfo= new MutableLiveData<>();
            ourInstance.isSpeechFinished.setValue(0);
            ourInstance.mTts=Tts.getInstance(context);
            ourInstance.isFirstEnter=false;
            ourInstance.login=new MutableLiveData<>();
        }
        return ourInstance;
    }
    public static CommonViewModel getInstance() {
        return ourInstance;
    }
    private CommonViewModel() {}

    public NavController getNavController() {
        return navController;
    }
    public Tts getMyTextToSpeech() {
        return mTts;
    }
    public MutableLiveData<Boolean> getIsNoActionBar() {
        return isNoActionBar;
    }
    public MutableLiveData<String> getAssessmentInstructions() {
        return assessmentInstructions;
    }
    public MutableLiveData<Integer> getIsSpeechFinished() {
        return isSpeechFinished;
    }
    public MutableLiveData<String> getTrainingInstructions() {
        return trainingInstructions;
    }
    public MutableLiveData<HashMap<String,String>> getPatientInfo() {
        return patientInfo;
    }
    public String getPaccount() {
        return paccount;
    }
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
    public boolean getIsFirstEnter() {
        return isFirstEnter;
    }
    public void setIsFirstEnter(boolean firstEnter) {
        isFirstEnter = firstEnter;
    }
    public MutableLiveData<String> getLogin() {
        return login;
    }
    public void setPaccount(String paccount) {
        this.paccount = paccount;
    }
}
