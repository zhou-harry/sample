package com.harry.sample.client.excel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.harry.base.excel.ExcelTemplate;
import com.harry.base.excel.handler.ExcelReadHandler;
import com.harry.base.excel.handler.ExcelXlsxReadHandler;
import com.harry.base.excel.pojo.ExcelErrorField;
import com.harry.sample.client.excel.domain.TestDomain;
import com.harry.sample.client.excel.domain.TestGroupDomain;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelController
 * @description: TODO
 * @date 2019/5/26 15:56
 */
@RestController
public class ExcelController {

    Logger logger= LoggerFactory.getLogger(getClass());

    // 生成导入模板（含3条示例数据）
    @GetMapping(value = "/excel/downLoad")
    public void downTemplate(HttpServletResponse response) {

        ArrayList<TestDomain> domains = Lists.newArrayList(
                new TestDomain(10010,"harry","123456","test@qq.com",1,new TestGroupDomain("管理员"),new Date(),1000),
                new TestDomain(10011,"zhouhong","123456","test@qq.com",1,new TestGroupDomain("普通会员"),new Date(),1000),
                new TestDomain(10012,"周宏","123456","test@qq.com",1,new TestGroupDomain("游客"),new Date(),1000)
        );
        ExcelTemplate
                .$Export(TestDomain.class, response)
                .downXlsx(domains, true);
    }

    @PostMapping(value = "/excel/import")
    public ResponseEntity<?> importEntity(HttpServletResponse response,@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<TestDomain> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();

        ExcelTemplate.$Import(TestDomain.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<TestDomain>() {
                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, TestDomain entity) {
                        successList.add(entity); // 单行读取成功，加入入库队列。
                    }
                    @Override
                    public void onError(int sheetIndex, int rowIndex,
                                        List<ExcelErrorField> errorFields) {
                        Map<String, Object> error = Maps.newHashMap();
                        error.put("sheetIndex", sheetIndex);
                        error.put("rowIndex", rowIndex);
                        error.put("errorFields", errorFields);
                        // 读取数据失败，记录了当前行所有失败的数据
                        errorList.add(error);
                    }
                });

        // TODO: 执行successList的入库操作。

        //返回
        HashMap<Object, Object> result = Maps.newHashMap();
        result.put("data", successList);
        result.put("haveError", !CollectionUtils.isEmpty(errorList));
        result.put("error", errorList);
        result.put("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L);

        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/excel/importExcel")
    public void importExcel(HttpServletResponse response,@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<TestDomain> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();

        ExcelXlsxReadHandler<TestDomain> handler = new ExcelXlsxReadHandler<TestDomain>(file.getInputStream()) {
            @Override
            public void onSuccess(int sheetIndex, int rowIndex, TestDomain entity) {
                successList.add(entity); // 单行读取成功，加入入库队列。
            }
        };
        ExcelTemplate.$Import(TestDomain.class)
                .readXlsx(file.getInputStream(), handler);

        // TODO: 执行successList的入库操作。

        //导出
        handler.download(response,"error.xlsx");
    }

    @GetMapping(value = "/excel/export")
    public void downXlsx(HttpServletResponse response) {

        List<TestDomain> domains = Lists.newArrayList();
        for (int i = 0; i < 100000; i++) {
            TestDomain domain= new TestDomain(10012,"周宏","123456","test@qq.com",1,new TestGroupDomain("游客"),new Date(),1000);
            domains.add(domain);
        }
        long beginMillis = System.currentTimeMillis();

        ExcelTemplate.$Export(TestDomain.class, response).downXlsx(domains, false);

        logger.info("#ExcelTemplate.$Export success, size={},timeConsuming={}s",
                domains.size(), (System.currentTimeMillis() - beginMillis) / 1000L);
    }

}
