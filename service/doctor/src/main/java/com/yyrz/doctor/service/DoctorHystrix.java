package com.yyrz.doctor.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component
public class DoctorHystrix implements DoctorPushService {
    @Override
    public boolean sendNotificationByAlias(@RequestParam String alias, @RequestParam String notificationTitle, @RequestParam String notificationMessage) {
        return false;
    }

    @Override
    public boolean sendMessageByAlias(@RequestParam String alias, @RequestParam String msgContent,@RequestBody(required = false) Map<String,String> map) {
        return false;
    }

    @Override
    public boolean sendNotificationAndMessageByAlias(@RequestParam String alias,@RequestParam String notificationTitle,@RequestParam String notificationMessage,@RequestParam  String msgContent) {
        return false;
    }
}
