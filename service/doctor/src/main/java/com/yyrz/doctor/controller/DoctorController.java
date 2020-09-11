package com.yyrz.doctor.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yyrz.common.myException.MyException;
import com.yyrz.doctor.service.DoctorDatabaseService;
import com.yyrz.doctor.service.DoctorPushService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@RestController
public class DoctorController {
    //这个路径是张熙业电脑对用的路径
    private String photoPath="Z:\\MySoftware\\MySQL\\yyrzs_photo\\";
    //这个电脑是郭容利电脑对应的路径
    //private String photoPath="C:\\Users\\GRL\\Desktop\\github\\YYRZ-System-master\\res\\photos\\";
    @Autowired
    DoctorPushService doctorPushService;
    @Autowired
    DoctorDatabaseService doctorDatabaseService;

    //查询医生的个人信息
    @GetMapping(value = "doctor/daccount")
    public Map<String, Object> getDoctor(@RequestParam String daccount)throws MyException{
        String sql = "select * from doctor where daccount='" + daccount + "';";
        ArrayList<Map<String, Object>> list;
        list = doctorDatabaseService.query(sql);
        Map<String, Object> map = list.get(0);
        map.remove("password");
        return map;
    }

    //查询与医生绑定的病人
    @GetMapping(value = "doctor/patient")
    public List<Map<String, Object>> getPatient(@RequestParam String daccount)throws MyException{
        String sql = "select paccount,name,birthday,gender,department,bednumber,hosiptal" +
                " from patient" +
                " where daccount='" + daccount + "';";
        ArrayList<Map<String, Object>> list;
        list = doctorDatabaseService.query(sql);
        return list;
    }

    //得到各项的数据统计
    @GetMapping(value = "summary")
    public String getSummary(@RequestParam String paccount) {
        String[] field = {"alternateLineTest_Score", "visualSpaceSkills_Score", "named_Score", "attention_Score", "fluentLanguage_Score", "abstract_Score", "memory_Score", "directional_Score"};
        ArrayList<Map<Integer, Float>> result = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            String sql = "select " + field[i] + " as result,count(*) as number from moca where paccount='" + paccount + "' group by " + field[i] + " order by " + field[i] + ";";
            ArrayList<Map<String, Object>> temp;
            temp = doctorDatabaseService.query(sql);
            //            double sum = 0;
//            for (Map<String, Object> map : temp) {
//                sum += (Integer) map.get("number");
//            }
            Map map = new HashMap<Integer, Float>();
            for (int j = 0; j < temp.size(); j++) {
//                DecimalFormat df=new DecimalFormat("0.00");
//                df.format((int)temp.get(j).get("number")/sum)
                map.put(temp.get(j).get("result"), temp.get(j).get("number"));
            }
            result.add(map);
        }
        return JSON.toJSONString(result);
    }

    //通过别名发送通知
    @GetMapping(value = "isNotificationByAlias")
    public boolean sendNotificationByAlias(@RequestParam String alias, @RequestParam String notificationTitle, @RequestParam String notificationMessage) {
        boolean flag;
        flag = doctorPushService.sendNotificationByAlias(alias, notificationTitle, notificationMessage);
        return flag;
    }

    //通过别名发送消息
    @GetMapping(value = "isMessageByAlias")
    public boolean sendMessageByAlias(@RequestParam String alias, @RequestParam String msgContent,@RequestParam String control,@RequestParam String type,@RequestParam String daccount,@RequestParam String timestamp) {
        Map<String,String> map=new HashMap();
        map.put("control",control);
        map.put("type",type);
        map.put("daccount",daccount);
        map.put("timestamp",timestamp);
        boolean flag;
        flag = doctorPushService.sendMessageByAlias(alias, msgContent,map);
        return flag;
    }

    //通过别名发送通知和消息
    @GetMapping(value = "isNotificationAndMessageByAlias")
    public boolean sendNotificationAndMessageByAlias(@RequestParam String alias, @RequestParam String notificationTitle, @RequestParam String notificationMessage, @RequestParam String msgContent) {
        boolean flag;
        flag = doctorPushService.sendNotificationAndMessageByAlias(alias, notificationTitle, notificationMessage, msgContent);
        return flag;
    }

    //医生通过账号发送创建一个MOCA值
    @PostMapping(value = "moca")
    public boolean postMoca(@RequestBody Map<String, Object> map) throws MyException{
        boolean flag = false;
        String photo = map.get("assessmentRecords").toString();
        map.remove("moca_id");
        if (!photo.equals("null")) {
            try {
                photo = URLDecoder.decode(photo, "utf-8");
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] result = decoder.decode(photo);
                String photoname = map.get("paccount").toString() + map.get("date").toString().replace(':', '-').replace(' ', '_');
                File file = new File(photoPath+ photoname + ".bmp");
                file.createNewFile();
                FileImageOutputStream imageOutput = new FileImageOutputStream(file);
                imageOutput.write(result, 0, result.length);
                imageOutput.close();
                File image = new File(photoPath + photoname + ".bmp");
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Thumbnails.of(image).size(100, 100).toOutputStream(out);
                result = out.toByteArray();
                out.close();
                Base64.Encoder encoder = Base64.getEncoder();
                String temp = encoder.encodeToString(result);
                temp = URLEncoder.encode(temp, "utf-8");
                map.replace("assessmentRecords", temp);
            } catch (IOException e) {
                throw new MyException(3001, e.getMessage());
            }
            flag = doctorDatabaseService.postMoca(map);
        }
        return flag;
    }

    @GetMapping(value = "doctor/mocaHistory")
    public List<Map<String, Object>> getHistory(@RequestParam String paccount,@RequestParam int count) throws MyException{
        String sql = "select * from moca where paccount='" + paccount + "' order by moca_id desc limit "+(count*3)+","+((count+1)*3)+";";
        List<Map<String, Object>> list;
        list = doctorDatabaseService.query(sql);
        return list;
    }

    @GetMapping(value = "doctor/photo")
    public Map<String,Object> getPhoto(@RequestParam String photoName) throws MyException{
        File image=new File(photoPath+photoName+".bmp");
        Map<String,Object> photo=new HashMap<>();
        if(image.exists()){
            try {
                FileInputStream in=new FileInputStream(image);
                byte []data=new byte[in.available()];
                in.read(data);
                in.close();
                Base64.Encoder encoder = Base64.getEncoder();
                String temp = encoder.encodeToString(data);
                temp = URLEncoder.encode(temp, "utf-8");
                photo.put("photo",temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            throw new MyException(3002,"没有这张照片");
        }
        return photo;
    }

    @GetMapping(value = "patient")
    public Map<String,Object>getBindPatient(@RequestParam String paccount)throws MyException{
        String sql = "select paccount,name,birthday,gender,department,bednumber,hosiptal from patient where paccount='" + paccount + "';";
        ArrayList<Map<String, Object>> list;
        list = doctorDatabaseService.query(sql);
        if(list.size()==0){
            throw new MyException(3004,"没有这个人的信息");
        }
        return list.get(0);
    };

    @GetMapping(value = "noBindPatient")
    public boolean noBind(@RequestParam String paccount){
        String sql = " update patient set daccount=null where paccount='"+paccount+"';";
        doctorDatabaseService.update(sql);
        return true;
    }

    @GetMapping(value = "bindPatient")
    public boolean noBind(@RequestParam String paccount,@RequestParam String daccount,@RequestParam String timestamp)throws MyException{
        String sql="select name from doctor where daccount='"+daccount+"';";
        String name=doctorDatabaseService.query(sql).get(0).get("name").toString();
        HashMap<String,String>map=new HashMap<>();
        map.put("daccount",daccount);
        map.put("paccount",paccount);
        map.put("control","requestBind");
        map.put("timestamp",timestamp);
        doctorPushService.sendMessageByAlias(paccount,"账户为  "+daccount+"   的    "+name+"    医生请求绑定，是否同意?请注意，若您已有医生，同意则会自动为您替换。",map);
        return true;
    }

    @GetMapping(value = "login")
    public String login(@RequestParam String daccount,@RequestParam String password)throws MyException{
        String sql="select password from doctor where daccount='"+daccount+"';";
        ArrayList<Map<String ,Object>> arrayList=doctorDatabaseService.query(sql);
        if(arrayList.size()==0) throw new MyException(3001,"账号密码输入错误");
        if(arrayList.get(0).get("password").toString().equals(password)){
            return daccount;
        }else{
            throw new MyException(3001,"账号密码输入错误");
        }
    }

    @PostMapping(value = "data")
    public String dataFromSensor(@RequestBody JSONObject  dataSensor)throws MyException{
        HashMap<String,String>map=new HashMap<>();
        map.put("control","sensorData");
        JSONObject data=dataSensor.getJSONObject("data");
        //TODO:此处需要将数据加入数据库
        System.out.println(dataSensor.toJSONString());
        if(doctorPushService.sendMessageByAlias(dataSensor.getString("alias"),data.toJSONString(),map)){
            return "金雪莹小姐姐，送你一朵小花花！数据传输OK";
        }
        else{
           throw new MyException(3005,"金雪莹小姐姐，你的推送失败了，加油加油奥里给!");
       }
    }
}
