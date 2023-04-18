package com.jis.springbootjpa.advice;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        
        log.info("time proxy 실행");

        long startTime = System.currentTimeMillis();

        Object proceed = invocation.proceed();

        long endTime = System.currentTimeMillis();

        log.info("running time {}",endTime-startTime);


        return proceed;
    }
}
