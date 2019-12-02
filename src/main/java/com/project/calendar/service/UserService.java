package com.project.calendar.service;

import com.project.calendar.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void register(User user);

    List<User> showAll(Integer page, Integer rowCount);

    Long showEntriesAmount();
}
