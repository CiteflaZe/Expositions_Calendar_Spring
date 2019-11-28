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
        final ModelAndView modelAndView = new ModelAndView("admin-add-exposition");

        modelAndView.addObject("halls", hallService.showAll());
        modelAndView.addObject("exposition", new Exposition());

        return modelAndView;
    }

    @PostMapping("admin/add-exposition")
    public ModelAndView addExposition(@Valid Exposition exposition, BindingResult bindingResult) {
        final ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin-add-exposition");
        } else if (exposition.getStartDate().compareTo(exposition.getEndDate()) >= 0) {
            modelAndView.addObject("dateError", true);
            modelAndView.setViewName("admin-add-exposition");
        } else {
            expositionService.add(exposition);
            modelAndView.setViewName("redirect:/admin");
        }

        return modelAndView;
    }

    @GetMapping("admin/add-hall")
    public ModelAndView addHallPage() {
        final ModelAndView modelAndView = new ModelAndView("admin-add-hall");

        modelAndView.addObject("hall", new Hall());

        return modelAndView;
    }


    @PostMapping("admin/add-hall")
    public ModelAndView addHall(@Valid Hall hall, BindingResult bindingResult) {
        final ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin-add-hall");
        } else {
            hallService.add(hall);
            modelAndView.setViewName("redirect:/admin");
        }

        return modelAndView;
    }

    @GetMapping("admin/users")
    public ModelAndView showUsers(@RequestParam("page") String stringPage,
                                  @RequestParam("rowCount") String stringRowCount) {
        final ModelAndView modelAndView = new ModelAndView("admin-users");

        final Integer[] paginationParameters = validatePagination.validate(stringPage, stringRowCount, userService.showEntriesAmount(), ValidatePagination.DEFAULT_USER_ROW_COUNT);
        final int page = paginationParameters[0];
        final int rowCount = paginationParameters[1];
        final int numberOfPages = paginationParameters[2];

        final List<User> users = userService.showAll(page - 1, rowCount);

        modelAndView.addObject("users", users);
        modelAndView.addObject("command", "/admin/users");
        modelAndView.addObject("numberOfPages", numberOfPages);
        modelAndView.addObject("page", page);
        modelAndView.addObject("rowCount", rowCount);

        return modelAndView;
    }

    @GetMapping("admin/halls")
    public ModelAndView showHalls() {
        final ModelAndView modelAndView = new ModelAndView("admin-halls");

        final List<Hall> halls = hallService.showAll();
        modelAndView.addObject("halls", halls);

        return modelAndView;
    }

    @GetMapping("admin/expositions")
    public ModelAndView showExpositions(@RequestParam("page") String stringPage,
                                        @RequestParam("rowCount") String stringRowCount) {
        final ModelAndView modelAndView = new ModelAndView("admin-expositions");

        final Integer[] paginationParameters = validatePagination.validate(stringPage, stringRowCount, expositionService.showEntriesAmount(), ValidatePagination.DEFAULT_EXPOSITION_ROW_COUNT);
        final int page = paginationParameters[0];
        final int rowCount = paginationParameters[1];
        final int numberOfPages = paginationParameters[2];

        final List<Exposition> expositions = expositionService.showAll(page - 1, rowCount);
        modelAndView.addObject("expositions", expositions);
        modelAndView.addObject("command", "/admin/expositions");
        modelAndView.addObject("numberOfPages", numberOfPages);
        modelAndView.addObject("page", page);
        modelAndView.addObject("rowCount", rowCount);

        return modelAndView;
    }
}
