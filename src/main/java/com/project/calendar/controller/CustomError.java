package com.project.calendar.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomError implements ErrorController {

    @RequestMapping("/404-error")
    public String handleError(){
        return "404-error";
    }

    @Override
    public String getErrorPath() {
        return "/404-error";
    }
}
