package com.project.calendar.service.mapper;

import com.project.calendar.domain.Role;
import com.project.calendar.domain.User;
import com.project.calendar.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity mapUserToUserEntity(User user){
        return null;
    }

    public User mapUserEntityToUser(UserEntity entity) {
        return User.builder()
                .email(entity.getEmail())
                .name(entity.getName())
                .surname(entity.getSurname())
                .password(entity.getPassword())
                .role(Role.valueOf(entity.getRole().getRoleName().toUpperCase()))
                .build();
    }

}
