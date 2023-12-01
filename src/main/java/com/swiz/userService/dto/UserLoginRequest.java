package com.swiz.userService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {

    private String email;
    private String password;
}
