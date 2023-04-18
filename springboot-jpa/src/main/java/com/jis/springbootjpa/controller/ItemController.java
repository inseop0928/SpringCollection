package com.jis.springbootjpa.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemController {

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("item",null);
        return "item/addForm";
    }


}
