package com.noh.Sibun_SpringBoot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class Menu {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Store store;

    private String name;

    private int price;

}
