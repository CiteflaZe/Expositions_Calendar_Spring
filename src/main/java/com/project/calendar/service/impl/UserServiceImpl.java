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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service("userService")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Log4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserMapper userMapper;

    @Override
    public void register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.warn("Email already exists");
            throw new EmailAlreadyExistException("User with such email already exist");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        final UserEntity entity = userMapper.mapUserToUserEntity(user);

        userRepository.save(entity);
    }

    @Override
    public List<User> showAll(Integer page, Integer rowCount) {
        final PageRequest pageRequest = PageRequest.of(page, rowCount);

        Page<UserEntity> entities = userRepository.findAll(pageRequest);

        return entities.isEmpty() ? emptyList() :
                entities.stream()
                        .map(userMapper::mapUserEntityToUser)
                        .collect(toList());
    }

    @Override
    public Long showEntriesAmount() {
        return userRepository.count();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        if (email == null) {
            log.warn("Email is null");
            throw new IllegalArgumentException("Email is null");
        }
        final Optional<UserEntity> entity = userRepository.findByEmail(email);

        if (!entity.isPresent()) {
            log.warn("No user found with such email");
            throw new InvalidLoginException("No user found with such email");
        }

        return userMapper.mapUserEntityToUser(entity.get());

    }
}
