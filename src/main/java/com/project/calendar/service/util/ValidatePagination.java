package com.project.calendar.service.util;

import com.project.calendar.exception.IllegalPaginationValuesException;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;

@Component
@Log4j
public class ValidatePagination {

    public static final Integer DEFAULT_USER_ROW_COUNT = 15;
    public static final Integer DEFAULT_EXPOSITION_ROW_COUNT = 5;
    public static final Integer DEFAULT_PAGE = 1;

    public Integer[] validate(String stringPage, String stringRowCount, Long entriesAmount, Integer defaultRowCount) {
        if (!isParsable(stringPage) || !isParsable(stringRowCount)) {
            log.warn("Illegal pagination values");
            throw new IllegalPaginationValuesException("Illegal pagination values");
        }

        int page = Integer.parseInt(stringPage);
        int rowCount = Integer.parseInt(stringRowCount);

        if(rowCount != defaultRowCount){
            rowCount = defaultRowCount;
        }

        int numberOfPages = (int) Math.ceil(entriesAmount*1.0/rowCount);

        if(numberOfPages != 0){
            if(page > numberOfPages){
                page = numberOfPages;
            }else if(page <= 0){
                page = DEFAULT_PAGE;
            }
        }else {
            page = DEFAULT_PAGE;
        }

        return new Integer[]{page, rowCount, numberOfPages};

    }

}
