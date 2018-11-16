package com.outdd.toolbox;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Properties;

@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages = "com.outdd.toolbox.**.dao")
public class ToolboxApplication extends SpringBootServletInitializer {

    private static String[] args;
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(ToolboxApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ToolboxApplication.class);
    }

    /**
     * TODO: 重启服务器方法
     * @param
     * @return: void
     * @auther: vaie
     * @date: 2018/10/28 13:59
     */

    public static void restart() {
        context.close();
        ToolboxApplication.context = SpringApplication.run(ToolboxApplication.class, args);
    }
    @Controller
    @RequestMapping("/")
    private class indexcon{
        @RequestMapping("/")
        public String index(){

            return "index";
        }
    }

//    //配置mybatis的分页插件pageHelper
//    @Bean
//    public PageHelper pageHelper(){
//        PageHelper pageHelper = new PageHelper();
//        Properties properties = new Properties();
//        properties.setProperty("offsetAsPageNum","true");
//        properties.setProperty("rowBoundsWithCount","true");
//        properties.setProperty("reasonable","true");
//        properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
//        pageHelper.setProperties(properties);
//        return pageHelper;
//    }



}
