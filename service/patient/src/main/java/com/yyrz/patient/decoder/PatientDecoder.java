package com.yyrz.patient.decoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyrz.common.myException.MyException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

@Component
public class PatientDecoder implements ErrorDecoder {
    private int code=0;
    private String msg=null;
    @Override
    public MyException decode(String methodKey, Response response) {
        if(response.status()==200) return null;
        createData(response.body().toString());
        return new MyException(code,msg);
    }

    private void createData(String data){
        data=data.replaceFirst(":"," ");
        data=data.replaceFirst(","," ");
        String[]temp=data.split("\"");
        code= Integer.parseInt(temp[2].substring(1,temp[2].length()-1));
        msg=temp[5];
    }
}
