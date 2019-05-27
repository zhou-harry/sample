package com.harry.base.excel.exception;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelConfigAnalyzeFailureException
 * @description: TODO
 * @date 2019/5/26 14:09
 */
public class ExcelConfigAnalyzeFailureException extends ExcelRuntimeException {

    public ExcelConfigAnalyzeFailureException(String message) {
        super(message);
    }

    public ExcelConfigAnalyzeFailureException(Throwable cause) {
        super(cause);
    }
}
