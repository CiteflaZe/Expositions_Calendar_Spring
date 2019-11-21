package com.project.calendar.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    @GetMapping("/user")
    public String main(){
        return "user-page";
    }

}
