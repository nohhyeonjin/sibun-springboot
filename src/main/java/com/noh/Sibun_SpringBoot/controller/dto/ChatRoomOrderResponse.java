package com.noh.Sibun_SpringBoot.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatRoomOrderResponse {

    private int totalPrice;
    private List<IndividualOrderInfo> data;

}

