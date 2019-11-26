package com.project.calendar.controller;

import com.project.calendar.domain.User;
import com.project.calendar.exception.DownloadTicketsException;
import com.project.calendar.exception.EmailAlreadyExistException;
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
        final ModelAndView mav = new ModelAndView("login");
        mav.addObject("loginError", true);

        return mav;
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ModelAndView handleEmailAlreadyExistException() {
        final ModelAndView mav = new ModelAndView("register");
        mav.addObject("registerError", true);
        mav.addObject("user", new User());

        return mav;
    }

    @ExceptionHandler(IllegalPaginationValuesException.class)
    public String handleIllegalPaginationValuesException() {
        return "error";
    }

    @ExceptionHandler(InvalidEntityException.class)
    public String handleInvalidEntityException() {
        return "error";
    }

    @ExceptionHandler(PDFCreationException.class)
    public String handlePDFCreationException() {
        return "error";
    }

    @ExceptionHandler(DownloadTicketsException.class)
    public String handleDownloadTicketsException() {
        return "error";
    }

    @ExceptionHandler(Throwable.class)
    public String handleThrowable() {
        return "error";
    }

}
