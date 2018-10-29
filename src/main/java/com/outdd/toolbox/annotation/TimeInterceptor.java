package com.outdd.toolbox.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
/*
 * TODO: 注解类，统计方法耗时
 * @author VAIE
 * @date: 2018/9/30-14:59
 * @version v1.0
 */


/**
 *检测方法执行耗时的spring切面类 *
 *使用@Aspect注解的类，Spring将会把它当作一个特殊的Bean（一个切面），也就是不对这个类本身进行动态代理
 *@author blinkfox
 *@date 2016-07-04
 */
@Aspect
@Component
public class TimeInterceptor {
    public TimeInterceptor() {
        System.out.println("開啟aop");
    }

    /**
     * 监控com.henry.advertising.web.service包及其子包的所有public方法
     * &lt;功能详细描述&gt;
     * @see [类、类#方法、类#成员]
     */
    @Pointcut("execution(* com.outdd.ai.excel..*.*(..))")
    private void pointCutMethod() {
    }

    //声明环绕通知
    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object o = pjp.proceed();
        long end = System.currentTimeMillis();
        String methodName=pjp.getTarget().getClass()+"."+pjp.getSignature().getName();
        String time=(end-begin)/1000+"s\t"+(end-begin)+"ms";
        System.out.println("统计方法名称："+methodName);
        System.out.println("共耗时："+time);
        return o;
    }

}

