package com.eicas.cms.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ArticleStatisticsAspect {


    // 定义切点 ArticleStatisticsPointCut
    @Pointcut("@annotation(com.eicas.cms.annotation.ArticleStatistics)")
    public void ArticleStatisticsPointCut(){};

    // 环绕通知
    @Around("ArticleStatisticsPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint){
        System.out.println("-----------------------------------");
        // 获取方法名称
        String methodName = joinPoint.getSignature().getName();
        // 获取入参
        Object[] param = joinPoint.getArgs();

        StringBuilder sb = new StringBuilder();
        for(Object arg : param){
            System.out.println("arg ==>" + arg);
            sb.append(arg + "; ");
        }
        System.out.println("进入[" + methodName + "]方法,参数为:" + sb.toString());

        Object proceed = null;
        try {
            // 继续执行方法
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println(methodName + "方法执行结束");
        return proceed;
    }
}