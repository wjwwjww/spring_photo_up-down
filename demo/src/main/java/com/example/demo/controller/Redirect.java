package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Redirect {
    @RequestMapping("/a")
    public String redirect() {
        return "a";
    }


    @RequestMapping("/b")
    public String redirect1() {
        return "b";
    }
}
