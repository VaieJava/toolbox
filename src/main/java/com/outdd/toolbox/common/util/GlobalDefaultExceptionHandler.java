package com.outdd.toolbox.common.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/*
 * TODO: 全局异常捕获
 * @author VAIE
 * @date: 2018/10/19-22:03
 * @version v1.0
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody   // 如果返回String或json要加此注解如果返回界面就不加
    public String defaultExceptionHandler(HttpServletRequest req, Exception  e) {

        e.printStackTrace();
        // 返回String
        return "对不起服务器繁忙";

        // 返回View 创建ModelAndView  mv = new ModelAndView("error")
    }

}
