package com.jis.springbootjpa.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private final String LOG_ID ="logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("preHandler");

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID,uuid);

        //@RequestMapping, @Controller : HandlerMethod
        //정적 리소스 : ResourceHttpRequestHandler
        if( handler instanceof HandlerMethod){
            HandlerMethod handlerMethod =(HandlerMethod) handler;//호출할 컨트롤러 메서드의 모든 정보가 포함되어있다.
        }
        log.info("REQEUST [{}] [{}] [{}]",uuid,requestURI,handler);
        return true;//false이면 여기서 끝남
    }
    
    
    //예외가 발생하는 경우에는 동작 안함 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandler [{}]" ,modelAndView);
    }

    //예외가 발생하는 경우에도 동작
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        Object logId = request.getAttribute(LOG_ID);
        String requestURI = request.getRequestURI();
        
        log.info("REQEUST [{}] [{}] [{}]",logId,requestURI,handler);
        
        //에러가 있는경우 에러 반환
        if(ex != null){
            log.error("afterCompletion error " ,ex);
        }
    }
}
