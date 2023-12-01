package com.swiz.userService.service;

import com.swiz.userService.component.JwtTokenProvider;
import com.swiz.userService.dto.UserLoginRequest;
import com.swiz.userService.entity.AppUser;
import com.swiz.userService.exception.UnauthorizedException;
import com.swiz.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;


    public String authenticate(UserLoginRequest loginRequest) {
        // Find the user by email
        System.out.println("***********************************************"+loginRequest.getEmail());
        AppUser user = userRepository.findByEmail(loginRequest.getEmail());
        System.out.println("***********************************************"+user);
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            System.out.println("exeption occured in authenticate");
            throw new UnauthorizedException("Invalid username or password");
        }

        // Generate a token for the user
        String token = tokenProvider.generateToken(user.getUsername());
        return token;
    }
}
