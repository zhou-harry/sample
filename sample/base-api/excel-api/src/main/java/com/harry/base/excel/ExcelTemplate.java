package com.harry.base.excel;

import com.harry.base.excel.exception.ExcelRuntimeException;
import com.harry.base.excel.factory.ExcelMappingFactory;
import com.harry.base.excel.handler.ExcelReadHandler;
import com.harry.base.excel.pojo.ExcelMapping;
import com.harry.base.excel.util.Const;
import com.harry.base.excel.util.POIUtil;
import com.harry.base.excel.xlsx.ExcelXlsxReader;
import com.harry.base.excel.xlsx.ExcelXlsxWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelTemplate
 * @description: TODO
 * @date 2019/5/26 15:07
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExcelTemplate {

    private Class<?> mClass = null;
    private HttpServletResponse mResponse = null;
    private OutputStream mOutputStream = null;
    private Integer mMaxSheetRecords = Const.MAX_SHEET_RECORDS;
    private String mCurrentOptionMode = Const.MODE_EXPORT;

    /**
     * 使用此构造器来执行浏览器导出
     *
     * @param clazz    导出实体对象
     * @param response 原生 response 对象, 用于响应浏览器下载
     * @return ExcelTemplate obj.
     * @see ExcelTemplate#downXlsx(List, boolean)
     */
    public static ExcelTemplate $Export(Class<?> clazz, HttpServletResponse response) {
        return new ExcelTemplate(clazz, response);
    }

    public void downXlsx(List<?> data, boolean isTemplate) {
        if (!mCurrentOptionMode.equals(Const.MODE_EXPORT)) {
            throw new ExcelRuntimeException(
                    "请使用com.wuwenze.poi.ExcelTemplate.$Export(Class<?> clazz, HttpServletResponse response)构造器初始化参数.");
        }
        try {
            ExcelMapping excelMapping = ExcelMappingFactory.get(mClass);
            ExcelXlsxWriter excelXlsxWriter = new ExcelXlsxWriter(excelMapping,
                    mMaxSheetRecords);
            SXSSFWorkbook workbook = excelXlsxWriter.generateXlsxWorkbook(data, isTemplate);
            String fileName = isTemplate ? (excelMapping.getName() + "-导入模板.xlsx")
                    : (excelMapping.getName() + "-导出结果.xlsx");
            POIUtil.download(workbook, mResponse, URLEncoder.encode(fileName, Const.ENCODING));
        } catch (Throwable e) {
            throw new ExcelRuntimeException("downXlsx error", e);
        }
    }


    /**
     * 使用此构造器来执行构建文件流.
     *
     * @param clazz        导出实体对象
     * @param outputStream 输出流
     * @return ExcelTemplate obj.
     * @see ExcelTemplate#writeXlsx(List, boolean)
     */
    public static ExcelTemplate $Builder(Class<?> clazz, OutputStream outputStream) {
        return new ExcelTemplate(clazz, outputStream);
    }

    public void writeXlsx(List<?> data, boolean isTemplate) {
        if (!mCurrentOptionMode.equals(Const.MODE_BUILD)) {
            throw new ExcelRuntimeException(
                    "请使用com.wuwenze.poi.ExcelTemplate.$Builder(Class<?> clazz, OutputStream outputStream)构造器初始化参数.");
        }
        ExcelMapping excelMapping = ExcelMappingFactory.get(mClass);
        ExcelXlsxWriter excelXlsxWriter = new ExcelXlsxWriter(excelMapping,
                mMaxSheetRecords);
        SXSSFWorkbook workbook = excelXlsxWriter.generateXlsxWorkbook(data, isTemplate);
        POIUtil.write(workbook, mOutputStream);
    }

    /**
     * 使用此构造器来执行Excel文件导入.
     *
     * @param clazz 导出实体对象
     * @return ExcelTemplate obj.
     * @see ExcelTemplate#readXlsx(File, Integer, ExcelReadHandler)
     * @see ExcelTemplate#readXlsx(InputStream, Integer, ExcelReadHandler)
     * @see ExcelTemplate#readXlsx(File, ExcelReadHandler)
     * @see ExcelTemplate#readXlsx(InputStream, ExcelReadHandler)
     */
    public static ExcelTemplate $Import(Class<?> clazz) {
        return new ExcelTemplate(clazz);
    }


    public void readXlsx(File excelFile, ExcelReadHandler<?> excelReadHandler) {
        readXlsx(excelFile, -1, excelReadHandler);
    }

    public void readXlsx(File excelFile, Integer sheetIndex,
                         ExcelReadHandler<?> excelReadHandler) {
        try {
            InputStream inputStream = new FileInputStream(excelFile);
            readXlsx(inputStream, sheetIndex, excelReadHandler);
        } catch (Throwable e) {
            throw new ExcelRuntimeException("readXlsx error", e);
        }
    }

    public void readXlsx(InputStream inputStream, ExcelReadHandler<?> excelReadHandler) {
        readXlsx(inputStream, -1, excelReadHandler);
    }

    public void readXlsx(InputStream inputStream, Integer sheetIndex,
                         ExcelReadHandler<?> excelReadHandler) {
        if (!mCurrentOptionMode.equals(Const.MODE_IMPORT)) {
            throw new ExcelRuntimeException(
                    "请使用com.wuwenze.poi.ExcelTemplate.$Import(Class<?> clazz)构造器初始化参数.");
        }
        ExcelMapping excelMapping = ExcelMappingFactory.get(mClass);
        ExcelXlsxReader excelXlsxReader = new ExcelXlsxReader(mClass, excelMapping,
                excelReadHandler);
        if (sheetIndex >= 0) {
            excelXlsxReader.process(inputStream, sheetIndex);
            return;
        }
        excelXlsxReader.process(inputStream);
    }

    public ExcelTemplate setMaxSheetRecords(Integer mMaxSheetRecords) {
        this.mMaxSheetRecords = mMaxSheetRecords;
        return this;
    }

    protected ExcelTemplate(Class<?> clazz) {
        this(clazz, null, null);
        mCurrentOptionMode = Const.MODE_IMPORT;
    }

    protected ExcelTemplate(Class<?> clazz, OutputStream outputStream) {
        this(clazz, outputStream, null);
        mCurrentOptionMode = Const.MODE_BUILD;
    }

    protected ExcelTemplate(Class<?> clazz, HttpServletResponse response) {
        this(clazz, null, response);
        mCurrentOptionMode = Const.MODE_EXPORT;
    }

    protected ExcelTemplate(
            Class<?> clazz, OutputStream outputStream, HttpServletResponse response) {
        mClass = clazz;
        mOutputStream = outputStream;
        mResponse = response;
    }

}
