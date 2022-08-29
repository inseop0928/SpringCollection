package com.jis.springbootjpa.aop;

import com.jis.springbootjpa.aop.annotation.MethodAop;
import com.jis.springbootjpa.common.interceptor.AuthInterceptor;
import com.jis.springbootjpa.controller.ApiController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.assertj.core.api.Assertions;
import org.hibernate.mapping.Join;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(AOPTest.parameterAspect.class)
public class AOPTest {

    @Autowired
    ApiController apiController;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    AspectJExpressionPointcut  pointcut = new AspectJExpressionPointcut();

    Method method;

    @Before
    public void init() throws NoSuchMethodException {
        method = ApiController.class.getMethod("test", String.class);
    }

    @Test
    public void printMethod(){
        logger.info("method {}",method);
    }

    @Test
    public void exactMatch(){
        
        /*exectution(* *(..)) 필수요소 제외 생략한 포인트 컷(반환타입* 메소드명* 파라미터 여러개(..)
        메서드명 패턴 체크 exectution(* *es*(..))
        패키지명 패턴 제크 execution(* com.jis.springbootjpa.*.*.test(String))
        '.' 정확하게 해당 패키지 위치
        '..' 해당 위치의 패키지와 그 하위의 패키지
        하위패키지 모든 메서드,모든 파라미터 패턴 제크 execution(* com.jis.springbootjpa..*.*(..))
        * */

        //method public java.lang.String com.jis.springbootjpa.controller.ApiController.test(java.lang.String)
        pointcut.setExpression("execution(public String com.jis.springbootjpa.controller.ApiController.test(String))");

        Assertions.assertThat(pointcut.matches(method,ApiController.class)).isTrue();
    }


    /*
    * within은 표현식에 부모타입을 지정하면 안 된다.(execution과 차이)
    * 타입, 파라미터,메소드를 제외하고 클래스까지만 지정
    *  */
    @Test
    public void exactWithInMatch() throws NoSuchMethodException {
        Method checkAnnotation = AuthInterceptor.class.getDeclaredMethod("checkAnnotation", Object.class, Class.class);

        //execution(private boolean com.jis.springbootjpa.common.interceptor.AuthInterceptor.checkAnnotation(Object,*))
        pointcut.setExpression("within(com.jis.springbootjpa.common.interceptor.AuthInterceptor)");
        Assertions.assertThat(pointcut.matches(checkAnnotation,AuthInterceptor.class)).isTrue();

    }

    @Test
    public void parameterAOPTest(){
        //com.jis.springbootjpa.controller.ApiController
        //logger.info(ApiController.class.getCanonicalName());

        //ApiController apiController = new ApiController();
        apiController.test("testId");//일반생성자 생성이 아니라 bean을 주입해야 하단에 aop로직에 접근
    }

    @Aspect
    static class parameterAspect {

        private Logger logger = LoggerFactory.getLogger(this.getClass());
        @Pointcut("execution(* com.jis.springbootjpa.controller.ApiController.*(..))")
        private void paramChk(){}
        
        @Around("paramChk()")
        public Object paramChkAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
            logger.info("looger");
            for(Object arg : joinPoint.getArgs()){
                logger.info("advice {}",arg.toString());
            }
            return joinPoint.proceed();
        }

        @Around("paramChk() && args(arg,..)")//args(arg,..) 매개변수명을 맞춰야함
        public Object paramChkAdvice2(ProceedingJoinPoint joinPoint,Object arg) throws Throwable {
            logger.info("advice2 {}",arg.toString());

            return joinPoint.proceed();
        }

        @org.aspectj.lang.annotation.Before("paramChk() && args(arg,..)")//args(arg,..) 매개변수명을 맞춰야함
        public void paramChkAdvice3(JoinPoint joinPoint, Object arg){
            logger.info("advice3 {}",arg);
        }

        //this 스프링 컨테이너에 들어있는 즉 프록시 객체(빈)를 출력
        @org.aspectj.lang.annotation.Before("paramChk() && this(obj)")
        public void thisAdvice(JoinPoint joinPoint,ApiController obj) {
            logger.info("this {} {}",joinPoint.getSignature(),obj.getClass());
        }

        //target은 실제 호출한 대상을 출력
        @org.aspectj.lang.annotation.Before("paramChk() && target(obj)")
        public void targetAdvice(JoinPoint joinPoint,ApiController obj){
            logger.info("target {} {}",joinPoint.getSignature(),obj.getClass());
        }

        @org.aspectj.lang.annotation.Before("paramChk() && @target(annotation)")//애노테이션 전달
        public void atTargetAdvice(JoinPoint joinPoint, MethodAop annotation){
            logger.info("atTarget {} {}",joinPoint.getSignature(),annotation);
        }

        @org.aspectj.lang.annotation.Before("paramChk() && @within(annotation)")//애노테이션 전달
        public void atWithAdvice(JoinPoint joinPoint,MethodAop annotation){
            logger.info("atWith {} {}",joinPoint.getSignature(),annotation);
        }

        @org.aspectj.lang.annotation.Before("paramChk() && @annotation(annotation)")//애노테이션 값 전달
        public void atAnnotationAdvice(JoinPoint joinPoint,MethodAop annotation){
            logger.info("atAnnotation {} {}",joinPoint.getSignature(),annotation.value());
        }
    }
}