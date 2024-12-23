package org.example.userauthenticationservice.services;

import org.example.userauthenticationservice.exceptions.IncorrectPasswordException;
import org.example.userauthenticationservice.exceptions.UserAlreadyExistException;
import org.example.userauthenticationservice.exceptions.UserDoesnotExistException;
import org.example.userauthenticationservice.models.User;
import org.example.userauthenticationservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User signup(String email, String password) {
        Optional<User>  optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()) {
            throw new UserAlreadyExistException("Please try with different email !!");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepo.save(user);
        return user;
    }

    @Override
    public User login(String email, String password) {
        Optional<User>  optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UserDoesnotExistException("Please signup first");
        }

        if(!bCryptPasswordEncoder.matches(password,optionalUser.get().getPassword())) {
       // if(!password.equals(optionalUser.get().getPassword())) {
           throw new IncorrectPasswordException("Password didn't match");
        }

        return optionalUser.get();

    }
}



//postman -> "anurag"
//signup -> bpe.encode("anurag")-> "dhidhihd"
//
//login -> "anurag"
//        bpe.encode("anurag")-> "dhidhihd" == "dhidhihd"
