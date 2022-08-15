package com.jis.springbootjpa.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TestAspect {

    //around에 어노테이션 값은 포인트컷
    //around에 메서드인 doLog는 어드바이스
    @Around("execution(* com.jis.springbootjpa.controller..*(..))")//..은 하위 패키지
    public Object doLog(ProceedingJoinPoint joinPoint)throws Throwable {
        log.info("[LOG] {}",joinPoint.getSignature());//조인포인트 시그니처 : 메서드 정보가 출력됨
        return joinPoint.proceed();//다음 어드바이스나 타겟을 호출
    }

    @Around("com.jis.springbootjpa.pointcut.CommonPointCut.allService()")//&&,||,! 으로 조합이 가능
    public Object doTranscation(ProceedingJoinPoint joinPoint) throws Throwable{

        try{
            Object rtn = joinPoint.getSignature();
            log.info("commit");
            joinPoint.proceed();
            return rtn;
        }catch (Exception e){
            log.info("rollback");
            throw e;
        }finally {

        }
    }

    @Before("com.jis.springbootjpa.pointcut.CommonPointCut.allService()")
    public void bfDoTransaction(ProceedingJoinPoint joinPoint){
        log.info("before {}",joinPoint.getSignature());
    }

    @AfterReturning(value = "com.jis.springbootjpa.pointcut.CommonPointCut.allService()",returning = "result")
    public void rtnDoTransaction(ProceedingJoinPoint joinPoint,Object result){//returning과 맞춰줘야한다.
        log.info("before {}",joinPoint.getSignature());
    }

    @AfterThrowing(value = "com.jis.springbootjpa.pointcut.CommonPointCut.allService()",throwing = "ex")
    public void exDoTransaction(ProceedingJoinPoint joinPoint,Exception ex){//returning과 맞춰줘야한다.
        log.info("before {}",joinPoint.getSignature());
    }

    @After("com.jis.springbootjpa.pointcut.CommonPointCut.allService()")
    public void afDoTransaction(ProceedingJoinPoint joinPoint){
        log.info("after {}",joinPoint.getSignature());
    }

}
