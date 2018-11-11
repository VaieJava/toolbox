package com.outdd.toolbox.common.util;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * TODO: 伪静态配置过滤器
 * @author VAIE
 * @date: 2018/11/11-15:58
 * @version v1.0
 */
@Configuration
public class CustomerFilter {
    @Bean
    public FilterRegistrationBean urlRewrite(){
        UrlRewriteFilter rewriteFilter=new UrlRewriteFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean(rewriteFilter);
        registration.setUrlPatterns(Arrays.asList("/*"));
        Map initParam=new HashMap();
        initParam.put("confPath", "urlrewirte.xml");//配置文件地址
        initParam.put("infoLevel","info");
        registration.setInitParameters(initParam);
        return  registration;
    }

}
