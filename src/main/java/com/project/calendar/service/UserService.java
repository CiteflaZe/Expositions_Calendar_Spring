package com.project.calendar.service;

import com.project.calendar.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User login(String email, String password);

    void register(User user);

    List<User> showAll(Integer page, Integer rowCount);

    Long showEntriesAmount();
}
