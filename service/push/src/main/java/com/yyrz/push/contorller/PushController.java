package com.yyrz.push.contorller;

import com.yyrz.common.myException.MyException;
import com.yyrz.push.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PushController {
    @Autowired
    PushService pushService;

    //此接口正常可用,异常正确抛出
    @PostMapping(value = "isNotificationByAlias")
    public boolean sendNotificationByAlias(@RequestParam String alias, @RequestParam String notificationTitle,@RequestParam String notificationMessage)throws MyException {
       return pushService.sendNotificationByAlias(alias,notificationTitle,notificationMessage);
    }
    //此接口正常可用，异常正确抛出
    @PostMapping(value = "isMessageByAlias")
    public boolean sendMessageByAlias(@RequestParam String alias, @RequestParam  String msgContent, @RequestBody Map<String,String> extra)throws MyException{
        return pushService.sendMessageByAlias(alias,msgContent,extra);
    }
    //此接口正常可用,异常正确抛出
    @GetMapping(value = "isNotificationAndMessageByAlias")
    public boolean sendNotificationAndMessageByAlias(@RequestParam String alias,@RequestParam  String notificationTitle,@RequestParam  String notificationMessage,@RequestParam String msgContent)throws MyException{
        return pushService.sendNotificationAndMessageByAlias(alias,notificationTitle,notificationMessage,msgContent);
    }
}
