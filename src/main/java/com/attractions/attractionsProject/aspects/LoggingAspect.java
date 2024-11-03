package com.attractions.attractionsProject.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.attractions.attractionsProject..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        String methods = Arrays.toString(methodArgs);

        logger.info("Calling method: {}#{} with arguments: {}", className, methodName, methods);
    }

    @AfterThrowing(pointcut = "execution(* com.attractions.attractionsProject..*(..))", throwing = "error")
    public void logMethodException(JoinPoint joinPoint, Throwable error) {
        String methodName = joinPoint.getSignature().getName();
        String stackTraceString = String.join(System.lineSeparator(),
                Arrays.stream(error.getStackTrace())
                        .map(element -> "\t" + element.toString())
                        .toArray(String[]::new));
        logger.error("Method {} threw exception: {}", methodName, error.getMessage());
        logger.error("{} \n {}", error.getClass().getName(), stackTraceString);
    }

    @After("execution(* com.attractions.attractionsProject..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        String methods = Arrays.toString(methodArgs);

        logger.debug("Executed method: {}#{} with arguments: {}", className, methodName, methods);
    }
}
