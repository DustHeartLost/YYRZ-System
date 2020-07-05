package com.yyrz.family.advice;

import com.yyrz.common.myException.ErrorCode;
import com.yyrz.common.myException.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(basePackages = {"com.yyrz.family"})
public class FamilyControllerAdvice{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorCode> handleMyException(Exception e) {
        ResponseEntity<ErrorCode> responseEntity;
        if(e instanceof MyException) {
            MyException me=(MyException)e;
            responseEntity= new ResponseEntity<>(new ErrorCode(me.getCode(),me.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            responseEntity=new ResponseEntity<>(new ErrorCode(2000,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
