package com.harry.base.excel.exception;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelKitRuntimeException
 * @description: TODO
 * @date 2019/5/26 14:08
 */
public class ExcelKitRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1059413765208343152L;

    public ExcelKitRuntimeException() {
    }

    public ExcelKitRuntimeException(String message) {
        super(message);
    }

    public ExcelKitRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelKitRuntimeException(Throwable cause) {
        super(cause);
    }
}