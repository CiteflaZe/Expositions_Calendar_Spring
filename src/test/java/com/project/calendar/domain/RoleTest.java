package com.project.calendar.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RoleTest {

    @Test
    public void valueOfByNameShouldReturnDefaultRoleWithNullInput() {
        final Role actual = Role.valueOfByName(null);

        assertThat(actual, is(Role.USER));
    }

    @Test
    public void valueOfByNameShouldReturnDefaultRoleWithIllegalValueInput() {
        final Role actual = Role.valueOfByName("something");

        assertThat(actual, is(Role.USER));
    }

    @Test
    public void valueOfByNameShouldReturnRequestedRole() {
        final Role actual = Role.valueOfByName("admin");

        assertThat(actual, is(Role.ADMIN));
    }

}