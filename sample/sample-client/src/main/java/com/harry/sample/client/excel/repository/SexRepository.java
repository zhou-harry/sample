package com.harry.sample.client.excel.repository;

import com.harry.base.excel.repository.ExcelRepository;

/**
 * @author harry
 * @version 1.0
 * @title: SexRepository
 * @description: TODO
 * @date 2019/5/26 15:43
 */
public class SexRepository implements ExcelRepository {
    @Override
    public String[] resource() {
        return new String[]{"未知", "男", "女"};
    }
}
