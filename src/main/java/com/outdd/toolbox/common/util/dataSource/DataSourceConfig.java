package com.outdd.toolbox.common.util.dataSource;

import com.alibaba.druid.pool.DruidDataSource;

import com.outdd.toolbox.ToolboxApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/*
 * TODO: 数据源配置管理。
 * @author VAIE
 * @date: 2018/10/19-17:02
 * @version v1.0
 */
@Slf4j
@Configuration
public class DataSourceConfig {
    //数据库类型
    private String dbType;

    @Bean(name = "dataSource")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        try {
            PropertiesConfiguration properties = new PropertiesConfiguration("db.properties");
            System.out.println("改变的"+properties.getString("url"));
            String driverClassName = properties.getString("driverClassName");
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(properties.getString("url"));
            dataSource.setUsername(properties.getString("username"));
            dataSource.setPassword(properties.getString("password"));
            dataSource.setTestWhileIdle(true);
            dataSource.setTestOnBorrow(false);
            dataSource.setTestOnReturn(false);
            dataSource.setName("mybatis_test");//设置名称
            //捕获异常
            dataSource.setFilters("stat");
            dataSource.setMaxActive(20);
            dataSource.setPoolPreparedStatements(false);
            dataSource.setInitialSize(5);
            dataSource.setMinIdle(1);
            //关闭自动重连
            dataSource.setConnectionErrorRetryAttempts(0);
            dataSource.setBreakAfterAcquireFailure(true);
            //判断数据库类型
            if(driverClassName.contains("mysqldb")){
                this.setDbType("mysqldb");
            }else if(driverClassName.contains("oracledb")){
                this.setDbType("oracledb");
            }

        } catch (Exception e) {
            log.error("获取数据库配置文件失败" + e);
        }

        return dataSource;
    }

    /**
     * 动态修改数据库链接
     */
    public void changeDataSource () {
        Thread restartThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    ToolboxApplication.restart();
                } catch (InterruptedException ignored) {
                }
            }
        });
        restartThread.setDaemon(false);
        restartThread.start();
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType){
        this.dbType = dbType;
    }
}
