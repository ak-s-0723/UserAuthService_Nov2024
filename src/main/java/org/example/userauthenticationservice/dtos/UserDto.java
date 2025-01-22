package org.example.userauthenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.userauthenticationservice.models.Role;

import java.util.List;

@Setter
@Getter
public class UserDto {
    private Long id;
    private String email;
    //private List<Role> roles;
}
