package com.project.calendar.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    private Long id;

    @NotEmpty(message = "Please provide email")
    @Pattern(regexp = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,4}$", message = "Email does not match pattern: example@gmail.com")
    private String email;

    @NotEmpty(message = "Please provide password")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,15}$", message = "Password should be 5 to 15 characters long")
    @Setter(AccessLevel.PUBLIC)
    private String password;

    @NotEmpty(message = "Please provide name")
    @Pattern(regexp = "^[a-zA-Zа-яА-Яієї']{2,25}$", message = "Name should be at least 2 characters long and should not contain numbers")
    private String name;

    @NotEmpty(message = "Please provide surname")
    @Pattern(regexp = "^[a-zA-Zа-яА-Яієї']{2,25}$", message = "Surname should be at least 2 characters long and should not contain numbers")
    private String surname;

    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole());
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
