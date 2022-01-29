package com.jis.springbootjpa.controller;


import com.jis.springbootjpa.domain.Member;
import com.jis.springbootjpa.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.awt.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberRepository memberRepository;


    @GetMapping("/add")
    public String addForm(@ModelAttribute Member member){
        return "member/addMemberForm";
    }

    @PostMapping("/add")
    public String addForm(@Valid @ModelAttribute("member")Member member, BindingResult bindingResult){
        if(bindingResult.hasErrors()){

            return "member/addMemberForm";
        }

        memberRepository.save(member);
        return "redirect:/";
    }


}
