package com.yyrz.common.myException;

import lombok.Data;

@Data
public class ErrorCode {
    private int code;
    private String msg;

    public ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
