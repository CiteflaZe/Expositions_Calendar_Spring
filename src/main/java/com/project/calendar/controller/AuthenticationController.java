package com.project.calendar.controller;

import com.project.calendar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class AuthenticationController {

    private final UserService userService;

    @RequestMapping("/")
    public String main(Model model){
        model.addAttribute("message", "Just slash");
        model.addAttribute("lang", "en");
        return "index";
    }

    @RequestMapping("/index")
    public String index(
            @RequestParam(name = "name", required = false, defaultValue = "name") String name, Model model){
        model.addAttribute("message", name);
        return "index";
    }
}
