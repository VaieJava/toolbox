package com.outdd.toolbox.excel.service;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.excel.dao.OperateTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * TODO: Excel的逻辑层
 * @author VAIE
 * @date: 2018/10/17-20:35
 * @version v1.0
 */
@Service
public class ExcelService {
    @Autowired
    OperateTableMapper operateTableMapper;
    /**
     * TODO: 创建一个新表
     * @param tableName 表名
     * @param fields 字段名
     * @return: boolean
     * @auther: vaie
     * @date: 2018/10/18 20:03
     */
    public boolean createNewTable (String tableName , String fields){
        int count=operateTableMapper.existTable(tableName);
        if(count!=0){
            operateTableMapper.dropTable(tableName);
        }
        count=operateTableMapper.createNewTable(tableName,fields);
        if(count==0){
            return false;
        }
        return true;
    }
    /**
     * TODO: 新增数据
     * @param tabName 表名
     * @param jsonList 数据集合
     * @return: int 新增的条数
     * @auther: vaie
     * @date: 2018/10/18 20:04
     */
    public int insert(String tabName , List<JSONObject> jsonList){
        String keys=getKey(jsonList.get(0));
        int count=0;
        List<List<JSONObject>> pluralList=averageAssign(jsonList);
        for(List<JSONObject> json:pluralList){
            List<String> values=getValues(json);
            count+=operateTableMapper.insert(tabName,keys,values);
        }
        return count;
    }
    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source){
        int singleNum=700;//每个list有多少个
        int count=source.size();
        int openList= (int) Math.ceil((double)count/singleNum);//需要分割成多数list
        int n=openList;
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }
    /**
     * TODO: 获取值
     * @param jsonList
     * @return: java.util.List<java.lang.String>
     * @auther: vaie
     * @date: 2018/10/18 20:05
     */
    public List<String> getValues(List<JSONObject> jsonList){
        List<String> list = new ArrayList<String>();
        for(JSONObject json:jsonList){
            String value="";
            for (Map.Entry<String, Object> entryz : json.entrySet()) {
                value+="'"+entryz.getValue()+"',";
            }
            list.add(value.substring(0,value.length()-1));
        }
        return list;
    }
    /**
     * TODO: 获取Key
     * @param json
     * @return: java.lang.String
     * @auther: vaie
     * @date: 2018/10/18 20:05
     */
    public String getKey(JSONObject json){
        String z="";
        for (Map.Entry<String, Object> entryz : json.entrySet()) {
            z+=entryz.getKey()+",";
        }
        return z.substring(0,z.length()-1);
    }
}
