package com.jis.springbootjpa.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointCut {

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    @Pointcut("execution(* *..*Controller.*(..))")
    public void allController(){}

    @Pointcut("allController() && allService()")
    public void allBusiness(){}
}
