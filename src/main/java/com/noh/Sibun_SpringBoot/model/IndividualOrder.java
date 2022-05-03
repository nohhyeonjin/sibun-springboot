package com.noh.Sibun_SpringBoot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class IndividualOrder {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonIgnore
    private RoomOrder roomOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private Menu menu;

    @NotNull
    private int amount;

    //==비즈니스 로직==//
    /**
     * 수량에 따른 가격 계산
     */
    public int getPrice() {
        int menuPrice = menu.getPrice();
        return menuPrice*amount;
    }
}
