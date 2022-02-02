package com.jis.springbootjpa.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/sessionInfo")
    public String sessionInfo(HttpServletRequest request){
        HttpSession httpSession = request.getSession();

        if(httpSession == null){
            return "세션이 없습니다.";
        }
        
        //루프로 돌리면서 세션 출력
        httpSession.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("name ={}, value={}",name,httpSession.getAttribute(name)));

        log.info("sessionId ={}",httpSession.getId());
        log.info("getMaxInactiveInterval ={}",httpSession.getMaxInactiveInterval());//세션 유효시간
        log.info("getCreationTime ={}",new Date(httpSession.getCreationTime()));
        log.info("getLastAccessedTime ={}",new Date(httpSession.getLastAccessedTime()));//세션과 연결된 사용자가 최근에 서버에 접근한 시간
        log.info("isNew ={}",httpSession.isNew());//새로 생성된 세션인지 아니면 과거에 만들어졌던 세션인지
        return "세션이 존재합니다.";
    }

}
