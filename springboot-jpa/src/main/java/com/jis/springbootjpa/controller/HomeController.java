package com.jis.springbootjpa.controller;


import com.jis.springbootjpa.aop.Login;
import com.jis.springbootjpa.common.session.SessionManager;
import com.jis.springbootjpa.constant.SessionConst;
import com.jis.springbootjpa.domain.Member;
import com.jis.springbootjpa.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    private final SessionManager sessionManager;

    //@GetMapping("/")
    public String home(){
        return "home";
    }

    /*
    * 스프링에서 제공하는 SessionAttribute를 사용용
   * */
    @GetMapping("/")
    public String login(@Login Member loginMember, Model model){

        if(loginMember == null){
            return "home";
        }

        model.addAttribute("member",loginMember);
        return "login/loginHome";
    }

    //Spring session을 이용
    public String login2(@SessionAttribute(name = SessionConst.LOGIN_MEMBER_KEY, required = false) Member loginMember, Model model){

        if(loginMember == null){
            return "home";
        }

        model.addAttribute("member",loginMember);
        return "login/loginHome";
    }

    //세션관리자를 만들어서 사용
    //@GetMapping("/")
    public String login3(HttpServletRequest request,  Model model){
        //세션관리자에서 우선 조회
        //Member sessionMember = (Member)sessionManager.getSession(request);
        Object sessionMember = sessionManager.getSession(request);

        if(sessionMember == null){
            return "home";
        }
        model.addAttribute("member",sessionMember);
        return "login/loginHome";
    }

    //쿠키를 이용
    //@GetMapping("/")
    public String login4(@CookieValue(name="memberId",required = false) String memberId, Model model){

        if(memberId == null){
            return "home";
        }else{
            Optional<Member> memberOptional = memberRepository.findByLoginId(memberId);
            if(null == memberOptional.get()){
                return "home";
            }else{

                model.addAttribute("member",memberOptional.get());

                return "login/loginHome";
            }
        }
    }

    /*
        httpSession을 이용
     */
    public String login5(HttpServletRequest request,  Model model){

        HttpSession session = request.getSession(false);

        if(session == null){
            return "home";
        }

        Member sessionMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER_KEY);

        if(sessionMember == null){
            return "home";
        }
        model.addAttribute("member",sessionMember);
        return "login/loginHome";
    }

}
