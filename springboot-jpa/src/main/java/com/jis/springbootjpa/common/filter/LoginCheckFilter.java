package com.jis.springbootjpa.common.filter;

import com.jis.springbootjpa.constant.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private String[] chkExceptList = {"/","/member/add","/login","/logout","/error-page/*","/error/*","/css/*"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        try {
            if(isLoginCheckPath(requestURI)){
                log.info("login check start {}",requestURI);
                HttpSession httpSession = httpRequest.getSession(false);

                if(httpSession == null || httpSession.getAttribute(SessionConst.LOGIN_MEMBER_KEY) == null){
                    log.info("no login");

                    //현재페이지를 리다이렉트로 포함해서 넘겨줌
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI);
                    return;
                }
            }

            filterChain.doFilter(httpRequest,httpResponse);
        }catch (Exception e){
            throw e;
        }
    }

    private boolean isLoginCheckPath(String requestURI){
        //패턴체크
        return !PatternMatchUtils.simpleMatch(chkExceptList,requestURI);
    }

}
