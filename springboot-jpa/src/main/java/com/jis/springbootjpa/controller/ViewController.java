package com.jis.springbootjpa.controller;


import com.jis.springbootjpa.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/thymeleaf-index")
    public String thymeleafIndex(){
        return "/thymeleaf/thymeleaf-index";
    }

    @GetMapping("/thymeleaf-text-basic")
    public String thymeleafTextBasic(Model model){
        model.addAttribute("data","hello Spring");
        model.addAttribute("escapeData","hello <b>Spring</b>");

        return "/thymeleaf/text-basic";
    }

    @GetMapping("/variable")
    public String variable(Model model){

        return "/thymeleaf/variable";
    }

    @GetMapping("/basic-objects")
    public String basicObjects(HttpSession httpSession){
        httpSession.setAttribute("sessionData","hello Session");
        return "/thymeleaf/basic-objects";
    }

    @GetMapping("/javascript")
    public String javascript(Model model){

        Map<String,Object> user = new HashMap<String,Object>();
        user.put("username","helloUser");
        user.put("age",10);
        model.addAttribute("user",user);
        return "/thymeleaf/javascript";
    }

    @GetMapping("/fragment")
    public String template(){
        return "/template/thymeleaf/fragment/fragmentMain";
    }

    @GetMapping("/layout")
    public String layout(){
        return "/template/thymeleaf/layout/layoutMain";
    }

    @GetMapping("/layoutExtend")
    public String layoutExtends() {
        return "/template/thymeleaf/layout/layoutExtendMain";
    }

    @Component("helloBean")
    static class HelloBean{
        public String hello(String data){
            return "Hello";
        }
    }
}
