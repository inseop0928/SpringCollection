package com.jis.springbootjpa.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component//class에 빈생성
public class TimerAspect {

    //포인트컷 설정(어노테이션 설정없이 동작)
    @Pointcut("execution(* com.jis.springbootjpa..*.*(..))")
    private void cut() {
    }

    @Pointcut("@annotation(com.jis.springbootjpa.aop.Timer)")
    private void timer(){
    }

    @Around("cut() && timer()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        log.info("name : {}",joinPoint.getSignature().getName());

        //로직 실행
        joinPoint.proceed();

        stopWatch.stop();

        log.info("total time : {}",stopWatch.getTotalTimeSeconds());
    }
}
