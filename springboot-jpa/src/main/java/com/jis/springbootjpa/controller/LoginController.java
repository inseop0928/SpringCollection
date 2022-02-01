package com.jis.springbootjpa.controller;


import com.jis.springbootjpa.common.session.SessionManager;
import com.jis.springbootjpa.constant.SessionConst;
import com.jis.springbootjpa.domain.Member;
import com.jis.springbootjpa.dto.LoginDto;
import com.jis.springbootjpa.sevice.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.http.HttpResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm")LoginDto loginDto){
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDto loginDto, BindingResult bindingResult, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginDto.getLoginId(),loginDto.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail","아이디 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
        
        //세션이 있으면 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_KEY,loginMember);
        
        return "redirect:/";
    }
    
    /* 쿠키를 만들어서 사용
        단점 : 보안에 취약
    */
    public String login2(@Valid @ModelAttribute LoginDto loginDto, BindingResult bindingResult, HttpServletResponse httpServletResponse){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginDto.getLoginId(),loginDto.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail","아이디 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 시 쿠키생성
        Cookie cookie = new Cookie("memberId",loginMember.getLoginId());
        httpServletResponse.addCookie(cookie);

        return "redirect:/";
    }

    
    /*
    SessionStorege를 만들어서 사용
    * */
    public String login3(@Valid @ModelAttribute LoginDto loginDto, BindingResult bindingResult, HttpServletResponse httpServletResponse){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginDto.getLoginId(),loginDto.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail","아이디 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        sessionManager.createSession(loginMember,httpServletResponse);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession(false);

        if(httpSession != null){
            httpSession.invalidate();
        }
        return "redirect:/";
    }


//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request){
//        sessionManager.sessionExpire(request);
//        return "redirect:/";
//    }

    //@PostMapping("/logout")
    //public String logout(HttpServletResponse response){
    //    Cookie cookie = new Cookie("memberId",null);
    //    cookie.setMaxAge(0);//유효시간을 없애줌
    //    response.addCookie(cookie);
    //    return "redirect:/";
    //}
}
