package com.yyrz.database.advice;

import com.yyrz.common.myException.ErrorCode;
import com.yyrz.common.myException.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"com.yyrz.database"})
public class DatabaseControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorCode> handleMyException(Exception e) {
        ResponseEntity<ErrorCode> responseEntity;
        if(e instanceof MyException) {
            MyException me=(MyException)e;
            responseEntity= new ResponseEntity<>(new ErrorCode(me.getCode(),me.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            responseEntity=new ResponseEntity<>(new ErrorCode(5000,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}