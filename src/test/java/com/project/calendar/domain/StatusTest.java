package com.project.calendar.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StatusTest {

    @Test
    public void valueOfByNameShouldReturnDefaultStatusWithNullInput() {
        final Status actual = Status.valueOfByName(null);

        assertThat(actual, is(Status.FAILED));
    }

    @Test
    public void valueOfByNameShouldReturnDefaultStatusWithIllegalValueInput() {
        final Status actual = Status.valueOfByName("done");

        assertThat(actual, is(Status.FAILED));
    }

    @Test
    public void valueOfByNameShouldReturnRequestedStatus() {
        final Status actual = Status.valueOfByName("passed");

        assertThat(actual, is(Status.PASSED));
    }

}