package com.outdd.toolbox.excel.dao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
 * TODO: 动态生成表的接口
 * @author VAIE
 * @date: 2018/10/13-12:55
 * @version v1.0
 */
@Mapper
public interface OperateTableMapper {

    int existTable(String tableName);

    int dropTable(@Param("tableName") String tableName);

    int createNewTable(@Param("tableName") String tableName, @Param("ziduan") String ziduan);

    int insert(@Param("tableName") String tableName, @Param("fields") String fields, @Param("values") List<String> values);
}
