package com.yyrz.database.controller;

import com.yyrz.common.myException.MyException;
import com.yyrz.database.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class DatabaseController {
    @Autowired
    DataBaseService dataBaseService;

    @GetMapping(value = "query/{sql}")
    public List<Map<String ,Object>> queryByAccount(@PathVariable String sql)throws MyException{
        List<Map<String ,Object>> map;
         map=dataBaseService.query(sql);
        return map;
    }

    //post请求
    @PostMapping(value = "{table}")
    public boolean post(@PathVariable String table,@RequestBody Map<String,Object> map) {
        return dataBaseService.post(table,map);
    }

    @GetMapping("update/{sql}")
    public boolean update(@PathVariable String sql){
        return dataBaseService.update(sql);
    }
}
