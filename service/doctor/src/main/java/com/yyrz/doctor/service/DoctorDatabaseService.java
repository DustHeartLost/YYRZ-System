package com.yyrz.doctor.service;

import com.alibaba.fastjson.JSONObject;
import com.yyrz.common.myException.MyException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@FeignClient(value = "database")
public interface DoctorDatabaseService {

    @GetMapping(value = "query/{sql}")
    ArrayList<Map<String ,Object>> query (@PathVariable String sql);

    @PostMapping(value = "moca")
    Boolean postMoca (@RequestBody Map<String,Object> map);

    @GetMapping(value = "update/{sql}")
    boolean update(@PathVariable String sql);

    @PostMapping(value = "insertSensorData")
    boolean insert(@RequestBody JSONObject dataSensor);

}
