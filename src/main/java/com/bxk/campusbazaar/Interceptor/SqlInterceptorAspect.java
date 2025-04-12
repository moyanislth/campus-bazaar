package com.bxk.campusbazaar.Interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Aspect
@Component
public class SqlInterceptorAspect {

    private static final Logger logger = LoggerFactory.getLogger(SqlInterceptorAspect.class);

    // 拦截所有Mapper接口的方法
    @Pointcut("execution(* com.bxk.campusbazaar.mapper..*.*(..))")
    public void mapperMethods() {}

    // 方法执行前打印参数
    @Before("mapperMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("准备执行方法: {}", joinPoint.getSignature().toShortString());
        logger.info("方法参数: {}", Arrays.toString(joinPoint.getArgs()));
    }

    // 方法执行后打印结果
    @AfterReturning(pointcut = "mapperMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("方法执行完成: {}", joinPoint.getSignature().toShortString());
        if (result != null) {
            logger.info("返回结果类型: {}", result.getClass().getSimpleName());
            if (result instanceof java.util.Collection) {
                logger.info("返回结果数量: {}", ((java.util.Collection<?>) result).size());
            }
        }
    }
}