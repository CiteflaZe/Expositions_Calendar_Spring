package com.project.calendar.service.mapper;

import com.project.calendar.domain.Role;
import com.project.calendar.domain.User;
import com.project.calendar.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity mapUserToUserEntity(User user) {
        //todo do something about path
        final com.project.calendar.entity.Role role = new com.project.calendar.entity.Role();
        role.setId(2);

        final UserEntity entity = new UserEntity();
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setName(user.getName());
        entity.setSurname(user.getSurname());
        entity.setRole(role);
        return entity;
    }

    //todo null check
    public User mapUserEntityToUser(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .surname(entity.getSurname())
//                .password(entity.getPassword())
                .role(Role.valueOfByName(entity.getRole().getRoleName().toUpperCase()))
                .build();
    }

}
