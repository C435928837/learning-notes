package com.cloudmall.demo.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpringAopDemo {
    @Pointcut("execution(* com.cloudmall.demo.spring.ProductService.*(..))")
    public void productServiceMethods() {
    }

    @Before("productServiceMethods()")
    public void logBefore(JoinPoint joinPoint){
        System.out.println("调用方法：" + joinPoint.getSignature().getName());
    }

    @Around("productServiceMethods()")
    public Object methodsTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            System.out.println("方法耗时：" + (System.currentTimeMillis() - startTime));
        }
    }
}
