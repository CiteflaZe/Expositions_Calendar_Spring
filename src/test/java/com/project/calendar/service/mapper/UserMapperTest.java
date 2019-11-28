package com.project.calendar.service.mapper;

import com.project.calendar.domain.User;
import com.project.calendar.entity.UserEntity;
import org.junit.Test;

import static com.project.calendar.MockData.MOCK_USER;
import static com.project.calendar.MockData.MOCK_USER_ENTITY;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class UserMapperTest {

    private static final User USER = MOCK_USER;
    private static final UserEntity USER_ENTITY = MOCK_USER_ENTITY;

    private UserMapper userMapper = new UserMapper();

    @Test
    public void mapUserToUserEntityShouldReturnUserEntity() {
        final UserEntity actual = userMapper.mapUserToUserEntity(USER);

        assertThat(actual.getEmail(), is(USER.getEmail()));
        assertThat(actual.getPassword(), is(USER.getPassword()));
        assertThat(actual.getName(), is(USER.getName()));
        assertThat(actual.getSurname(), is(USER.getSurname()));
        assertThat(actual.getRole(), is(USER.getRole()));
    }

    @Test
    public void mapUserEntityToUserShouldReturnUser() {
        final User actual = userMapper.mapUserEntityToUser(USER_ENTITY);

        assertThat(actual.getId(), is(USER_ENTITY.getId()));
        assertThat(actual.getEmail(), is(USER_ENTITY.getEmail()));
        assertThat(actual.getPassword(), is(nullValue()));
        assertThat(actual.getName(), is(USER_ENTITY.getName()));
        assertThat(actual.getSurname(), is(USER_ENTITY.getSurname()));
        assertThat(actual.getRole(), is(USER_ENTITY.getRole()));
    }

}