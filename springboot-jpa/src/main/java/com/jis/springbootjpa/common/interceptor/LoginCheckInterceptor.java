package com.jis.springbootjpa.common.interceptor;

import com.jis.springbootjpa.constant.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//인증 컨트롤러 전에 호출
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("LoginCheckInterceptor [{}]",requestURI);

        HttpSession session = request.getSession();

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER_KEY) == null){
            log.info("no login");

            //현재페이지를 리다이렉트로 포함해서 넘겨줌
            response.sendRedirect("/login?redirectURL="+requestURI);
            return false;
        }
        return false;
    }
}
