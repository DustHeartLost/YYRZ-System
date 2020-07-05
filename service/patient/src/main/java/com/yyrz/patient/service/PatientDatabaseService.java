package com.yyrz.patient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Map;

@Service
@FeignClient(value = "database")
public interface PatientDatabaseService {

    @GetMapping(value = "query/{sql}")
    ArrayList<Map<String ,Object>> query(@PathVariable String sql);

    @GetMapping(value = "update/{sql}")
    boolean update(@PathVariable String sql);
}
