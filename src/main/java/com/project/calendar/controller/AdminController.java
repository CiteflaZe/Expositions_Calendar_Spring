package com.project.calendar.controller;

import com.project.calendar.service.HallService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final HallService hallService;

    @GetMapping("/admin")
    public String main(){
        return "admin-page";
    }

    @GetMapping("add-exposition")
    public ModelAndView addExpositionPage(){
        final ModelAndView mav = new ModelAndView();

        mav.addObject("halls", hallService.showAll());
        mav.setViewName("admin-add-exposition");

        return mav;
    }

}
