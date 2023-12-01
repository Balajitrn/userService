package com.swiz.userService.service;

import com.swiz.userService.dto.UserProfileUpdateRequest;
import com.swiz.userService.dto.UserRegistrationRequest;
import com.swiz.userService.entity.AppUser;
import com.swiz.userService.repository.UserRepository;
import com.swiz.userService.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,AuthenticationFacade authenticationFacade){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationFacade = authenticationFacade;
    }

    public void registerUser(UserRegistrationRequest registrationRequest) {
        // Validate the request
        if (registrationRequest.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        // Check if the email is already used
        if (userRepository.findByEmail(registrationRequest.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Create a new user entity
        AppUser user = new AppUser();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        // Set other fields as needed
        // Save the user to the database
        userRepository.save(user);
    }

    public void updateProfile(UserProfileUpdateRequest updateRequest) {
        UserDetails currentUser = authenticationFacade.getCurrentUser();
        String username = currentUser.getUsername();
        AppUser user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with username :"+ username);
        }
        // update the user's profile with the new information
        user.setUsername(updateRequest.getUsername());
        user.setEmail(updateRequest.getEmail());
        user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));


        // save and update
        userRepository.save(user);
    }
}
