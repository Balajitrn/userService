package com.swiz.userService.repository;

import com.swiz.userService.entity.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        AppUser mockUser = new AppUser();
        mockUser.setUsername("balaji");

        //stub the mockuser
        when(userRepository.findByUsername("username")).thenReturn(mockUser);
    }

    @Test
    public void testFindByUserName() {
        //setup
        AppUser appuser = AppUser.builder()
                .id(1L)
                .username("balaji")
                .email("balaji@hello.com")
                .password("hello_123")
                .build();

        //execute
        userRepository.save(appuser);
        AppUser foundUser = userRepository.findByUsername("username");
        assertNotNull(foundUser);
        System.out.println("*************************"+foundUser);
        assertEquals(appuser.getUsername(),foundUser.getUsername());

    }
}