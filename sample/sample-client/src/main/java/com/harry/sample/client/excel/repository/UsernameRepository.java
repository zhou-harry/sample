package com.harry.sample.client.excel.repository;

import com.harry.base.excel.repository.ExcelRepository;

/**
 * @author harry
 * @version 1.0
 * @title: UsernameRepository
 * @description: TODO
 * @date 2019/5/26 15:37
 */
public class UsernameRepository implements ExcelRepository {

    @Override
    public String[] resource() {
        return new String[]{"harry", "zhouhong"};
    }

}
