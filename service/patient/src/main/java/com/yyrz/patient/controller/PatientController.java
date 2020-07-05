package com.yyrz.patient.controller;

import com.yyrz.common.myException.MyException;
import com.yyrz.patient.service.PatientDatabaseService;
import com.yyrz.patient.service.PatientPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PatientController {
    @Autowired
    PatientPushService patientPushService;
    @Autowired
    PatientDatabaseService patientDatabaseService;

    @GetMapping(value = "isNotificationByAlias")
    public boolean sendNotificationByAlias(@RequestParam String alias, @RequestParam String notificationTitle,@RequestParam String notificationMessage){
        return patientPushService.sendNotificationByAlias(alias,notificationTitle,notificationMessage);
    }
    @GetMapping(value = "isMessageByAlias")
    public boolean sendMessageByAlias(@RequestParam String alias,@RequestParam  String msgContent,@RequestParam String type,@RequestParam String timestamp){
        Map<String,String> map=new HashMap<>();
        map.put("type",type);
        map.put("timestamp",timestamp);
        return patientPushService.sendMessageByAlias(alias,msgContent,map);
    }

    @GetMapping(value = "isNotificationAndMessageByAlias")
    public boolean sendNotificationAndMessageByAlias(@RequestParam String alias,@RequestParam  String notificationTitle,@RequestParam  String notificationMessage,@RequestParam String msgContent ){
        return patientPushService.sendNotificationAndMessageByAlias(alias,notificationTitle,notificationMessage,msgContent);
    }

    @GetMapping(value = "patientInfo")
    public Map<String, Object> getDoctor(@RequestParam String paccount) {
        String sql = "select * from patient where paccount='" + paccount + "';";
        ArrayList<Map<String, Object>> list;
        list = patientDatabaseService.query(sql);
        Map<String, Object> map = list.get(0);
        map.remove("password");
        return map;
    }

    @GetMapping(value = "bindResponse")
    public boolean bindResponse(@RequestParam boolean agree,@RequestParam String paccount,@RequestParam String daccount,@RequestParam String time){
        HashMap<String,String>map=new HashMap<>();
        map.put("timestamp",time);
        map.put("type","respondBind");
        if(agree){
            patientPushService.sendMessageByAlias(daccount,"对方同意了你的请求",map);
            String sql = " update patient set daccount='"+daccount+"' where paccount='"+paccount+"';";
            patientDatabaseService.update(sql);
        }else {
            patientPushService.sendMessageByAlias(daccount,"对方拒绝了你的请求",map);
        }
        return true;
    }

    @GetMapping(value = "login")
    public String login(@RequestParam String paccount,@RequestParam String password)throws MyException{
        String sql="select password from patient where paccount='"+paccount+"';";
        ArrayList<Map<String ,Object>> arrayList=patientDatabaseService.query(sql);
        if(arrayList.size()==0) throw new MyException(3001,"账号密码输入错误");
        if(arrayList.get(0).get("password").toString().equals(password)){
            return paccount;
        }else{
            throw new MyException(3001,"账号密码输入错误");
        }
    }
}
