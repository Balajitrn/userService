package com.swiz.userService.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationFacade {
    UserDetails getCurrentUser();
}
