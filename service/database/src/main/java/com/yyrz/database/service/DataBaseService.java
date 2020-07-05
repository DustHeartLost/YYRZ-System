package com.yyrz.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class DataBaseService {
    @Autowired
    JdbcTemplate jdbcTemplate;

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
        return jdbcTemplate.update(sql) > 0 ? true : false;
    }

    public boolean update(String sql){
        jdbcTemplate.update(sql);
        return true;
    }
}
