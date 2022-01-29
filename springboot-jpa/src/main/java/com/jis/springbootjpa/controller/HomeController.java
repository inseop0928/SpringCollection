package com.jis.springbootjpa.controller;


import com.jis.springbootjpa.domain.Member;
import com.jis.springbootjpa.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    //@GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/")
    public String login(@CookieValue(name="memberId",required = false) String memberId, Model model){

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

}
