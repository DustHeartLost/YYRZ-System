package com.yyrz.doctor.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
@FeignClient(value = "push",fallback = DoctorHystrix.class)
public interface DoctorPushService {
    @GetMapping(value = "isNotificationByAlias")
    boolean sendNotificationByAlias(@RequestParam String alias, @RequestParam String notificationTitle, @RequestParam String notificationMessage);

    @PostMapping(value = "isMessageByAlias")
    boolean sendMessageByAlias(@RequestParam String alias, @RequestParam String msgContent, @RequestBody Map<String,String> extra);

    @GetMapping(value = "isNotificationAndMessageByAlias")
    boolean sendNotificationAndMessageByAlias(@RequestParam String alias, @RequestParam String notificationTitle, @RequestParam String notificationMessage, @RequestParam String msgContent);
}
