package com.harry.base.excel.exception;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelRuntimeException
 * @description: TODO
 * @date 2019/5/26 14:08
 */
public class ExcelRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1059413765208343152L;

    public ExcelRuntimeException() {
    }

    public ExcelRuntimeException(String message) {
        super(message);
    }

    public ExcelRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelRuntimeException(Throwable cause) {
        super(cause);
    }
}