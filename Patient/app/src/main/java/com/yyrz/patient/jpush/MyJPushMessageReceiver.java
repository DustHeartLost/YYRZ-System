package com.yyrz.patient.jpush;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yyrz.patient.MainActivity;
import com.yyrz.patient.R;
import com.yyrz.patient.common.dialog.BindFragment;
import com.yyrz.patient.common.http.RequestRepository;
import com.yyrz.patient.common.viewModel.CommonViewModel;

import java.util.Map;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class MyJPushMessageReceiver extends JPushMessageReceiver {
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        switch (jPushMessage.getSequence()){
            case 1:
                if(jPushMessage.getErrorCode() == 0){
                    Toast.makeText(context, "register success", Toast.LENGTH_SHORT).show();
                    CommonViewModel.getInstance().getLogin().postValue("201");
                }else if(jPushMessage.getErrorCode()==6017){
                    CommonViewModel.getInstance().getLogin().postValue("当前账号已失效，请重新更换账号");
                }
                else{
                    JPushInterface.setAlias(MainActivity.context,1, CommonViewModel.getInstance().getPaccount());
                    Toast.makeText(context, "register fail"+jPushMessage.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
                break;
            case 2://此处需要写删除别名之后的代码
                break;
            case 3:
                if(jPushMessage.getErrorCode()!=0)
                    JPushInterface.getAlias(MainActivity.context, 3);
                else if(jPushMessage.getAlias()!=null&&jPushMessage.getAlias().equals(CommonViewModel.getInstance().getPaccount())){
                    CommonViewModel.getInstance().getLogin().postValue("201");
                }
                else
                    JPushInterface.setAlias(MainActivity.context, 1, CommonViewModel.getInstance().getPaccount());
                break;
        }
    }

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        //Toast.makeText(context,"收到了自定义消息"+customMessage.message,Toast.LENGTH_SHORT).show();
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
//        if(now.getTime()-timestamp.getTime()>50000){
//            Log.d("我","丢弃一个数据包");
//            return;
//        }
        String controlInfo=map.get("control");
        switch (controlInfo){
            case CommonViewModel.CON_ASSESSMENT:this.assessmentControl(customMessage);break;
            case CommonViewModel.CON_TRAINING:this.trainingControl(customMessage);break;
            case CommonViewModel.REQUESTBIND:
                DialogFragment dialogFragment=new BindFragment(customMessage.message,map.get("daccount"),map.get("paccount"));
                dialogFragment.show(CommonViewModel.getInstance(null,null).getFragmentManager(), "missile");break;
            case CommonViewModel.TYPE_SENSORDATA:
                Gson gson = new Gson();
                JsonObject jsonObject=gson.fromJson(customMessage.message,JsonObject.class);
                commonViewModel.getSensor().postValue(gson.fromJson(customMessage.message,JsonObject.class));
        }

    }


    private void assessmentControl(CustomMessage customMessage){
        CommonViewModel commonViewModel=CommonViewModel.getInstance(null,null);
        Map<String,String> map=new Gson().fromJson(customMessage.extra,Map.class);
        String type=map.get("type");
        //TODO:根据指令跳转到相应页面my
        switch (type){
            case CommonViewModel.TYPE_NAVCONTROLLER:
                    jumpPager1(customMessage.message);
                    if(commonViewModel.getNavController().getCurrentDestination().getId()!=R.id.login)
                    RequestRepository.getInstance().confirmCurrentState(map.get("daccount"), Integer.valueOf(customMessage.message),map.get("timestamp"),CommonViewModel.TYPE_CONFIRMCONTROLLER);break;
            case CommonViewModel.TYPE_INSTRUCTIONS:
                    commonViewModel.getAssessmentInstructions().postValue(customMessage.message);break;
            case CommonViewModel.TYPE_DESTINATION:
                    jumpPager2(customMessage.message);
                    RequestRepository.getInstance().confirmCurrentState(map.get("daccount"), Integer.valueOf(customMessage.message),map.get("timestamp"),CommonViewModel.TYPE_CONFIRMCONTROLLER);break;

        }
    }

    private void jumpPager1(String destination){
        CommonViewModel commonViewModel=CommonViewModel.getInstance(null,null);
        NavController navController=commonViewModel.getNavController();
        switch (destination){
            case "01":commonViewModel.getIsNoActionBar().postValue(true);
                if(Integer.parseInt(destination)<1) return;
                navController.navigate(R.id.action_mainUI_to_alterLineTest);
                commonViewModel.getAssessmentInstructions().postValue("01_speech_1");break;
            case "02":navController.navigate(R.id.action_alterLineTest_to_visualTest);
                if(Integer.parseInt(destination)<2) return;
                commonViewModel.getAssessmentInstructions().postValue("02_speech_1");break;
            case "03":navController.navigate(R.id.action_visualTest_to_named);
                if(Integer.parseInt(destination)<3) return;
                commonViewModel.getAssessmentInstructions().postValue("03_speech_1");break;
            case "04":navController.navigate(R.id.action_named_to_memoryAssessment);
                if(Integer.parseInt(destination)<4) return;
                commonViewModel.getAssessmentInstructions().postValue("04_speech_1");break;
            case "05":navController.navigate(R.id.action_memoryAssessment_to_attention);
                if(Integer.parseInt(destination)<5) return;
                commonViewModel.getAssessmentInstructions().postValue("05_speech_1");break;
            case "06":navController.navigate(R.id.action_attention_to_fluentLanguage);
                if(Integer.parseInt(destination)<6) return;
                commonViewModel.getAssessmentInstructions().postValue("06_speech_1");break;
            case "07":navController.navigate(R.id.action_fluentLanguage_to_abstraction);
                if(Integer.parseInt(destination)<7) return;
                commonViewModel.getAssessmentInstructions().postValue("07_speech_1");break;
            case "08":navController.navigate(R.id.action_abstraction_to_memoryDelayTest);
                if(Integer.parseInt(destination)<8) return;
                commonViewModel.getAssessmentInstructions().postValue("08_speech_1");break;
            case "09":navController.navigate(R.id.action_memoryDelayTest_to_direction);
                if(Integer.parseInt(destination)<9) return;
                commonViewModel.getAssessmentInstructions().postValue("09_speech_1");break;
            case "10":commonViewModel.getIsNoActionBar().postValue(false);
                if(Integer.parseInt(destination)<10) return;
                CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech("评估结束",null);
                navController.popBackStack(R.id.mainUI,false);break;
        }
    }

    private void jumpPager2(String destination){
        CommonViewModel commonViewModel=CommonViewModel.getInstance(null,null);
        commonViewModel.getIsNoActionBar().postValue(true);
        NavController navController=commonViewModel.getNavController();
        switch (destination){
            case "01":commonViewModel.getIsNoActionBar().postValue(true);
                navController.navigate(R.id.alterLineTest);
                commonViewModel.getAssessmentInstructions().postValue("01_speech_1");break;
            case "02":navController.navigate(R.id.visualTest);
                commonViewModel.getAssessmentInstructions().postValue("02_speech_1");break;
            case "03":navController.navigate(R.id.named);
                commonViewModel.getAssessmentInstructions().postValue("03_speech_1");break;
            case "04":navController.navigate(R.id.memoryAssessment);
                commonViewModel.getAssessmentInstructions().postValue("04_speech_1");break;
            case "05":navController.navigate(R.id.attention);
                commonViewModel.getAssessmentInstructions().postValue("05_speech_1");break;
            case "06":navController.navigate(R.id.fluentLanguage);
                commonViewModel.getAssessmentInstructions().postValue("06_speech_1");break;
            case "07":navController.navigate(R.id.abstraction);
                commonViewModel.getAssessmentInstructions().postValue("07_speech_1");break;
            case "08":navController.navigate(R.id.memoryDelayTest);
                commonViewModel.getAssessmentInstructions().postValue("08_speech_1");break;
            case "09":navController.navigate(R.id.direction);
                commonViewModel.getAssessmentInstructions().postValue("09_speech_1");break;
            case "10":commonViewModel.getIsNoActionBar().postValue(false);
                CommonViewModel.getInstance(null,null).getMyTextToSpeech().speech("评估结束",null);
                navController.popBackStack(R.id.mainUI,false);break;
        }

    }

    private void trainingControl(CustomMessage customMessage){
        Map<String,String> map=new Gson().fromJson(customMessage.extra,Map.class);
        String type=map.get("type");
        switch (type){
            case CommonViewModel.TYPE_NAVCONTROLLER:
                RequestRepository.getInstance().confirmCurrentState(map.get("daccount"), Integer.valueOf(customMessage.message),map.get("timestamp"),CommonViewModel.TYPE_CONFIRMCONTROLLER);
                jumpPager4(customMessage.message);
                break;
            case CommonViewModel.TYPE_INSTRUCTIONS:
                jumpPager3(customMessage.message); break;
        }
    }

    private void jumpPager3(String destination){
        CommonViewModel commonViewModel=CommonViewModel.getInstance(null,null);
        commonViewModel.getIsNoActionBar().postValue(true);
        commonViewModel.getNavController().navigateUp();
        switch (destination.substring(0,2)){
            case "01": commonViewModel.getNavController().navigate(R.id.action_mainUI_to_instantMemory);break;
            case "02": commonViewModel.getNavController().navigate(R.id.action_mainUI_to_shortTerm);break;
            case "03": commonViewModel.getNavController().navigate(R.id.action_mainUI_to_longTerm);break;
        }
        commonViewModel.getTrainingInstructions().postValue(destination);
    }

    private void jumpPager4(String destination){
         switch (destination){
            case "20":break;
            case "21":
                CommonViewModel commonViewModel=CommonViewModel.getInstance(null,null);
                NavController navController=commonViewModel.getNavController();
                commonViewModel.getIsNoActionBar().postValue(false);
                commonViewModel.getMyTextToSpeech().speech("训练结束",null);
                navController.navigateUp();break;
        }
    }
}
