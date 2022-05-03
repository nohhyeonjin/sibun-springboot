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

    @OneToMany(mappedBy = "roomOrder", cascade = CascadeType.ALL)
    private List<IndividualOrder> individualOrderList = new ArrayList<>();

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    //==비즈니스 로직==//
    /**
     * totalPrice 증가
     */
    public void addPrice(int price) {
        this.totalPrice+=price;
    }

    public void minusPrice(int price) {
        this.totalPrice-=price;
    }

    public void updateTotalPrice(int previousPrice, int changedPrice) {
        minusPrice(previousPrice);
        addPrice(changedPrice);
    }

    //==연관관계 메서드==//
    public void addIndividualOrder(IndividualOrder individualOrder) {
        this.individualOrderList.add(individualOrder);
        individualOrder.setRoomOrder(this);
        addPrice(individualOrder.getPrice());
    }
}
