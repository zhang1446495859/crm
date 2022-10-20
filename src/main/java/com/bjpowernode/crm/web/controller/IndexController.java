package com.bjpowernode.crm.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    //一个资源目录 一个Controller
    @RequestMapping("/")
    public String Index(){
        return "index";
    }

}
