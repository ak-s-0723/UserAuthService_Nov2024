package org.example.userauthenticationservice.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthenticationservice.exceptions.IncorrectPasswordException;
import org.example.userauthenticationservice.exceptions.UserAlreadyExistException;
import org.example.userauthenticationservice.exceptions.UserDoesnotExistException;
import org.example.userauthenticationservice.models.Status;
import org.example.userauthenticationservice.models.User;
import org.example.userauthenticationservice.models.UserSession;
import org.example.userauthenticationservice.repos.SessionRepo;
import org.example.userauthenticationservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SessionRepo sessionRepo;


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
    public Pair<User,MultiValueMap<String,String>> login(String email, String password) {
        Optional<User>  optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UserDoesnotExistException("Please signup first");
        }

        if(!bCryptPasswordEncoder.matches(password,optionalUser.get().getPassword())) {
       // if(!password.equals(optionalUser.get().getPassword())) {
           throw new IncorrectPasswordException("Password didn't match");
        }

//                String message = "{\n" +
//                "   \"email\": \"anurag@gmail.com\",\n" +
//                "   \"roles\": [\n" +
//                "      \"instructor\",\n" +
//                "      \"ta\"\n" +
//                "   ],\n" +
//                "   \"expirationDate\": \"2ndApril2025\"\n" +
//                "}";
//
//                byte[] content = message.getBytes(StandardCharsets.UTF_8);


        Map<String,Object> userClaims = new HashMap<>();
        userClaims.put("userId",optionalUser.get().getId());
        userClaims.put("permissions",optionalUser.get().getRoles());
        Long currentTimeInMillis = System.currentTimeMillis();
        userClaims.put("iat",currentTimeInMillis);
        userClaims.put("exp",currentTimeInMillis+8640000);
        userClaims.put("issuer","scaler");



        MacAlgorithm algorithm = Jwts.SIG.HS256;
        SecretKey secretKey = algorithm.key().build();
        String token = Jwts.builder().claims(userClaims).signWith(secretKey).compact();

        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE,token);

        //For Validation purpose
        UserSession session = new UserSession();
        session.setToken(token);
        session.setUser(optionalUser.get());
        session.setStatus(Status.ACTIVE);
        sessionRepo.save(session);


        Pair<User,MultiValueMap<String,String>> response = new Pair<>(optionalUser.get(), headers);
        return response;

        //return optionalUser.get();

    }
}



//postman -> "anurag"
//signup -> bpe.encode("anurag")-> "dhidhihd"
//
//login -> "anurag"
//        bpe.encode("anurag")-> "dhidhihd" == "dhidhihd"
