package com.jis.springbootjpa.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
