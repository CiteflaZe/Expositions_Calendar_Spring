package com.project.calendar.service.impl;

import com.project.calendar.domain.User;
import com.project.calendar.entity.UserEntity;
import com.project.calendar.exception.EmailAlreadyExistException;
import com.project.calendar.exception.InvalidLoginException;
import com.project.calendar.repository.UserRepository;
import com.project.calendar.service.mapper.UserMapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.project.calendar.MockData.MOCK_USER;
import static com.project.calendar.MockData.MOCK_USER_ENTITY;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UserServiceImpl.class)
public class UserServiceImplTest {

    private static final User USER = MOCK_USER;
    private static final UserEntity USER_ENTITY = MOCK_USER_ENTITY;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl userService;

    @After
    public void resetMocks() {
        reset(userRepository, encoder, userMapper);
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
        expectedException.expectMessage("Incorrect email or password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER_ENTITY));
        when(encoder.matches(anyString(), anyString())).thenReturn(false);

        userService.login(USER.getEmail(), USER.getPassword());
    }

    @Test
    public void loginShouldReturnUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER_ENTITY));
        when(encoder.matches(anyString(), anyString())).thenReturn(true);
        when(userMapper.mapUserEntityToUser(any(UserEntity.class))).thenReturn(USER);

        final User actual = userService.login(USER.getEmail(), USER.getPassword());

        assertThat(actual.getName(), is(MOCK_USER_ENTITY.getName()));
        assertThat(actual.getSurname(), is(MOCK_USER_ENTITY.getSurname()));
        assertThat(actual.getEmail(), is(MOCK_USER_ENTITY.getEmail()));
        assertThat(actual.getPassword(), is(MOCK_USER_ENTITY.getPassword()));
    }

    @Test
    public void registerShouldThrowEmailAlreadyExistException() {
        expectedException.expect(EmailAlreadyExistException.class);
        expectedException.expectMessage("User with such email already exist");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER_ENTITY));

        userService.register(USER);
    }

    @Test
    public void registerShouldRegisterUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(encoder.encode(anyString())).thenReturn("user19");
        when(userMapper.mapUserToUserEntity(any(User.class))).thenReturn(USER_ENTITY);

        userService.register(USER);

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    public void showAllShouldReturnEmptyList() {
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());

        final List<User> users = userService.showAll(1, 3);

        verify(userRepository).findAll(any(PageRequest.class));
        assertThat(users, is(emptyList()));
    }

    @Test
    public void showAllShouldReturnListOfUsers() {
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(singletonList(USER_ENTITY)));
        when(userMapper.mapUserEntityToUser(USER_ENTITY)).thenReturn(USER);

        final List<User> actual = userService.showAll(1, 3);

        verify(userRepository).findAll(any(PageRequest.class));
        assertThat(actual, hasItem(USER));
    }

    @Test
    public void showEntriesAmountShouldReturnAmountOfRowsInDatabase() {
        when(userRepository.count()).thenReturn(5L);

        final Long actual = userService.showEntriesAmount();

        verify(userRepository).count();
        assertThat(actual, is(5L));
    }

}