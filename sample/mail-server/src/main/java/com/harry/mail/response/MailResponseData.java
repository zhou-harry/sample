package com.harry.mail.response;

import com.harry.common.response.ResponseData;

/**
 * @author harry
 * @version 1.0
 * @title: MailResponseData
 * @description: TODO
 * @date 2019/5/5 10:23
 */
public class MailResponseData  extends ResponseData {

    private static final long serialVersionUID = 1L;

    public MailResponseData(String statusCode, String message, Object data) {
        super(statusCode, message, data);
    }

    public MailResponseData(String statusCode, String message) {
        super(statusCode, message);
    }

    public MailResponseData(ResultCode result) {
        super(result.getCode(), result.getMsg(), "");
    }

    public MailResponseData(ResultCode result, Object data) {
        this(result, data, false);
    }

    public MailResponseData(ResultCode result, Object data, boolean append) {
        super(result.getCode(), result.getMsg(), data);

        switch (result) {
            case SUCCESS:
                break;
            default:
                if (data instanceof String) {
                    if (append) {
                        this.setMessage(
                                new StringBuffer(this.getMessage())
                                        .append("[")
                                        .append(data.toString())
                                        .append("]")
                                        .toString()
                        );
                    } else {
                        this.setMessage(data.toString());
                    }
                }
                break;
        }
    }
}
