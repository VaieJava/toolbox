package com.outdd.toolbox.common.util;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/*
 * TODO: spring boot 配置mvc 后缀访问
 * @author VAIE
 * @date: 2018/11/11-17:58
 * @version v1.0
 */

@Configuration
public class CustomwebMvcConfigurer  implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseRegisteredSuffixPatternMatch(true);
    }
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                dispatcherServlet);
        registration.addUrlMappings("*.do");
        registration.addUrlMappings("*.html");
        registration.addUrlMappings("*.css");
        registration.addUrlMappings("*.js");
        registration.addUrlMappings("*.png");
        registration.addUrlMappings("*.gif");
        registration.addUrlMappings("*.ico");
        registration.addUrlMappings("*.jpeg");
        registration.addUrlMappings("*.jpg");
        return registration;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置模板资源路径
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        // 不可以下面方式操作，如果那样写是不可以的。这样等于是创建了多个Interceptor。而不是只有一个Interceptor
//        addInterceptor.excludePathPatterns("/login");
//        addInterceptor.excludePathPatterns("/login/main");
//        //拦截所有路径
//       addInterceptor.addPathPatterns("/**");
        // addPathPatterns("/**")对所有请求都拦截，但是排除了/login/main和/login请求的拦截
        registry.addInterceptor(new LocaleChangeInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**","/login/main");
    }


}
