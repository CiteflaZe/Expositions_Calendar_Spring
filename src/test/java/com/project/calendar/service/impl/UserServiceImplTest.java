package com.project.calendar.service.impl;

import com.project.calendar.domain.User;
import com.project.calendar.entity.UserEntity;
import com.project.calendar.exception.EmailAlreadyExistException;
import com.project.calendar.exception.InvalidLoginException;
import com.project.calendar.repository.UserRepository;
import com.project.calendar.service.mapper.UserMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final User USER = initUser();
    private static final UserEntity ENTITY = initUserEntity();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder encoder;
    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    @After
    public void resetMocks() {
        reset(userRepository, encoder, mapper);
    }

    @Test
    public void loginShouldThrowInvalidLoginException() {
        expectedException.expect(InvalidLoginException.class);
        expectedException.expectMessage("No user found with such email");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        userService.login(USER.getEmail(), USER.getPassword());
    }

    @Test
    public void loginShouldThrowInvalidLoginExceptionWithIncorrectPassword() {
        expectedException.expect(InvalidLoginException.class);
        expectedException.expectMessage("Incorrect password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(ENTITY));
        when(encoder.matches(anyString(), anyString())).thenReturn(false);

        userService.login(USER.getEmail(), USER.getPassword());
    }

    @Test
    public void loginShouldReturnUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(ENTITY));
        when(encoder.matches(anyString(), anyString())).thenReturn(true);
        when(mapper.mapUserEntityToUser(any(UserEntity.class))).thenReturn(USER);

        final User actual = userService.login(USER.getEmail(), USER.getPassword());

        assertThat(actual.getName(), is("Name"));
        assertThat(actual.getSurname(), is("Surname"));
        assertThat(actual.getEmail(), is("somemail@gmail.com"));
        assertThat(actual.getPassword(), is("user19"));
    }

    @Test
    public void registerShouldThrowEmailAlreadyExistException(){
        expectedException.expect(EmailAlreadyExistException.class);
        expectedException.expectMessage("User with such email already exist");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(ENTITY));

        userService.register(USER);
    }

    @Test
    public void registerShouldRegisterUser(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(encoder.encode(anyString())).thenReturn("user19");
        when(mapper.mapUserToUserEntity(any(User.class))).thenReturn(ENTITY);

        userService.register(USER);

        verify(userRepository).save(any(UserEntity.class));
    }

    private static User initUser() {
        return User.builder()
                .name("Name")
                .surname("Surname")
                .email("somemail@gmail.com")
                .password("user19")
                .build();
    }

    private static UserEntity initUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setName("Jack");
        entity.setSurname("Jacky");
        entity.setEmail("anothermail@gmail.com");
        entity.setPassword("entity13");

        return entity;
    }

}