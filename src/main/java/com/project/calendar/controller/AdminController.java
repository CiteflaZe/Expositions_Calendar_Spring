package com.project.calendar.controller;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Hall;
import com.project.calendar.domain.User;
import com.project.calendar.service.ExpositionService;
import com.project.calendar.service.HallService;
import com.project.calendar.service.UserService;
import com.project.calendar.service.util.ValidatePagination;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller("/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final HallService hallService;
    private final ExpositionService expositionService;
    private final UserService userService;
    private final ValidatePagination validatePagination;

    @GetMapping("/admin")
    public String main() {
        return "admin-page";
    }

    @GetMapping("admin/add-exposition")
    public ModelAndView addExpositionPage() {
        final ModelAndView mav = new ModelAndView("admin-add-exposition");

        mav.addObject("halls", hallService.showAll());
        mav.addObject("exposition", new Exposition());

        return mav;
    }

    @PostMapping("admin/add-exposition")
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

    @GetMapping("admin/add-hall")
    public ModelAndView addHallPage() {
        final ModelAndView mav = new ModelAndView("admin-add-hall");

        mav.addObject("hall", new Hall());

        return mav;
    }


    @PostMapping("admin/add-hall")
    public ModelAndView addHall(@Valid Hall hall, BindingResult bindingResult) {
        final ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {
            mav.setViewName("admin-add-hall");
        } else {
            hallService.add(hall);
            mav.setViewName("redirect:/admin");
        }

        return mav;
    }

    @GetMapping("admin/users")
    public ModelAndView showUsers(@RequestParam("page") String stringPage,
                                  @RequestParam("rowCount") String stringRowCount) {
        final ModelAndView mav = new ModelAndView("admin-users");

        final Integer[] paginationParameters = validatePagination.validate(stringPage, stringRowCount, userService.showEntriesAmount(), ValidatePagination.DEFAULT_USER_ROW_COUNT);
        final int page = paginationParameters[0];
        final int rowCount = paginationParameters[1];
        final int numberOfPages = paginationParameters[2];

        final List<User> users = userService.showAll(page - 1, rowCount);

        mav.addObject("users", users);
        mav.addObject("command", "/admin/users");
        mav.addObject("numberOfPages", numberOfPages);
        mav.addObject("page", page);
        mav.addObject("rowCount", rowCount);

        return mav;
    }

    @GetMapping("admin/halls")
    public ModelAndView showHalls() {
        final ModelAndView mav = new ModelAndView("admin-halls");

        final List<Hall> halls = hallService.showAll();
        mav.addObject("halls", halls);

        return mav;
    }

    @GetMapping("admin/expositions")
    public ModelAndView showExpositions(@RequestParam("page") String stringPage,
                                        @RequestParam("rowCount") String stringRowCount) {
        final ModelAndView mav = new ModelAndView("admin-expositions");

        final Integer[] paginationParameters = validatePagination.validate(stringPage, stringRowCount, expositionService.showEntriesAmount(), ValidatePagination.DEFAULT_EXPOSITION_ROW_COUNT);
        final int page = paginationParameters[0];
        final int rowCount = paginationParameters[1];
        final int numberOfPages = paginationParameters[2];

        final List<Exposition> expositions = expositionService.showAll(page - 1, rowCount);
        mav.addObject("expositions", expositions);
        mav.addObject("command", "/admin/expositions");
        mav.addObject("numberOfPages", numberOfPages);
        mav.addObject("page", page);
        mav.addObject("rowCount", rowCount);

        return mav;
    }
}
