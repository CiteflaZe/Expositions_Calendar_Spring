package com.project.calendar.service.util;

import com.project.calendar.exception.IllegalPaginationValuesException;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;

@Component
@Log4j
public class ValidatePagination {

    public void validate(String page, String rowCount) {
        if (isParsable(page) || isParsable(rowCount)) {
            log.warn("Illegal pagination values");
            throw new IllegalPaginationValuesException("Illegal pagination values");
        }


    }


}
