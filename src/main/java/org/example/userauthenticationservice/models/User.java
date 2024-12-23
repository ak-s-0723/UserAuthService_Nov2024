package org.example.userauthenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class User extends BaseModel {
    private String email;

    private String password;

    @ManyToMany
    private List<Role> roles=new ArrayList<>();
}


//1       m
//user  role
//m        1
//
//m : m