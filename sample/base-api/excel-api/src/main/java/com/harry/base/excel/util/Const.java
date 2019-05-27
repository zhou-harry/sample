package com.harry.base.excel.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author harry
 * @version 1.0
 * @title: Const
 * @description: TODO
 * @date 2019/5/26 15:13
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Const {

    public static final String ENCODING = "UTF-8";
    public static final String XLSX_SUFFIX = ".xlsx";
    public static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String XLSX_HEADER_KEY = "Content-disposition";
    public static final String XLSX_HEADER_VALUE_TEMPLATE = "attachment; filename=%s";
    public static final String XLSX_DEFAULT_EMPTY_CELL_VALUE = "$EMPTY_CELL$";
    public static final Integer XLSX_DEFAULT_BEGIN_READ_ROW_INDEX = 1;
    public static final String SAX_PARSER_CLASS = "org.apache.xerces.parsers.SAXParser";
    public static final String SAX_C_ELEMENT = "c";
    public static final String SAX_R_ATTR = "r";
    public static final String SAX_T_ELEMENT = "t";
    public static final String SAX_S_ATTR_VALUE = "s";
    public static final String SAX_RID_PREFIX = "rId";
    public static final String SAX_ROW_ELEMENT = "row";

    public final static String MODE_EXPORT = "$MODE_EXPORT$";
    public final static String MODE_BUILD = "$MODE_BUILD$";
    public final static String MODE_IMPORT = "$MODE_IMPORT$";
    public static final Integer MAX_SHEET_RECORDS = 5000;
}
