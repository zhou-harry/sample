package com.harry.mail.response;

/**
 * @author harry
 * @version 1.0
 * @title: ResultCode
 * @description: 邮件服务状态反馈码
 * @date 2019/5/5 10:25
 */
public enum ResultCode {

    SUCCESS("200", "操作成功"),
    FAILED("400", "操作失败"),
    TIMEOUT("500","响应超时，请稍后重试")
    ;

    private ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
