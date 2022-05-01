package com.noh.Sibun_SpringBoot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class ChatRoom {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address deliveryAddress;

    private LocalDateTime orderExpectedTime;

}
