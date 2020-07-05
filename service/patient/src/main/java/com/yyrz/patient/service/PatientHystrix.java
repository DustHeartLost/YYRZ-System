package com.yyrz.patient.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component
public class PatientHystrix implements PatientPushService {
    @Override
    public boolean sendNotificationByAlias(@RequestParam String alias, @RequestParam String notificationTitle, @RequestParam String notificationMessage) {
        return false;
    }

    @Override
    public boolean sendMessageByAlias(String alias, String msgContent, Map<String, String> extra) {
        return false;
    }

    @Override
    public boolean sendNotificationAndMessageByAlias(@RequestParam String alias,@RequestParam String notificationTitle,@RequestParam  String notificationMessage,@RequestParam  String msgContent) {
        return false;
    }
}
