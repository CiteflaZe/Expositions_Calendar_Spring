package com.project.calendar.service.mapper;

import com.project.calendar.domain.Role;
import com.project.calendar.domain.User;
import com.project.calendar.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapper {

    public UserEntity mapUserToUserEntity(User user) {
        final UserEntity entity = new UserEntity();
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setName(user.getName());
        entity.setSurname(user.getSurname());
        entity.setRole(Role.USER);
        return entity;
    }

    public User mapUserEntityToUser(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .surname(entity.getSurname())
                .password(null)
                .role(getRole(entity))
                .build();
    }

    private Role getRole(UserEntity entity) {
        return Optional.ofNullable(entity)
                .map(UserEntity::getRole)
                .map(Role::name)
                .map(String::toUpperCase)
                .map(Role::valueOfByName)
                .orElse(null);
    }

}
