package com.yyrz.doctor.jpush;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class MyJPushMessageReceiver extends JPushMessageReceiver {
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        /**
         * ErrorCode为 0，表示成功
         */
        switch (jPushMessage.getSequence()){
            case 1:
                if(jPushMessage.getErrorCode() == 0){
                    Toast.makeText(context, "register success", Toast.LENGTH_SHORT).show();
                    CommonViewModel.getInstance().getLogin().postValue("201");
                    CommonViewModel.getInstance().getJPushAlias().postValue(jPushMessage.getAlias());
                }else if(jPushMessage.getErrorCode()==6017){
                    CommonViewModel.getInstance().getLogin().postValue("当前账号已失效，请重新更换账号");
                }
                else{
                    JPushInterface.setAlias(CommonViewModel.getInstance().getContext(),1, CommonViewModel.getInstance().getAccount());
                    Toast.makeText(context, "register fail"+jPushMessage.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if(jPushMessage.getErrorCode()==0)
                    CommonViewModel.getInstance().getJPushAlias().postValue("已删除");
                break;
            case 3:
                if(jPushMessage.getErrorCode()!=0)
                    JPushInterface.getAlias(CommonViewModel.getInstance().getContext(), 3);
                else if(jPushMessage.getAlias()!=null&&jPushMessage.getAlias().equals(CommonViewModel.getInstance().getAccount())){
                    CommonViewModel.getInstance().getLogin().postValue("201");
                    CommonViewModel.getInstance().getJPushAlias().postValue(jPushMessage.getAlias());
                }
                else
                    JPushInterface.setAlias(CommonViewModel.getInstance().getContext(), 1, CommonViewModel.getInstance().getAccount());
                break;
        }
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
   public void onMessage(Context context, CustomMessage customMessage) {
        Toast.makeText(context,"收到了自定义消息"+customMessage.message,Toast.LENGTH_LONG).show();
        CommonViewModel commonViewModel=CommonViewModel.getInstance();
        Map<String,String> map=new Gson().fromJson(customMessage.extra,Map.class);
//        Date now=new Date(System.currentTimeMillis());
//        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date timestamp = null;
//        try {
//            timestamp =formatter.parse(map.get("timestamp"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if(now.getTime()-timestamp.getTime()>5000){
//            Log.d("我","丢弃一个数据包");
//            return;
//        }
        String type=map.get("type");
        switch (type){
            case CommonViewModel.TYPE_CONFIRMCONTROLLER:
                if(!customMessage.message.equals("10"))
                    commonViewModel.getCurrentState().postValue(Integer.valueOf(customMessage.message));break;
            case CommonViewModel.RESPONDBIND:
                CommonViewModel.getInstance().getCodeAndMsg().postValue("200:"+customMessage.message);break;

        }
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        //Log.e(TAG,"[onRegister] "+registrationId);
        Toast.makeText(context,"registrationId"+registrationId,Toast.LENGTH_LONG).show();
    }
}
