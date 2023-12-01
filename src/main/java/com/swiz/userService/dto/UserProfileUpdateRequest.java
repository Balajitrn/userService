package com.swiz.userService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpdateRequest{
    private String username;
    private String email;
    private String password;

}
