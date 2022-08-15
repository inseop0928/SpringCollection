package com.jis.springbootjpa.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component//spring에서 관리
public class LogAspect {

    //포인트컷 설정(어노테이션 설정없이 동작)
    @Pointcut("execution(* com.jis.springbootjpa..*.*(..))")
    private void cut() {}//pointcut signature

    @Before("cut()")
    public void before(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("method : " + methodSignature.getMethod());

        Object[] args = joinPoint.getArgs();
        for (Object obj : args) {
            //System.out.println("type : " + obj.getClass().getSimpleName());
            System.out.println("object " + obj);
        }
    }

    @AfterReturning(value = "cut()", returning = "rtnObj")
    public void after(JoinPoint joinPoint, Object rtnObj) {

    }
}
