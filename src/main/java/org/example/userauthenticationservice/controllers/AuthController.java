package org.example.userauthenticationservice.controllers;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthenticationservice.dtos.LoginRequestDto;
import org.example.userauthenticationservice.dtos.SignupRequestDto;
import org.example.userauthenticationservice.dtos.UserDto;
import org.example.userauthenticationservice.dtos.ValidateTokenRequest;
import org.example.userauthenticationservice.exceptions.UserAlreadyExistException;
import org.example.userauthenticationservice.models.User;
import org.example.userauthenticationservice.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
            User user = authService.signup(signupRequestDto.getEmail(), signupRequestDto.getPassword());
            return from(user);
        }catch (UserAlreadyExistException exception) {
            throw exception;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Pair<User, MultiValueMap<String,String>> response = authService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());
        UserDto user = from(response.a);
        return new ResponseEntity<>(user,response.b, HttpStatusCode.valueOf(200));

    }


    @PostMapping("/validateToken")
    public Boolean validateToken(@RequestBody ValidateTokenRequest validateTokenRequest) {
        return authService.validateToken(validateTokenRequest.getUserId(), validateTokenRequest.getToken());
    }

    public UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setRoles(user.getRoles());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
