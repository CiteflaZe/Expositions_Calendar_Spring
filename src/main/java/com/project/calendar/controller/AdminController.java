package com.project.calendar.controller;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Hall;
import com.project.calendar.domain.User;
import com.project.calendar.exception.EntityNotFoundException;
import com.project.calendar.exception.ExpositionAlreadyExistException;
import com.project.calendar.service.ExpositionService;
import com.project.calendar.service.HallService;
import com.project.calendar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController implements PaginationUtility {

    private final HallService hallService;
    private final ExpositionService expositionService;
    private final UserService userService;

    @GetMapping("")
    public String main(HttpSession session) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        session.setAttribute("user", user);

        return "admin-page";
    }

    @GetMapping("/add-exposition")
    public ModelAndView addExpositionPage() {
        final ModelAndView modelAndView = new ModelAndView("admin-add-exposition");

        modelAndView.addObject("halls", hallService.showAll());
        modelAndView.addObject("exposition", new Exposition());

        return modelAndView;
    }

    @PostMapping("/add-exposition")
    public ModelAndView addExposition(@Valid Exposition exposition, BindingResult bindingResult,
                                      @RequestParam(value = "hallId", required = false) Long hallId) {
        final ModelAndView modelAndView = new ModelAndView();

        Hall hall;
        try {
            hall = hallService.showById(hallId);
        } catch (EntityNotFoundException e) {
            hall = null;
        }
        exposition.setHall(hall);

        modelAndView.addObject("halls", hallService.showAll());
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin-add-exposition");
        } else if (exposition.getStartDate().compareTo(exposition.getEndDate()) >= 0) {
            modelAndView.addObject("dateError", true);
            modelAndView.setViewName("admin-add-exposition");
        } else if (exposition.getHall() == null) {
            modelAndView.addObject("hallError", true);
            modelAndView.setViewName("admin-add-exposition");
        } else {
            expositionService.add(exposition);
            modelAndView.setViewName("redirect:/admin");
        }

        return modelAndView;
    }

    @GetMapping("/add-hall")
    public ModelAndView addHallPage() {
        final ModelAndView modelAndView = new ModelAndView("admin-add-hall");

        modelAndView.addObject("hall", new Hall());

        return modelAndView;
    }


    @PostMapping("/add-hall")
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

    @GetMapping("/users")
    public ModelAndView showUsers(@RequestParam("page") String stringPage,
                                  @RequestParam("rowCount") String stringRowCount) {
        final ModelAndView modelAndView = new ModelAndView("admin-users");

        validatePagination(stringPage, stringRowCount);

        final int page = Integer.parseInt(stringPage);
        final int rowCount = Integer.parseInt(stringRowCount);
        final List<User> users = userService.showAll(page - 1, rowCount);

        paginate(page, rowCount, userService.showEntriesAmount(), modelAndView, "/admin/users");

        modelAndView.addObject("users", users);

        return modelAndView;
    }

    @GetMapping("/halls")
    public ModelAndView showHalls() {
        final ModelAndView modelAndView = new ModelAndView("admin-halls");

        final List<Hall> halls = hallService.showAll();
        modelAndView.addObject("halls", halls);

        return modelAndView;
    }

    @GetMapping("/expositions")
    public ModelAndView showExpositions(@RequestParam("page") String stringPage,
                                        @RequestParam("rowCount") String stringRowCount) {
        final ModelAndView modelAndView = new ModelAndView("admin-expositions");

        validatePagination(stringPage, stringRowCount);

        final int page = Integer.parseInt(stringPage);
        final int rowCount = Integer.parseInt(stringRowCount);
        final List<Exposition> expositions = expositionService.showAll(page - 1, rowCount);

        paginate(page, rowCount, expositionService.showEntriesAmount(), modelAndView, "/admin/expositions");

        modelAndView.addObject("expositions", expositions);

        return modelAndView;
    }

    @ExceptionHandler(ExpositionAlreadyExistException.class)
    public ModelAndView handleExpositionAlreadyExistException() {
        final ModelAndView modelAndView = new ModelAndView("admin-add-exposition");
        modelAndView.addObject("expositionError", true);
        modelAndView.addObject("halls", hallService.showAll());
        modelAndView.addObject("exposition", new Exposition());

        return modelAndView;
    }
}
