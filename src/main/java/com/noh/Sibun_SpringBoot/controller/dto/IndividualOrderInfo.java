package com.noh.Sibun_SpringBoot.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IndividualOrderInfo {

    private Long userId;
    private String menu;
    private int amount;
    private int price;

}
