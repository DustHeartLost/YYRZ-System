package com.yyrz.patient.common.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import com.yyrz.patient.common.myException.ErrorCode;
import com.yyrz.patient.common.viewModel.CommonViewModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class RequestRepository {
    private MyHTTP myHTTP;
    private static RequestRepository instance;
    private Handler handler;
    private  RequestRepository(){}

    public static RequestRepository getInstance(){
        if (instance==null){
            instance=new RequestRepository();
            instance.myHTTP=new MyHTTP();
        }
        return instance;
    }

    public void confirmCurrentState(final String alias,final Integer message,final String time, final String type){
        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what!=100){
                    myHTTP.get("isMessageByAlias","alias="+alias+"&msgContent="+message+"&type="+type+"&timestamp="+time,null,Boolean.class);}
                return true;
            }
        });
        myHTTP.get("isMessageByAlias","alias="+alias+"&msgContent="+message+"&type="+type+"&timestamp="+time,handler,Boolean.class);
    }

    public void getInformation(final String paccount){
        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==100) CommonViewModel.getInstance(null,null).getPatientInfo().postValue((HashMap) msg.obj);
                else Log.d("我getInformation",((ErrorCode)msg.obj).getMsg()+"  "+((ErrorCode)msg.obj).getCode());
                return true;
            }
        });
         myHTTP.get("patientInfo","paccount="+paccount,handler, HashMap.class);
    }

    public void bind(final Boolean agree, final String daccount, final String paccount){
        final String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        final Handler handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what!=100) {
                    Log.d("我bind",((ErrorCode)msg.obj).getMsg()+"  "+((ErrorCode)msg.obj).getCode());
                }
                return false;
            };
        });
        new MyHTTP().get("bindResponse","agree="+agree+"&paccount="+paccount+"&daccount="+daccount+"&time="+time,handler, Boolean.class);

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
        new MyHTTP().get("login","paccount="+account+"&password="+password,handler,String.class);
    }
}