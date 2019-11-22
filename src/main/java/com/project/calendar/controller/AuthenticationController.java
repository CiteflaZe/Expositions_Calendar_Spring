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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final UserService userService;

    @RequestMapping("/")
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
        final ModelAndView mav = new ModelAndView();

        final User user = userService.login(email, password);

        session.setAttribute("user", user);

        if (user.getRole() == Role.ADMIN) {
            mav.setViewName("redirect:/admin");
        } else if (user.getRole() == Role.USER) {
            mav.setViewName("redirect:/user");
        } else {
            mav.setViewName("redirect:/");
        }

        return mav;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", new User());
        mav.setViewName("register");
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult,
                                     @RequestParam(name = "passwordConfirmation") String confirmPass) {
        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {
            mav.setViewName("register");
        } else if (!Objects.equals(user.getPassword(), confirmPass)) {
            mav.addObject("confirmationError", true);
            mav.setViewName("register");
        } else {
            userService.register(user);
            mav.setViewName("redirect:/");
        }
        return mav;
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
