package com.yyrz.patient.service;

import com.yyrz.patient.model.PatientVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@FeignClient(value = "database")
public interface PatientDatabaseService {

    @GetMapping(value = "query/{sql}")
    ArrayList<Map<String ,Object>> query(@PathVariable String sql);

    @GetMapping(value = "update/{sql}")
    boolean update(@PathVariable String sql);

    @PostMapping(value = "selectByPaccount/{sql}")
    List<Map<String,Object>> selectByPaccount(@PathVariable(value = "sql") String sql);

    @PostMapping(value = "insertPat/{sql}")
    void insertPat(@PathVariable(value = "sql") String sql);
}
