package com.harry.base.excel.handler;

import com.harry.base.excel.pojo.ExcelErrorField;
import com.harry.base.excel.util.POIUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelXlsxReadHandler
 * @description: TODO
 * @date 2019/5/26 17:36
 */
public abstract class ExcelXlsxReadHandler<T> implements ExcelReadHandler<T> {

    private WeakReference<SXSSFWorkbook> wk;

    public ExcelXlsxReadHandler(InputStream inputStream) throws IOException {
        this.wk = new WeakReference<>(new SXSSFWorkbook(new XSSFWorkbook(inputStream)));
    }

    @Override
    public void onError(int sheetIndex, int rowIndex,
                        List<ExcelErrorField> errorFields) {
        errorComment(sheetIndex,rowIndex,errorFields);
    }

    public void download(HttpServletResponse response, String filename){
        SXSSFWorkbook workbook = this.wk.get();
        if (null==workbook) {
            return;
        }
        POIUtil.download(workbook,response,filename);
    }

    protected void errorComment(int sheetIndex, int rowIndex, List<ExcelErrorField> errorFields){
        SXSSFWorkbook workbook = this.wk.get();
        if (null==workbook) {
            return;
        }
        SXSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        errorFields.forEach(field->{
            POIUtil.errorComment(workbook,sheet,rowIndex,field.getCellIndex(),field.getErrorMessage());
        });
    };


}
