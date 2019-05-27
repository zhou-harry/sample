package com.harry.base.excel.util;

import com.harry.base.excel.exception.ExcelKitRuntimeException;
import com.harry.base.excel.repository.ExcelRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author harry
 * @version 1.0
 * @title: POIUtil
 * @description: TODO
 * @date 2019/5/26 15:12
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class POIUtil {

    private static final int mDefaultRowAccessWindowSize = 100;
    private static CellStyle TIPS_STYLE = null;


    private static SXSSFWorkbook newSXSSFWorkbook(int rowAccessWindowSize) {
        return new SXSSFWorkbook(rowAccessWindowSize);
    }

    public static SXSSFWorkbook newSXSSFWorkbook() {
        return POIUtil.newSXSSFWorkbook(POIUtil.mDefaultRowAccessWindowSize);
    }

    public static SXSSFSheet newSXSSFSheet(SXSSFWorkbook wb, String sheetName) {
        return wb.createSheet(sheetName);
    }

    public static SXSSFRow newSXSSFRow(SXSSFSheet sheet, int index) {
        return sheet.createRow(index);
    }

    public static SXSSFCell newSXSSFCell(SXSSFRow row, int index) {
        return row.createCell(index);
    }

    public static void setColumnWidth(
            SXSSFSheet sheet, int index, Short width, String value) {
        boolean widthNotHaveConfig = (null == width || width == -1);
        if (widthNotHaveConfig && !ValidatorUtil.isEmpty(value)) {
            sheet.setColumnWidth(index, (short) (value.length() * 2048));
        } else {
            width = widthNotHaveConfig ? 200 : width;
            sheet.setColumnWidth(index, (short) (width * 35.7));
        }
    }

    public static void setColumnCellRange(SXSSFSheet sheet, ExcelRepository repository,
                                          int firstRow, int endRow,
                                          int firstCell, int endCell) {
        if (null != repository) {
            String[] datasource = repository.resource();
            if (null != datasource && datasource.length > 0) {
                if (datasource.length > 100) {
                    throw new ExcelKitRuntimeException("Options item too much.");
                }

                DataValidationHelper validationHelper = sheet.getDataValidationHelper();
                DataValidationConstraint explicitListConstraint = validationHelper
                        .createExplicitListConstraint(datasource);
                CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCell,
                        endCell);
                DataValidation validation = validationHelper
                        .createValidation(explicitListConstraint, regions);
                validation.setSuppressDropDownArrow(true);
                validation.createErrorBox("提示", "请从下拉列表选取");
                validation.setShowErrorBox(true);
                sheet.addValidationData(validation);
            }
        }
    }

    public static void write(SXSSFWorkbook wb, OutputStream out) {
        try {
            if (null != out) {
                wb.write(out);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void download(
            SXSSFWorkbook wb, HttpServletResponse response, String filename) {
        try {
            OutputStream out = response.getOutputStream();
            response.setContentType(Const.XLSX_CONTENT_TYPE);
            response.setHeader(Const.XLSX_HEADER_KEY,
                    String.format(Const.XLSX_HEADER_VALUE_TEMPLATE, filename));
            POIUtil.write(wb, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object convertByExp(Object propertyValue, String converterExp)
            throws Exception {
        try {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource) {
                String[] itemArray = item.split("=");
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return propertyValue;
    }

    public static int countNullCell(String ref, String ref2) {
        // excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String xfd_1 = ref2.replaceAll("\\d+", "");

        xfd = POIUtil.fillChar(xfd, 3, '@', true);
        xfd_1 = POIUtil.fillChar(xfd_1, 3, '@', true);

        char[] letter = xfd.toCharArray();
        char[] letter_1 = xfd_1.toCharArray();
        int res =
                (letter[0] - letter_1[0]) * 26 * 26 + (letter[1] - letter_1[1]) * 26 + (letter[2]
                        - letter_1[2]);
        return res - 1;
    }

    private static String fillChar(String str, int len, char let, boolean isPre) {
        int len_1 = str.length();
        if (len_1 < len) {
            if (isPre) {
                for (int i = 0; i < (len - len_1); i++) {
                    str = let + str;
                }
            } else {
                for (int i = 0; i < (len - len_1); i++) {
                    str = str + let;
                }
            }
        }
        return str;
    }

    public static void checkExcelFile(File file) {
        String filename = null != file ? file.getAbsolutePath() : null;
        if (null == filename || !file.exists()) {
            throw new ExcelKitRuntimeException("Excel file[" + filename + "] does not exist.");
        }
        if (!filename.endsWith(Const.XLSX_SUFFIX)) {
            throw new ExcelKitRuntimeException(
                    "[" + filename + "]Only .xlsx formatted files are supported.");
        }
    }

    public static void errorComment(Workbook workbook, Sheet sheet,int rowIndex,int colIndex,
                        String message) {
        CreationHelper factory = workbook.getCreationHelper();
        ClientAnchor anchor = factory.createClientAnchor();
        anchor.setRow1(rowIndex);
        anchor.setRow2(rowIndex + 2);
        anchor.setCol1(colIndex);
        anchor.setCol2(colIndex + 3);

        Drawing<?> drawing = sheet.createDrawingPatriarch();

        RichTextString context=null;
        if (drawing instanceof HSSFPatriarch){
            context=new HSSFRichTextString(message);
        }else {
            context=new XSSFRichTextString(message);
        }
        Comment comment = drawing.createCellComment(anchor);
        comment.setString(context);
        comment.setAuthor("system-error");
    }

}
