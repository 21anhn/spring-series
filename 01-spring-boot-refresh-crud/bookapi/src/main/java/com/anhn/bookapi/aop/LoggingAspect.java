package com.anhn.bookapi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.anhn.bookapi.service.*.*(..)))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("[BEFORE] Method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.anhn.bookapi.service.*.*(..)))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("[AFTER RETURNING] Method: {}, Returned: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.anhn.bookapi.service.*.*(..)))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("[EXCEPTION] Method: {}, Exception: {}", joinPoint.getSignature().getName(), ex.getMessage(), ex);
    }

    @After("execution(* execution(* com.anhn.bookapi.service.*.*(..)))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("[AFTER] Method: {}", joinPoint.getSignature().getName());
    }
}
