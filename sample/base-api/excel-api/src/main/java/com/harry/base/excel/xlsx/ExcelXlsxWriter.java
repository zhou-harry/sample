package com.harry.base.excel.xlsx;

import com.harry.base.excel.convert.WriteConverter;
import com.harry.base.excel.exception.ExcelRuntimeException;
import com.harry.base.excel.pojo.ExcelMapping;
import com.harry.base.excel.pojo.ExcelProperty;
import com.harry.base.excel.util.DateUtil;
import com.harry.base.excel.util.POIUtil;
import com.harry.base.excel.util.ValidatorUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelXlsxWriter
 * @description: TODO
 * @date 2019/5/26 15:10
 */
public class ExcelXlsxWriter {

    private final ExcelMapping mExcelMapping;
    private final Integer mMaxSheetRecords;

    public ExcelXlsxWriter(ExcelMapping excelMapping, Integer maxSheetRecords) {
        mExcelMapping = excelMapping;
        mMaxSheetRecords = maxSheetRecords;
    }

    /**
     * 构建xlsxWorkbook对象
     *
     * @param data 数据集
     * @param isTemplate 是否是导出模板
     * @return SXSSFWorkbook
     */
    public SXSSFWorkbook generateXlsxWorkbook(List<?> data, boolean isTemplate) {
        SXSSFWorkbook workbook = POIUtil.newSXSSFWorkbook();
        List<ExcelProperty> propertyList = mExcelMapping.getPropertyList();
        double sheetNo = Math.ceil(data.size() / (double) mMaxSheetRecords);
        for (int index = 0; index <= (sheetNo == 0.0 ? sheetNo : sheetNo - 1); index++) {
            String sheetName = mExcelMapping.getName() + (index == 0 ? "" : "_" + index);
            SXSSFSheet sheet = generateXlsxHeader(workbook, propertyList, sheetName, isTemplate);
            if (null != data && data.size() > 0) {
                int startNo = index * mMaxSheetRecords;
                int endNo = Math.min(startNo + mMaxSheetRecords, data.size());
                for (int i = startNo; i < endNo; i++) {
                    SXSSFRow bodyRow = POIUtil.newSXSSFRow(sheet, i + 1 - startNo);
                    for (int j = 0; j < propertyList.size(); j++) {
                        SXSSFCell cell = POIUtil.newSXSSFCell(bodyRow, j);
                        ExcelXlsxWriter.buildCellValueByExcelProperty(cell, data.get(i), propertyList.get(j));
                    }
                }
            }
        }
        return workbook;
    }

    private SXSSFSheet generateXlsxHeader(SXSSFWorkbook workbook,
                                          List<ExcelProperty> propertyList,
                                          String sheetName, boolean isTemplate) {
        SXSSFDrawing sxssfDrawing = null;
        SXSSFSheet sheet = POIUtil.newSXSSFSheet(workbook, sheetName);
        SXSSFRow headerRow = POIUtil.newSXSSFRow(sheet, 0);

        for (int i = 0; i < propertyList.size(); i++) {
            ExcelProperty property = propertyList.get(i);
            SXSSFCell cell = POIUtil.newSXSSFCell(headerRow, i);
            POIUtil.setColumnWidth(sheet, i, property.getWidth(), property.getColumn());
            if (isTemplate) {
                // cell range
                POIUtil.setColumnCellRange(sheet, property.getRepository(), 1, mMaxSheetRecords, i, i);
                // cell comment.
                if (null == sxssfDrawing) {
                    sxssfDrawing = sheet.createDrawingPatriarch();
                }
                if (!ValidatorUtil.isEmpty(property.getComment())) {

                    CreationHelper factory = workbook.getCreationHelper();
                    ClientAnchor anchor = factory.createClientAnchor();
                    anchor.setCol1(cell.getColumnIndex());
                    anchor.setCol2(cell.getColumnIndex() + 4);
                    anchor.setRow1(headerRow.getRowNum());
                    anchor.setRow2(headerRow.getRowNum() + 2);
                    Comment cellComment = sxssfDrawing.createCellComment(anchor);

                    XSSFRichTextString xssfRichTextString = new XSSFRichTextString(
                            property.getComment());
                    Font commentFormatter = workbook.createFont();
                    xssfRichTextString.applyFont(commentFormatter);
                    cellComment.setString(xssfRichTextString);
                    cell.setCellComment(cellComment);
                }
            }
            cell.setCellStyle(getHeaderCellStyle(workbook));
            String headerColumnValue = property.getColumn();
            if (isTemplate && null != property.getRequired() && property.getRequired()) {
                headerColumnValue = (headerColumnValue + "[*]");
            }
            cell.setCellValue(headerColumnValue);
        }
        return sheet;
    }

    private static void buildCellValueByExcelProperty(SXSSFCell cell, Object entity,
                                                      ExcelProperty property) {
        Object cellValue;
        try {
            cellValue = BeanUtils.getProperty(entity, property.getName());
        } catch (Throwable e) {
            throw new ExcelRuntimeException(e);
        }
        if (null != cellValue) {
            String dateFormat = property.getDateFormat();
            if (!ValidatorUtil.isEmpty(dateFormat)) {
                if (cellValue instanceof Date) {
                    cell.setCellValue(DateUtil.format(dateFormat, (Date) cellValue));
                } else if (cellValue instanceof String) {
                    try {
                        Date parse = DateUtil.ENGLISH_LOCAL_DF.parse((String) cellValue);
                        cell.setCellValue(DateUtil.format(dateFormat, parse));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
            // writeConverterExp && writeConverter
            String writeConverterExp = property.getWriteConverterExp();
            WriteConverter writeConverter = property.getWriteConverter();
            if (!ValidatorUtil.isEmpty(writeConverterExp)) {
                try {
                    cellValue = POIUtil.convertByExp(cellValue, writeConverterExp);
                } catch (Throwable e) {
                    throw new ExcelRuntimeException(e);
                }
            } else if (null != writeConverter) {
                cell.setCellValue(writeConverter.convert(cellValue));
                return;
            }
            cell.setCellValue(String.valueOf(cellValue));
        }
    }

    private CellStyle mHeaderCellStyle = null;


    public CellStyle getHeaderCellStyle(SXSSFWorkbook wb) {
        if (null == mHeaderCellStyle) {
            mHeaderCellStyle = wb.createCellStyle();
            Font font = wb.createFont();
            mHeaderCellStyle.setFillForegroundColor((short) 12);
            mHeaderCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            mHeaderCellStyle.setBorderTop(BorderStyle.DOTTED);
            mHeaderCellStyle.setBorderRight(BorderStyle.DOTTED);
            mHeaderCellStyle.setBorderBottom(BorderStyle.DOTTED);
            mHeaderCellStyle.setBorderLeft(BorderStyle.DOTTED);
            mHeaderCellStyle.setAlignment(HorizontalAlignment.LEFT);// 对齐
            mHeaderCellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
            mHeaderCellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
            font.setColor(HSSFColor.WHITE.index);
            // 应用标题字体到标题样式
            mHeaderCellStyle.setFont(font);
            //设置单元格文本形式
            DataFormat dataFormat = wb.createDataFormat();
            mHeaderCellStyle.setDataFormat(dataFormat.getFormat("@"));
        }
        return mHeaderCellStyle;
    }

}
