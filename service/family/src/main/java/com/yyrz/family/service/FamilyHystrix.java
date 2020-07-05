package com.yyrz.family.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class FamilyHystrix implements FamilyPushService {
    @Override
    public boolean sendNotificationByAlias(@RequestParam String alias, @RequestParam String notificationTitle, @RequestParam String notificationMessage) {
        return false;
    }

    @Override
    public boolean sendMessageByAlias(@RequestParam String alias, @RequestParam String msgContent) {
        return false;
    }

    @Override
    public boolean sendNotificationAndMessageByAlias(@RequestParam String alias,@RequestParam String notificationTitle,@RequestParam String notificationMessage,@RequestParam  String msgContent) {
        return false;
    }
}
