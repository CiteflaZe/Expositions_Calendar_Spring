package com.project.calendar.service.impl;

import com.project.calendar.domain.User;
import com.project.calendar.entity.UserEntity;
import com.project.calendar.exception.EmailAlreadyExistException;
import com.project.calendar.exception.InvalidLoginException;
import com.project.calendar.repository.UserRepository;
import com.project.calendar.service.UserService;
import com.project.calendar.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
@AllArgsConstructor(onConstructor = @_(@Autowired))
@Log4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserMapper mapper;

    @Override
    public User login(String email, String password) {
        final Optional<UserEntity> entity = userRepository.findByEmail(email);

        if (!entity.isPresent()) {
            log.warn("No user found with such email");
            throw new InvalidLoginException("No user found with such email");
        }

        if (encoder.matches(password, entity.get().getPassword())) {
            return mapper.mapUserEntityToUser(entity.get());
        } else {
            log.warn("Incorrect password");
            throw new InvalidLoginException("Incorrect password");
        }
    }

    @Override
    public void register(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
           log.warn("Email already exists");
           throw new EmailAlreadyExistException("User with such email already exist");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        final UserEntity entity = mapper.mapUserToUserEntity(user);

        userRepository.save(entity);
    }

    @Override
    public List<User> showAll(Integer currentPage, Integer rowCount) {
        return null;
    }
}