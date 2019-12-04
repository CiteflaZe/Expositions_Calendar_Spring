package com.project.calendar.controller;

import com.project.calendar.domain.Hall;
import com.project.calendar.domain.User;
import com.project.calendar.exception.DownloadTicketsException;
import com.project.calendar.exception.EmailAlreadyExistException;
import com.project.calendar.exception.EntityNotFoundException;
import com.project.calendar.exception.HallAlreadyExistException;
import com.project.calendar.exception.IllegalPaginationValuesException;
import com.project.calendar.exception.PDFCreationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ModelAndView handleEmailAlreadyExistException() {
        final ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("registerError", true);
        modelAndView.addObject("user", new User());

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
            EntityNotFoundException.class,
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
