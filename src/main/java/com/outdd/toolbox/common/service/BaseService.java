package com.outdd.toolbox.common.service;

import com.outdd.toolbox.common.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * TODO: [NAME]
 * @author VAIE
 * @date: 2018/9/23-22:14
 * @version v1.0
 */
public class BaseService<D extends BaseDao<T>, T> {

    @Autowired
    protected D dao;
    /**
     * 获取单条数据
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 保存数据（插入）
     * @param entity
     */
    @Transactional(readOnly = false)
    public void save(T entity) {
        dao.insert(entity);
    }

    /**
     * 保存数据（更新）
     * @param entity
     */
    @Transactional(readOnly = false)
    public void update(T entity) {
        dao.update(entity);
    }

    /**
     * 删除数据
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete(T entity) {
        dao.delete(entity);
    }

    /**
     * 删除数据 (批量删除数据)
     * @param id
     */
    @Transactional(readOnly = false)
    public void delete(String[] id) {
        dao.delete(id);
    }
}
