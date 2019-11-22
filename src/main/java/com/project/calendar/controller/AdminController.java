package com.project.calendar.controller;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Hall;
import com.project.calendar.service.ExpositionService;
import com.project.calendar.service.HallService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller("/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final HallService hallService;
    private final ExpositionService expositionService;

    @GetMapping("/admin")
    public String main() {
        return "admin-page";
    }

    @GetMapping("add-exposition")
    public ModelAndView addExpositionPage() {
        final ModelAndView mav = new ModelAndView();

        mav.addObject("halls", hallService.showAll());
        mav.addObject("exposition", new Exposition());
        mav.setViewName("admin-add-exposition");

        return mav;
    }

    @PostMapping("add-exposition")
    public ModelAndView addExposition(@Valid Exposition exposition, BindingResult bindingResult) {
        final ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {
            System.out.println("General Error");
            mav.setViewName("admin-add-exposition");
        } else if (exposition.getStartDate().compareTo(exposition.getEndDate()) >= 0) {
            System.out.println("Date error");
            mav.addObject("dateError", true);
            mav.setViewName("admin-add-exposition");
        } else {
            expositionService.add(exposition);
            mav.setViewName("redirect:/admin");
        }

        return mav;
    }

    @GetMapping("add-hall")
    public ModelAndView addHallPage() {
        final ModelAndView mav = new ModelAndView();

        mav.addObject("hall", new Hall());
        mav.setViewName("admin-add-hall");

        return mav;
    }


    @PostMapping("add-hall")
    public ModelAndView addHall(@Valid Hall hall, BindingResult bindingResult) {
        final ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {
            mav.setViewName("admin-add-hall");
        } else {
            System.out.println("Added");
            hallService.add(hall);
            mav.setViewName("redirect:/admin");
        }

        return mav;
    }

}
