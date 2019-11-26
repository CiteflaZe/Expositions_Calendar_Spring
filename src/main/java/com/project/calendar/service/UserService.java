package com.project.calendar.service;

import com.project.calendar.domain.User;

import java.util.List;

public interface UserService {

    User login(String email, String password);

    void register(User user);

    List<User> showAll(Integer page, Integer rowCount);

    Long showEntriesAmount();
}
