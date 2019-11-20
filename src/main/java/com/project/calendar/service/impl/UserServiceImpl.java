package com.project.calendar.service.impl;

import com.project.calendar.domain.User;
import com.project.calendar.entity.UserEntity;
import com.project.calendar.exception.InvalidLoginException;
import com.project.calendar.repository.UserRepository;
import com.project.calendar.service.UserService;
import com.project.calendar.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private UserMapper mapper;

    @Override
    public User login(String email, String password) {
        final Optional<UserEntity> entity = userRepository.findByEmail(email);

        if (!entity.isPresent()) {
            LOGGER.warn("No user found with such email");
            throw new InvalidLoginException("No user found with such email");
        }

        if (encoder.matches(password, entity.get().getPassword())) {
            return mapper.mapUserEntityToUser(entity.get());
        } else {
            LOGGER.warn("Incorrect password");
            throw new InvalidLoginException("Incorrect password");
        }
    }

    @Override
    public User register(User user) {
        return null;
    }

    @Override
    public List<User> showAll(Integer currentPage, Integer rowCount) {
        return null;
    }
}
