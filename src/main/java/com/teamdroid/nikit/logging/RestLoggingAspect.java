package com.teamdroid.nikit.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
public class RestLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(RestLoggingAspect.class);

    @Around("execution(* com.teamdroid.nikit.controller..*(..))")
    public Object logRestCall(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        String httpMethod = request != null ? request.getMethod() : "N/A";
        String path = request != null ? request.getRequestURI() : "N/A";
        Object[] args = joinPoint.getArgs();

        TDLogger.log(logger, TDLogger.Level.INFO, new StructuredLogEvent("IN", httpMethod, path, className, methodName, args));

        Object result = joinPoint.proceed();

        TDLogger.log(logger, TDLogger.Level.INFO, new StructuredLogEvent("OUT", httpMethod, path, className, methodName, result));

        return result;
    }
}