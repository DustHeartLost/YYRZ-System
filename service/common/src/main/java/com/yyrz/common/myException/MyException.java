package com.yyrz.common.myException;

import lombok.Data;

@Data
public class MyException extends RuntimeException{
    int code;
    String msg;
    public MyException(int code, String msg){
        this.msg=msg;
        this.code=code;
    }
}
