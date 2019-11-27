package com.project.calendar.service.util;

import com.project.calendar.exception.IllegalPaginationValuesException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ValidatePaginationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final ValidatePagination validatePagination = new ValidatePagination();

    @Test
    public void validateShouldThrowIllegalPaginationValuesException() {
        expectedException.expect(IllegalPaginationValuesException.class);
        expectedException.expectMessage("Illegal pagination values");

        validatePagination.validate(null, null, 12L, ValidatePagination.DEFAULT_USER_ROW_COUNT);
    }

    @Test
    public void validateShouldChangeRowCountAndPage() {
        final Integer[] validValues = validatePagination.validate("-12", "28", 48L, ValidatePagination.DEFAULT_EXPOSITION_ROW_COUNT);

        assertThat(validValues[0], is(ValidatePagination.DEFAULT_PAGE));
        assertThat(validValues[1], is(ValidatePagination.DEFAULT_EXPOSITION_ROW_COUNT));
    }
}