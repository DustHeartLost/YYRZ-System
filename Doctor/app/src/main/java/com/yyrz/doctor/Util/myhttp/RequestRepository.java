package com.yyrz.doctor.Util.myhttp;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yyrz.doctor.Util.DataRestored.Repository;
import com.yyrz.doctor.Util.exception.ErrorCode;
import com.yyrz.doctor.Util.model.DoctorInformation;
import com.yyrz.doctor.Util.model.Moca;
import com.yyrz.doctor.Util.model.PatientInformation;
import com.yyrz.doctor.Util.model.Record;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;
import com.yyrz.doctor.ui.mainUI.patient.historyRecord.HistoryRecordView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RequestRepository {
    private MyHTTP myHTTP;
    private static RequestRepository instance;

    private RequestRepository(){}

    public static RequestRepository getInstance(){
        if (instance==null){
            instance=new RequestRepository();
            instance.myHTTP=new MyHTTP();
        }
        return instance;
    }

    public void currentState(final String alias, final String message, final String control, final String type, final String daccount){
        final String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what != 100) {
                    myHTTP.get("isMessageByAlias", "alias=" + alias + "&msgContent=" + message + "&control=" + control + "&type=" + type + "&daccount=" + daccount + "&timestamp=" + time, null, Boolean.class);
                    Log.d("我currentState",((ErrorCode)msg.obj).getMsg()+"  "+((ErrorCode)msg.obj).getCode());
                }
                return true;
            }
        });
       myHTTP.get("isMessageByAlias","alias="+alias+"&msgContent="+message+"&control="+control+"&type="+type+"&daccount="+daccount+"&timestamp="+time, handler,Boolean.class);
    }

    public void getBindPatient(String paccount){
        CommonViewModel.getInstance().setBindPatient(null);
        CommonViewModel.getInstance().getCodeAndMsg().postValue(null);
        Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==100){
                    CommonViewModel.getInstance().getCodeAndMsg().postValue("100:找到了");
                    CommonViewModel.getInstance().setBindPatient((HashMap) msg.obj);
                }else {
                    ErrorCode errorCode= (ErrorCode) msg.obj;
                    CommonViewModel.getInstance().getCodeAndMsg().postValue(errorCode.getMsg());
                }
              return false;
            }
        });
        new MyHTTP().get("patient","paccount="+paccount,handler, HashMap.class);
    }

    public void bind(final String daccount, final String paccount){
        final String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        CommonViewModel.getInstance().setBindPatient(null);
        CommonViewModel.getInstance().getCodeAndMsg().postValue(null);
        final Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what!=100){
                    new MyHTTP().get("bindPatient","paccount="+paccount+"&daccount="+daccount+"&timestamp="+time,null, Boolean.class);
                    Log.d("我bind",((ErrorCode)msg.obj).getMsg()+"  "+((ErrorCode)msg.obj).getCode());
                }
                return false;
            }
        });
        new MyHTTP().get("bindPatient","paccount="+paccount+"&daccount="+daccount+"&timestamp="+time,handler, Boolean.class);
    }

    public void noBind( String paccount, final int position){
        CommonViewModel.getInstance().getCodeAndMsg().postValue(null);
        Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==100){
                    CommonViewModel.getInstance().getCodeAndMsg().postValue("100:"+position);
                }else {
                    CommonViewModel.getInstance().getCodeAndMsg().postValue(((ErrorCode)msg.obj).getMsg());
                }
                return false;
            }
        });
        new MyHTTP().get("noBindPatient","paccount="+paccount,handler, Boolean.class);
    }

    public void  getChartDataFromNet(final String paccount){
        final Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==100) {
                    CommonViewModel.getInstance().getChartData().postValue((JsonArray) msg.obj);
                }else{
                    Log.d("我getChartDataFromNet",((ErrorCode)msg.obj).getMsg()+"  "+((ErrorCode)msg.obj).getCode());
                }
                return true;
            }
        });
        new MyHTTP().get("summary","paccount="+paccount,handler, JsonArray.class);
    }

    public void getHistoryRecordFromNet(String paccount,int count){
        Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==100){
                    ArrayList<Moca>temp=CommonViewModel.getInstance().getHistoryRecord().getValue();
                    if(temp==null)temp=new ArrayList<>();
                    HistoryRecordView.dataLength[0]=temp.size();
                    ArrayList<Moca>data=(ArrayList<Moca>)msg.obj;
                    HistoryRecordView.dataLength[1]=data.size();
                    temp.addAll(data);
                    CommonViewModel.getInstance().getHistoryRecord().postValue(temp);
                }else{
                    Log.d("我getHistoryRecordFromNet",((ErrorCode)msg.obj).getMsg()+"  "+((ErrorCode)msg.obj).getCode());
                }
                return true;
            }
        });
        new MyHTTP().get("doctor/mocaHistory","paccount="+paccount+"&count="+count,handler, ArrayList.class);
    }

    public void getPhotoFromNet(String photoname){
        Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==100){
                    Map<String,String> map=(Map<String,String>) msg.obj;
                    String data=map.get("photo");
                    try {
                        data = URLDecoder.decode(data, "utf-8");
                        java.util.Base64.Decoder decoder = Base64.getDecoder();
                        byte[] imageByte = decoder.decode(data);
                        CommonViewModel.getInstance().getBitmap().postValue(BitmapFactory.decodeByteArray(imageByte,0,imageByte.length));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.d("我getChartDataFromNet",((ErrorCode)msg.obj).getMsg()+"  "+((ErrorCode)msg.obj).getCode());
                }
                return true;
            }
        });
        new MyHTTP().get("doctor/photo","photoName="+photoname,handler, Map.class);
    }

    public void getPatientInformationFromNet(String daccount){
        System.out.println("我"+daccount);
        Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==100){
                    CommonViewModel.getInstance().getPatientInformation().setValue((ArrayList<PatientInformation>) msg.obj);
                }else {
                    Log.d("我getPatientInformationFromNet",((ErrorCode)msg.obj).getMsg()+"  "+((ErrorCode)msg.obj).getCode());
                }
                return true;
            }
        });
        new MyHTTP().get("doctor/patient","daccount="+daccount,handler, ArrayList.class);
    }

    public void getDoctorInformation() {
        Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==100){
                    CommonViewModel.getInstance().getDoctorInformation().postValue((DoctorInformation) msg.obj);
                }else {
                    Log.d("我getDoctorInformation",((ErrorCode)msg.obj).getMsg()+"  "+((ErrorCode)msg.obj).getCode());
                }
                return true;
            }
        });
        new MyHTTP().get("doctor/daccount","daccount="+CommonViewModel.getInstance().getAccount(),handler, DoctorInformation.class);
    }

    public void postMoca() {
        final Moca moca=new Moca();
        Record record=CommonViewModel.getInstance().getRecord();
        moca.setAlternateLineTest_Score(record.getLine() == 1 ? 1 : 0);
        for (int i : record.getVisual()) {
            int j = moca.getVisualSpaceSkills_Score() + i;
            moca.setVisualSpaceSkills_Score(j);
        }
        for (int i : record.getName()) {
            int j = moca.getNamed_Score() + i;
            moca.setNamed_Score(j);
        }
        for (int i = 0; i < 3; i++) {
            int j = record.getAttention()[i];
            moca.setAttention_Score(j + moca.getAttention_Score());
        }
        for (int i : record.getLanguage()) {
            int j = moca.getFluentLanguage_Score() + i;
            moca.setFluentLanguage_Score(j);
        }
        moca.setFluentLanguage_record(record.getLanguage_record());
        for (int i : record.getAbstraction()) {
            int j = moca.getAbstract_Score() + i;
            moca.setAbstract_Score(j);
        }
        for (int i = 0; i < 5; i++) {
            int j = record.getDelayMemory()[i] + moca.getMemory_Score();
            moca.setMemory_Score(j);
        }
        for (int i : record.getDirection()) {
            int j = i + moca.getDirectional_Score();
            moca.setDirectional_Score(j);
        }
        moca.setAssessmentRecords(record.getPhoto());
        moca.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis())));
        moca.setPaccount(record.getPaccount());
        moca.setDaccount(record.getDaccount());
        Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==100){
                    CommonViewModel.getInstance().getCodeAndMsg().setValue("200:");
                    Repository.getInstance().deleteRecord(moca.getPaccount());
                    CommonViewModel.getInstance().setMoca(null);
                }else {
                    CommonViewModel.getInstance().getCodeAndMsg().setValue("400:");
                    Log.d("我postMoca",((ErrorCode)msg.obj).getMsg()+"  "+((ErrorCode)msg.obj).getCode());
                    CommonViewModel.getInstance().setMoca(moca);
                }
                return true;
            }
        });
        new MyHTTP().post("moca", handler, moca);
    }

    public void postDataAgain(){
        final Moca moca=CommonViewModel.getInstance().getMoca();
        Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 100) {
                    CommonViewModel.getInstance().getCodeAndMsg().setValue("200:");
                    Repository.getInstance().deleteRecord(moca.getPaccount());
                    CommonViewModel.getInstance().setMoca(null);
                } else {
                    CommonViewModel.getInstance().getCodeAndMsg().setValue("400:");
                    Log.d("我postMoca", ((ErrorCode) msg.obj).getMsg() + "  " + ((ErrorCode) msg.obj).getCode());
                    CommonViewModel.getInstance().setMoca(moca);
                }
                return true;
            }
        });
        new MyHTTP().post("moca", handler,moca);
    }

    public void login(String account,String password){
        System.out.println("我"+account+" "+password);
        Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==100){
                    CommonViewModel.getInstance().getLogin().setValue("200"+msg.obj.toString());
                }else {
                    CommonViewModel.getInstance().getLogin().postValue((((ErrorCode)msg.obj).getMsg()));
                    Log.d("我login",((ErrorCode)msg.obj).getMsg()+"  "+((ErrorCode)msg.obj).getCode());
                }
                return true;
            }
        });
        new MyHTTP().get("login","daccount="+account+"&password="+password,handler, String.class);
    }
}
