package com.noh.Sibun_SpringBoot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class RoomOrder {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Store store;

    @OneToMany(mappedBy = "roomOrder")
    private List<IndividualOrder> individualOrderList = new ArrayList<>();

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;
}
