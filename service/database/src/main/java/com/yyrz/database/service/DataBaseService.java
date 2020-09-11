package com.yyrz.database.service;

import com.yyrz.database.model.PatientVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class DataBaseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> query(String sql) {
        List<Map<String, Object>> map = jdbcTemplate.queryForList(sql);
        return map;
    }

    public boolean post(String table, Map<String, Object> map) {
        String temp = "";
        Iterator<String> iterator = map.keySet().iterator();
         while (iterator.hasNext()) {
            temp += "'" + map.get(iterator.next()) + "'";
            if (iterator.hasNext())
                temp += ",";
        }
        String sql;
        if (table.equals("moca")) sql = "insert into " + table + " values(null," + temp + ");";
        else sql = "insert into " + table + " values(" + temp + ");";
        return jdbcTemplate.update(sql) > 0;
    }

    public boolean update(String sql){
        return jdbcTemplate.update(sql) > 0;
    }

    public void insert(String sql) {
         jdbcTemplate.execute(sql);
    }
}
