package com.alchemist.exception;

import com.alchemist.common.ResponseCode;

/**
 * Created by Baowen on 2017/12/3.
 */
public class BusinessException extends RuntimeException {
    private int code;
    private String innerMessage;

    public BusinessException() {
    }

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BusinessException(int code, String msg, String innerMessage) {
        super(msg);
        this.code = code;
        this.innerMessage = innerMessage;
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(ResponseCode errorCode) {
        super(errorCode.getDesc());
        this.code = errorCode.getCode();
    }

    public BusinessException(ResponseCode errorCode, Throwable cause) {
        super(errorCode.getDesc(), cause);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return this.code;
    }

    public String getInnerMessage() {
        return this.innerMessage;
    }
}
