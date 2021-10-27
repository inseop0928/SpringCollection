package com.jis.springbootjpa.aop;

import com.jis.springbootjpa.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Base64;

@Slf4j
@Aspect
@Component//class에 빈생성
public class DecodeAspect {

    //포인트컷 설정(어노테이션 설정없이 동작)
    @Pointcut("execution(* com.jis.springbootjpa..*.*(..))")
    private void cut() {
    }

    @Pointcut("@annotation(com.jis.springbootjpa.aop.Decode)")
    private void decode(){
    }

    @Before("cut() && decode()")
    public void before(JoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        for(int i=0;i<args.length;i++){
            log.info("parameter"+i+" : {}",args[i]);

            if(args[i] instanceof UserDto){
                
                UserDto user = UserDto.class.cast(args[i]);//형변환

                //ex)aW5zZW9wQGdtYWlsLmNvbQ==
                String email = new String(Base64.getDecoder().decode(user.getUserEmail()),"UTF-8");//디코딩
                user.setUserEmail(email);
            }
        }
    }

    
    //returning 파라미터로 리턴받을 객체를 생성해줌
    @AfterReturning(value = "cut() && decode()" ,returning = "returnObj")
    public void after(JoinPoint joinPoint, Object returnObj) throws Throwable {

        if(returnObj instanceof UserDto){
            UserDto user = UserDto.class.cast(returnObj);//형변환
            String email = Base64.getEncoder().encodeToString(user.getUserEmail().getBytes());//인코딩
            user.setUserEmail(email);
        }
    }
}
