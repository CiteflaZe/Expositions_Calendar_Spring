package com.project.calendar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class User {

    private final Long id;

    @NotEmpty(message = "Please provide email")
    @Pattern(regexp = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,4}$")
    private final String email;

    @NotEmpty(message = "Please provide password")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,15}$")
    @Setter
    private String password;

    @NotEmpty(message = "Please provide name")
    @Pattern(regexp = "^[a-zA-Zа-яА-Яієї']{2,25}$")
    private final String name;

    @NotEmpty(message = "Please provide surname")
    @Pattern(regexp = "^[a-zA-Zа-яА-Яієї']{2,25}$")
    private final String surname;

    private final Role role;
}
