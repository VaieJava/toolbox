package com.outdd.toolbox.common.dao;

import java.util.List;

/*
 * TODO: 增读改删数据层接口
 * @author VAIE
 * @date: 2018/9/23-21:48
 * @version v1.0
 */
public interface BaseDao<T> {
    /**
     * 添加记录到系统
     * @param entity
     * @return
     */
    public int insert(T entity);

    /**
     * 获取单条数据
     * @param id
     * @return
     */
    public T get(String id);

    /**
     * 获取单条数据
     * @param entity
     * @return
     */
    public T get(T entity);

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    public List<T> findList(T entity);

    /**
     * 查询所有数据列表
     * @param entity
     * @return
     */
    public List<T> findAllList(T entity);

    /**
     * 查询所有数据列表
     * @see public List<T> findAllList(T entity)
     * @return
     */
    @Deprecated
    public List<T> findAllList();

    /**
     * 更新数据
     * @param entity
     * @return
     */
    public int update(T entity);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param id
     * @see public int delete(T entity)
     * @return
     */
    @Deprecated
    public int delete(String id);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param entity
     * @return
     */
    public int delete(T entity);

    /**
     * 删除数据 (批量删除数据)
     * @param id
     * @return
     */
    public int delete(String[] id);
}