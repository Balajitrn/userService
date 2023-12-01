package com.swiz.userService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiz.userService.component.JwtTokenProvider;
import com.swiz.userService.dto.UserRegistrationRequest;
import com.swiz.userService.entity.AppUser;
import com.swiz.userService.security.CustomUserDetailsService;
import com.swiz.userService.service.AuthService;
import com.swiz.userService.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthService authService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testRegisterEndPoint() throws Exception {
        //setup
        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                .username("balaji")
                .email("balaji@hello.com")
                .password("hello_123")
                .build();

        AppUser appUser = AppUser.builder()
                        .id(1L)
                        .username("balaji")
                        .email("balaji@hello.com")
                        .password("hello_123")
                        .build();

        //execute
        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(appUser)))
                .andDo(print())
                .andExpect(status().isCreated());

        //validate
        verify(userService).registerUser(userRegistrationRequest);
    }

}