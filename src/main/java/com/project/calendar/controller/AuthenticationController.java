package com.project.calendar.controller;

import com.project.calendar.domain.Role;
import com.project.calendar.domain.User;
import com.project.calendar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView signIn(HttpSession session,
                               @RequestParam(name = "email") String email,
                               @RequestParam(name = "password") String password) {
        final ModelAndView modelAndView = new ModelAndView();

        final User user = userService.login(email, password);

        System.out.println(user);

        session.setAttribute("user", user);

        if (user.getRole() == Role.ADMIN) {
            modelAndView.setViewName("redirect:/admin");
        } else if (user.getRole() == Role.USER) {
            modelAndView.setViewName("redirect:/user");
        } else {
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult,
                                     @RequestParam(name = "passwordConfirmation") String confirmPass) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else if (!Objects.equals(user.getPassword(), confirmPass)) {
            modelAndView.addObject("confirmationError", true);
            modelAndView.setViewName("register");
        } else {
            userService.register(user);
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
