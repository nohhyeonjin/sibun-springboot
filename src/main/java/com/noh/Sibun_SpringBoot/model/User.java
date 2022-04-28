package com.noh.Sibun_SpringBoot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private String pwd;

    @Enumerated(EnumType.STRING)
    private UserType type;
}
