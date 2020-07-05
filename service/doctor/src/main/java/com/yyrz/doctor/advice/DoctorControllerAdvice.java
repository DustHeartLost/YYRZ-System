package com.yyrz.doctor.advice;

import com.yyrz.common.myException.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"com.yyrz.doctor"})
public class DoctorControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorCode> handleMyException(Exception e) {
        ResponseEntity<ErrorCode> responseEntity;
        if(e instanceof MyException) {
            MyException me=(MyException)e;
            responseEntity= new ResponseEntity<>(new ErrorCode(me.getCode(),me.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            responseEntity=new ResponseEntity<>(new ErrorCode(3000,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return responseEntity;
    }
}
