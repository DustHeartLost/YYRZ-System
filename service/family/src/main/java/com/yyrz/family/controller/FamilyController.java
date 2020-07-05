package com.yyrz.family.controller;

import com.yyrz.family.service.FamilyPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FamilyController {
    @Autowired
    FamilyPushService familyPushService;

    @GetMapping(value = "isNotificationByAlias")
    public boolean sendNotificationByAlias(@RequestParam String alias, @RequestParam String notificationTitle,@RequestParam String notificationMessage){
        return familyPushService.sendNotificationByAlias(alias,notificationTitle,notificationMessage);
    }
    @GetMapping(value = "isMessageByAlias")
    public boolean sendMessageByAlias(@RequestParam String alias,@RequestParam  String msgContent){
        return familyPushService.sendMessageByAlias(alias,msgContent);
    }

    @GetMapping(value = "isNotificationAndMessageByAlias")
    public boolean sendNotificationAndMessageByAlias(@RequestParam String alias,@RequestParam  String notificationTitle,@RequestParam  String notificationMessage,@RequestParam String msgContent ){
        return familyPushService.sendNotificationAndMessageByAlias(alias,notificationTitle,notificationMessage,msgContent);
    }
}
