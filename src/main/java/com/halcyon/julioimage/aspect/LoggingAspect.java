package com.halcyon.julioimage.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.halcyon.julioimage.service.*.*.*(..))")
    public void logServiceMethodCall(JoinPoint joinPoint) {
        logger.info(String.format(
                "Method %s called with arguments %s",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())
        ));
    }

    @AfterReturning(pointcut = "execution(* com.halcyon.julioimage.service.*.*.*(..))", returning = "result")
    public void logServiceMethodReturn(JoinPoint joinPoint, Object result) {
        logger.info(String.format(
                "Method %s returned %s",
                joinPoint.getSignature().getName(),
                result
        ));
    }

    @AfterThrowing(pointcut = "execution(* com.halcyon.julioimage.service.*.*.*(..))", throwing = "ex")
    public void logServiceMethodThrow(JoinPoint joinPoint, Exception ex) {
        logger.info(String.format(
                "Method %s throw exception %s",
                joinPoint.getSignature().getName(),
                ex.getMessage()
        ));
    }
}