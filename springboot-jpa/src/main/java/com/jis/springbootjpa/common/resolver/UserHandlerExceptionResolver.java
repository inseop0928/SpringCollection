package com.jis.springbootjpa.common.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jis.springbootjpa.common.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        try {
            if(e instanceof UserException){
                log.info("UserException resolver to 400");
                String acceptHeader = httpServletRequest.getHeader("accept");
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                if("application/json".equals(acceptHeader)){
                    Map<String,Object> errorResult = new HashMap<>();
                    errorResult.put("ex",e.getClass());
                    errorResult.put("message",e.getMessage());
                    ObjectMapper objectMapper = new ObjectMapper();
                    String result = objectMapper.writeValueAsString(errorResult);

                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("utf-8");
                    httpServletResponse.getWriter().write(result);
                    return new ModelAndView();

                }else{
                    return new ModelAndView("error/500");
                }

            }
        } catch (Exception e1) {
            log.error(e1.getMessage());
            e1.printStackTrace();
        }


        return null;
    }
}
