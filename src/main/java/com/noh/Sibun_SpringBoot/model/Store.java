package com.noh.Sibun_SpringBoot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Store {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private String name;

    @Embedded
    private Address address;

    private int minimumPrice;

    private int deliveryFee;

    @OneToMany(mappedBy = "store")
    private List<RoomOrder> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Menu> menuList = new ArrayList<>();

}
