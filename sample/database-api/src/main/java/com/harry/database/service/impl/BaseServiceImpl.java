package com.harry.database.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.harry.database.repository.BaseRepository;
import com.harry.database.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 数据层公共实现类
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    protected abstract BaseRepository<T> getMapper();


    @Override
    public boolean insert(T entity) {
        boolean falg = true;
        try {
            getMapper().insert(entity);
        } catch (Exception e) {
            falg = false;
            logger.error("新增" + getClassName(entity) + "失败!原因是:", e);
        }
        return falg;
    }


    @Override
    public boolean update(T entity) {
        boolean falg = true;
        try {
            getMapper().update(entity);
        } catch (Exception e) {
            falg = false;
            logger.error("更新" + getClassName(entity) + "失败!原因是:", e);
        }
        return falg;
    }

    @Override
    public boolean deleteByPrimaryKey(int id) {
        boolean falg = true;
        try {
            getMapper().deleteByPrimaryKey(id);
        } catch (Exception e) {
            falg = false;
            logger.error("id:" + id + "删除失败!原因是:", e);
        }
        return falg;
    }

    @Override
    public boolean delete(T entity) {
        boolean falg = true;
        try {
            getMapper().delete(entity);
        } catch (Exception e) {
            falg = false;
            logger.error("删除" + getClassName(entity) + "失败!原因是:", e);
        }
        return falg;
    }

    @Override
    public T findByPrimaryKey(int id) {
        T obj = null;
        try {
            obj = getMapper().findByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("id:" + id + "查询失败!原因是:", e);
        }
        return obj;
    }

    @Override
    public T findByEntity(T entity) {
        T obj = null;
        try {
            obj = getMapper().findByEntity(entity);
        } catch (Exception e) {
            logger.error("查询" + getClassName(entity) + "失败!原因是:", e);
        }
        return obj;
    }

    @Override
    public List<T> findByListEntity(T entity) {
        List<T> list = null;
        try {
            Page<?> page = PageHelper.startPage(1, 2);
            System.out.println(getClassName(entity) + "设置第一页两条数据!");
            list = getMapper().findByListEntity(entity);
            System.out.println("总共有:" + page.getTotal() + "条数据,实际返回:" + list.size() + "两条数据!");
        } catch (Exception e) {
            logger.error("查询" + getClassName(entity) + "失败!原因是:", e);
        }
        return list;
    }

    @Override
    public List<T> findAll() {
        List<T> list = null;
        try {
            list = getMapper().findAll();
        } catch (Exception e) {
            logger.error("查询失败!原因是:", e);
        }
        return list;
    }

    @Override
    public Object findByObject(Object obj) {
        Object result = null;
        try {
            result = getMapper().findByObject(obj);
        } catch (Exception e) {
            logger.error("查询" + obj + "失败!原因是:", e);
        }
        return result;
    }

    private String getClassName(T t) {
       return t.getClass().getSimpleName();
    }
}
