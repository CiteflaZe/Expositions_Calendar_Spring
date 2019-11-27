package com.project.calendar.service.mapper;

import com.project.calendar.domain.Role;
import com.project.calendar.domain.User;
import com.project.calendar.entity.UserEntity;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class UserMapperTest {
    private static final Long ID = 4L;
    private static final String EMAIL = "email@gmail.com";
    private static final String PASSWORD = "userpass1";
    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";

    private static final User USER = initUser();
    private static final UserEntity ENTITY = initEntity();

    private UserMapper mapper = new UserMapper();

    @Test
    public void mapUserToUserEntityShouldReturnEntity() {
        final UserEntity actual = mapper.mapUserToUserEntity(USER);

        assertThat(actual.getEmail(), is(EMAIL));
        assertThat(actual.getPassword(), is(PASSWORD));
        assertThat(actual.getName(), is(NAME));
        assertThat(actual.getSurname(), is(SURNAME));
        assertThat(actual.getRole(), is(Role.USER));
    }

    @Test
    public void mapUserEntityToUserShouldReturnUser(){
        final User actual = mapper.mapUserEntityToUser(ENTITY);

        assertThat(actual.getId(), is(ID));
        assertThat(actual.getEmail(), is(EMAIL));
        assertThat(actual.getPassword(), is(nullValue()));
        assertThat(actual.getName(), is(NAME));
        assertThat(actual.getSurname(), is(SURNAME));
        assertThat(actual.getRole(), is(Role.USER));
    }

    private static User initUser() {
        return User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .surname(SURNAME)
                .role(Role.USER)
                .build();
    }

    private static UserEntity initEntity() {
        final UserEntity entity = new UserEntity();
        entity.setId(ID);
        entity.setEmail(EMAIL);
        entity.setPassword(PASSWORD);
        entity.setName(NAME);
        entity.setSurname(SURNAME);
        entity.setRole(Role.USER);

        return entity;
    }

}