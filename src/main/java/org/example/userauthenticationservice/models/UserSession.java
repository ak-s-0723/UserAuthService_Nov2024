package org.example.userauthenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserSession extends BaseModel {
    private String token;

    @ManyToOne
    private User user;
}


//1            m
//user         session
//1             1
//
//1        :     m