package com.swiz.userService.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "AppUser")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;


}
