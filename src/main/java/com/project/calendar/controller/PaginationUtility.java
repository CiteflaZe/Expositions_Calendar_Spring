package com.project.calendar.controller;

import com.project.calendar.exception.IllegalPaginationValuesException;
import org.springframework.web.servlet.ModelAndView;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public interface PaginationUtility {

    default void paginate(int page, int rowCount, long entries, ModelAndView modelAndView, String url) {
        int numberOfPages = (int) Math.ceil(entries * 1.0 / rowCount);

        modelAndView.addObject("page", page);
        modelAndView.addObject("rowCount", rowCount);
        modelAndView.addObject("numberOfPages", numberOfPages);
        modelAndView.addObject("command", url);
    }

    default void validatePagination(String page, String rowCount) {
        if (!isParsable(page) || !isParsable(rowCount)) {
            throw new IllegalPaginationValuesException("Illegal pagination values");
        }
    }

}
