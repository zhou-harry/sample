package com.harry.common.response;

import java.io.Serializable;

/**
 * @author harry
 * @version 1.0
 * @title: ResponseData
 * @description: TODO
 * @date 2019/5/5 10:11
 */
public class ResponseData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";

    /** 返回信息码 */
    private String statusCode = "000000";
    /** 返回信息内容 */
    private String message = "操作成功";

    private Object data;

    public ResponseData(String statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResponseData(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
