package com.yyrz.family.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
@FeignClient(value = "push",fallback = FamilyHystrix.class)
public interface FamilyPushService {
    @GetMapping(value = "isNotificationByAlias")
    boolean sendNotificationByAlias(@RequestParam String alias, @RequestParam String notificationTitle,@RequestParam String notificationMessage);

    @GetMapping(value = "isMessageByAlias")
    boolean sendMessageByAlias(@RequestParam String alias,@RequestParam  String msgContent);

    @GetMapping(value = "isNotificationAndMessageByAlias")
    boolean sendNotificationAndMessageByAlias(@RequestParam String alias,@RequestParam  String notificationTitle,@RequestParam  String notificationMessage,@RequestParam String msgContent);

}
