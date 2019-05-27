package com.harry.sample.client.excel.repository;

import com.harry.base.excel.repository.ExcelRepository;

/**
 * @author harry
 * @version 1.0
 * @title: GroupNameRepository
 * @description: TODO
 * @date 2019/5/26 15:51
 */
public class GroupNameRepository implements ExcelRepository {
    @Override
    public String[] resource() {
        return new String[]{"管理员", "普通会员", "游客"};
    }
}
