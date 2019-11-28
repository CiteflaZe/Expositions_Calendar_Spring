package com.project.calendar.controller;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Hall;
import com.project.calendar.domain.User;
import com.project.calendar.exception.DownloadTicketsException;
import com.project.calendar.exception.EmailAlreadyExistException;
import com.project.calendar.exception.ExpositionAlreadyExistException;
import com.project.calendar.exception.HallAlreadyExistException;
import com.project.calendar.exception.IllegalPaginationValuesException;
import com.project.calendar.exception.InvalidEntityException;
import com.project.calendar.exception.InvalidLoginException;
import com.project.calendar.exception.PDFCreationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidLoginException.class)
    public ModelAndView handleInvalidLoginException() {
        final ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("loginError", true);

        return modelAndView;
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ModelAndView handleEmailAlreadyExistException() {
        final ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("registerError", true);
        modelAndView.addObject("user", new User());

        return modelAndView;
    }

    @ExceptionHandler(ExpositionAlreadyExistException.class)
    public ModelAndView handleExpositionAlreadyExistException() {
        final ModelAndView modelAndView = new ModelAndView("admin-add-exposition");
        modelAndView.addObject("expositionError", true);
        modelAndView.addObject("exposition", new Exposition());

        return modelAndView;
    }

    @ExceptionHandler(HallAlreadyExistException.class)
    public ModelAndView handleHallAlreadyExistException() {
        final ModelAndView modelAndView = new ModelAndView("admin-add-hall");
        modelAndView.addObject("hallError", true);
        modelAndView.addObject("hall", new Hall());

        return modelAndView;
    }

    @ExceptionHandler(value = {IllegalPaginationValuesException.class,
            InvalidEntityException.class,
            PDFCreationException.class,
            DownloadTicketsException.class,
            IllegalArgumentException.class})
    public String handleIllegalPaginationValuesException() {
        return "error";
    }

    @ExceptionHandler(Throwable.class)
    public String handleThrowable() {
        return "error";
    }

}
