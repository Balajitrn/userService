package com.swiz.userService.service;

import com.swiz.userService.dto.UserRegistrationRequest;
import com.swiz.userService.entity.AppUser;
import com.swiz.userService.repository.UserRepository;
import com.swiz.userService.security.AuthenticationFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationFacade authenticationFacade;

    @Test
    public void testRegisterUser(){
        //setup the date
        AppUser user = AppUser.builder()
                .id(1L)
                .username("balaji") // ree34444 // ree3445 // ree3446 // ree34447
                .email("balaji@hello.com")
                .password("hello_123")
                .build();

        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                .username("balaji")
                .email("balaji@hello.com")
                .password("hello_123")
                .build();

        //stub the mock
        when(userRepository.findByEmail(userRegistrationRequest.getEmail())).thenReturn(null);
        //when(userRepository.save(any(AppUser.class))).thenReturn(user);
        when(passwordEncoder.encode(userRegistrationRequest.getPassword())).thenReturn("encodedPassword");
        ArgumentCaptor<AppUser> argumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        //execute
        userService.registerUser(userRegistrationRequest);
        //validate
        verify(userRepository).save(argumentCaptor.capture());
        AppUser savedUser = argumentCaptor.getValue();

        assertEquals("balaji",savedUser.getUsername(),"user name should match");
        assertEquals("balaji@hello.com",savedUser.getEmail(),"email should match");
        assertEquals("encodedPassword",savedUser.getPassword(),"password should be same");
    }

}