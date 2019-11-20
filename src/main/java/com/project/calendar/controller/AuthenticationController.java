package com.project.calendar.controller;

import com.project.calendar.domain.Role;
import com.project.calendar.domain.User;
import com.project.calendar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final UserService userService;

    @RequestMapping("/")
    public String main(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView signIn() {
        final ModelAndView mav = new ModelAndView();

        final User admin = userService.login("admin@gmail.com", "admin");
        System.out.println(admin);

        if (admin.getRole() == Role.ADMIN) {
            mav.setViewName("admin-page");
        }else{
            mav.setViewName("index");
        }

        return mav;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView();
        User user = User.builder().build();
        mav.addObject("user", user);
        mav.setViewName("/register");
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.setViewName("register");
        } else {
            mav.addObject("succMessage", "User was registered");
            mav.setViewName("register");
        }
        return mav;
    }
}
