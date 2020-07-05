package com.yyrz.push.service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.yyrz.common.myException.MyException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PushService {

    final static private String APP_KEY = "7214a851e7d38f914179bb62";
    final static private String MASTER_SECRET = "b9ae024e3d060bcdf85fefed";

    /**
     * 推送通知：根据alias推送给个人
     * @param alias
     * @param notificationTitle
     * @param notificationMessage
     * @return
     */
    public boolean sendNotificationByAlias(String alias,String notificationTitle, String notificationMessage)throws MyException{
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()//指定不同平台的推送内容
                                .setTitle(notificationTitle)
                                .setAlert(notificationMessage)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)//APNs是否为生产环境，false为开发环境
                        .setTimeToLive(60)//指定本推送的离线保存时长(单位：秒)，如果不传此字段则默认保存一天，最多指定保留十天
                        .build())
                .build();
        PushResult pushResult=null;
        try {
            pushResult =jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            throw new MyException(1001,e.getMessage());
        } catch (APIRequestException e) {
            throw new MyException(1002,e.getMessage());
        }
        return pushResult.isResultOK()?true:false;
    }

    /**
     * 推送自定义消息：根据alias推给个人
     * @param alias
     * @param msgContent
     * @return
     */
    public boolean sendMessageByAlias(String alias, String msgContent, Map<String,String> extra)throws MyException{
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .setContentType("json")
                        .addExtras(extra)
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)//APNs是否为生产环境，false为开发环境
                        .setTimeToLive(60)//指定本推送的离线保存时长(单位：秒)，如果不传此字段则默认保存一天，最多指定保留十天
                        .build())
                .build();

        PushResult pushResult=null;
        try {
            pushResult =jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            throw new MyException(1001,e.getMessage());
        } catch (APIRequestException e) {
            throw new MyException(1002,e.getMessage());
        }
        return pushResult.isResultOK()?true:false;
    }

    /**
     * 推送通知和自定义消息：根据alias推给个人
     * @param alias
     * @param notificationTitle
     * @return
     */
    public boolean sendNotificationAndMessageByAlias(String alias, String notificationTitle, String notificationMessage,String msgContent)throws MyException{
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        PushPayload.Builder builder = PushPayload.newBuilder();
        builder.setPlatform(Platform.android());
        builder.setAudience(Audience.alias(alias));
        builder.setNotification(Notification.newBuilder()
                .addPlatformNotification(AndroidNotification.newBuilder()
                        .setTitle(notificationTitle)
                        .setAlert(notificationMessage)
                        .build())
                .build());
        builder.setMessage(Message.newBuilder()
                .setMsgContent(msgContent)
                .setContentType("json")
                .build());
        builder.setOptions(Options.newBuilder()
                .setApnsProduction(true)//APNs是否为生产环境，false为开发环境
                .setTimeToLive(60)//指定本推送的离线保存时长(单位：秒)，如果不传此字段则默认保存一天，最多指定保留十天
                .build());
        PushPayload payload = builder
                .build();

        PushResult pushResult=null;
        try {
            pushResult =jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            throw new MyException(1001,e.getMessage());
        } catch (APIRequestException e) {
            throw new MyException(1002,e.getMessage());
        }
        return pushResult.isResultOK()?true:false;
    }

}
