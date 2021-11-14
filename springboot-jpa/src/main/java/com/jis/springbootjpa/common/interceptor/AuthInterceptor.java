package com.jis.springbootjpa.common.interceptor;

import com.jis.springbootjpa.aop.Auth;
import com.jis.springbootjpa.common.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();

        URI uri = UriComponentsBuilder.fromUriString(request.getRequestURI())
                .query(request.getQueryString())
                .build()
                .toUri();

        log.info("-----prehandle----");
        log.info("url : {}",url);
        boolean hasAnnotation = checkAnnotation(handler, Auth.class);
        log.info("auth : {}",hasAnnotation);

        if(hasAnnotation){
            //권한체크
            String query = uri.getQuery();
            log.info("query : {}",query);

            throw new AuthException("권한없음");
        }

        return true;
    }

    private boolean checkAnnotation(Object handler,Class claszz){

        //resource js, html
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;

        if(null != handlerMethod.getMethodAnnotation(claszz) || null != handlerMethod.getBeanType().getAnnotation(claszz)){

            //Auth annotation이 있을때 true
            return true;

        }

         return false;

    }
}
