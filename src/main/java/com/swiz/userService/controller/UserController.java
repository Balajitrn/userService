package com.swiz.userService.controller;

import com.swiz.userService.dto.AuthenticationResponse;
import com.swiz.userService.dto.UserLoginRequest;
import com.swiz.userService.dto.UserProfileUpdateRequest;
import com.swiz.userService.dto.UserRegistrationRequest;
import com.swiz.userService.service.AuthService;
import com.swiz.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService){
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest registrationRequest){
        try{
            userService.registerUser(registrationRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest){
        try{
            String token = authService.authenticate(loginRequest);
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileUpdateRequest updateRequest){
        try{
            userService.updateProfile(updateRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
