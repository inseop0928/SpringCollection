package com.jis.springbootjpa.common.session;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {


    //concurrentHashMap 동시에 접근 이슈
    private Map<String,Object> sessionStore = new ConcurrentHashMap<>();


    public void createSession(Object value, HttpServletResponse response){

        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId,value);

        Cookie cookie = new Cookie("sessionId",sessionId);
        response.addCookie(cookie);
    }

    public Object getSession(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if(cookies == null){
            return null;
        }
        return Arrays.stream(cookies).filter(s->s.getName().equals("sessionId"))
                .findAny().orElse(null);
    }

    public void sessionExpire(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        Cookie cookie = Arrays.stream(cookies).filter(s->s.getName().equals("sessionId"))
                .findAny().orElse(null);

        if(cookie !=null){
            sessionStore.remove(cookie.getValue());
        }
    }
}
