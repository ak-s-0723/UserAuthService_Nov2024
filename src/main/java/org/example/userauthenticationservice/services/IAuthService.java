package org.example.userauthenticationservice.services;

import org.example.userauthenticationservice.models.User;

public interface IAuthService {
   User signup(String email, String password);

   User login(String email,String password);
}
