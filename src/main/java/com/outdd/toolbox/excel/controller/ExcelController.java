package com.outdd.toolbox.excel.controller;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.common.util.dataSource.DataSourceConfig;
import com.outdd.toolbox.excel.ExcelUtil;
import com.outdd.toolbox.excel.service.ExcelService;
import com.outdd.toolbox.excel.thread.ExcelMysqlThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * TODO: excel控制器
 * @author VAIE
 * @date: 2018/10/13-12:59
 * @version v1.0
 */
@Controller
@RequestMapping("/excel/")
@Slf4j
public class ExcelController {
    @Autowired
    ExcelService excelService;
    @Autowired
    DataSourceConfig dataSourceConfig;
    static String  path="C:\\Users\\Administrator\\Desktop\\01.xlsx";

    ExecutorService es =Executors.newCachedThreadPool();//线程池
    @Autowired
    DataSourceProperties dataSourceProperties;

    @Autowired
    ApplicationContext applicationContext;

    String uuid="";
    @RequestMapping("index")
    public String index(ModelMap map, HttpSession httpSession){
        uuid=CommomUtil.uuid();
        //创建session对象
        //把用户数据保存在session域对象中
        httpSession.setAttribute("loginName", uuid);
        map.put("ver", uuid);
        return "excelIndex";
    }

    @RequestMapping("excelup")
    public String excelup(ModelMap map, HttpSession httpSession){

        return "excelUp";
    }
    /**
     * 文件上传
     */
    @RequestMapping(value = "upload")
    @ResponseBody
    public String uploadExcel(@RequestParam("file") MultipartFile file) {
        String restule="ok";
        File f = null;
        try {
            if(file.equals("")||file.getSize()<=0){
                file = null;
            }else{
                InputStream ins = file.getInputStream();
                f=new File(file.getOriginalFilename());
                ExcelUtil.inputStreamToFile(ins, f);
            }

            ExcelUtil ex=new ExcelUtil();
            boolean s=f.isFile();
            uploading(ex.readExcel(f));
        } catch (Exception e) {
            e.printStackTrace();
            restule="err";
        }finally {
            File del = new File(f.toURI());
            del.delete();

        }
        return restule;
    }

    @ResponseBody
    @RequestMapping("testOn")
    public String testOn(){

        return "服务器在线。";
    }


    @ResponseBody
    @RequestMapping("detectionDataSource")
    public String detectionDataSource() {
        String result="连接数据库成功";
        // 获取配置的数据源
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        Connection con=null;
        try {
            con=dataSource.getConnection();
        }catch (SQLException e){
            System.out.println("数据连接异常");
            result="数据连接异常";
            result+="\n异常信息："+e.toString();
        }finally {
            if(con!=null){
                System.out.println(con);
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
        }
        return result;
    }

    @RequestMapping("update")
    public String update(@RequestParam Map<String,Object> dbMap, ModelMap map, HttpSession httpSession, HttpServletRequest req, HttpServletResponse response){
        String ip=CommomUtil.getIpAddress(req);
        try {
            CommomUtil.getPort(req,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ip="+ip);
        Object loginName=httpSession.getAttribute("loginName");
        String session="";
        if(loginName!=null){
            session=loginName.toString();
            httpSession.removeAttribute("loginName");
        }
        System.out.println("session="+session);
        if(!session.equals(dbMap.get("ver").toString())){
            map.put("info","不能重复提交");
            return "dataSource";
        }
        try {
            PropertiesConfiguration conf = new PropertiesConfiguration("db.properties");
            conf.setProperty("driverClassName",dbMap.get("driverClassName"));
            conf.setProperty("url",dbMap.get("url"));
            conf.setProperty("username",dbMap.get("username"));
            conf.setProperty("password",dbMap.get("password"));
            conf.save();
            System.out.println("driverClassName="+dbMap.get("driverClassName")+",url="+dbMap.get("url"));
            dataSourceConfig.changeDataSource();
        } catch (ConfigurationException e) {
            log.error("获取数据库配置文件失败：" + e);
            map.put("info","获取数据库配置文件失败");
        }
        map.put("info","设置数据库配置成功");

        map.put("driverClassName",dbMap.get("driverClassName"));
        map.put("url",dbMap.get("url"));
        map.put("username",dbMap.get("username"));
        map.put("password",dbMap.get("password"));
        return "excelIndex";
    }


    /**
     * TODO: 导入excle到数据库
     * @param
     * @return: java.lang.String
     * @auther: vaie
     * @date: 2018/10/19 16:35
     */
    public String uploading(Map<String,List<JSONObject>> sheetMap){


        String asd="id varchar(255) NOT NULL,age varchar() NOT NULL";
//        operateTableMapper.createNewTable("asdas",asd);
        String resls="执行成功";
        ExcelUtil ex=new ExcelUtil();
        try {
            File fiel=new File(path);
            int threadNum=10;
            int openThcount= (int) Math.ceil((double)sheetMap.size()/threadNum);//需要开启的线程数量
            CountDownLatch doneSignal = new CountDownLatch(openThcount);//使用计数栅栏
            final CyclicBarrier cb = new CyclicBarrier(openThcount);//号令枪

            System.out.println("读取成功");
            Iterator<Map.Entry<String,List<JSONObject>>> it = sheetMap.entrySet().iterator();
            System.out.println("开始执行迭代");
            List<Map.Entry<String,List<JSONObject>>> list = new ArrayList<Map.Entry<String,List<JSONObject>>>();
            boolean fail=false;
                while (it.hasNext()) {
                         Map.Entry<String,List<JSONObject>> entry = it.next();
                         if(openThcount > 4){
                             list.add(entry);
                             if(list.size() == threadNum-1){
                                 fail=true;
                                 es.submit(new ExcelMysqlThread(doneSignal,cb,excelService,list));
                                 list=new ArrayList<Map.Entry<String,List<JSONObject>>>();
                             }
                         }else{
                             String fields="";
                             for (Map.Entry<String, Object> entryz : entry.getValue().get(0).entrySet()) {
                                 fields+=" "+entryz.getKey()+" varchar(255),";
                             }
                             fields=fields.substring(0,fields.length()-1);
                             excelService.createNewTable(entry.getKey(),fields);
                             excelService.insert(entry.getKey(),entry.getValue());
                             fail=true;
                         }


                    }
                    if(fail){
                        if(openThcount > 4){
                            doneSignal.await();//线程等待
                            es.shutdown();//线程关闭
                        }
                    }else {
                        resls="执行失败";
                    }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resls;
    }


}
