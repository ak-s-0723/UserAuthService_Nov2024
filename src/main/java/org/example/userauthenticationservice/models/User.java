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

//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setRoles(List<Role> roles) {
//        this.roles = roles;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public List<Role> getRoles() {
//        return roles;
//    }
}


//1       m
//user  role
//m        1
//
//m : m