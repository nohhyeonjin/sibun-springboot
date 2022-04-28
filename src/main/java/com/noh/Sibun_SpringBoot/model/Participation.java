package com.noh.Sibun_SpringBoot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Participation {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @Enumerated(EnumType.STRING)
    private Role role;
}
