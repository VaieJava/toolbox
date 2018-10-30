package com.outdd.toolbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ToolboxApplication {

    private static String[] args;
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(ToolboxApplication.class, args);
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

}