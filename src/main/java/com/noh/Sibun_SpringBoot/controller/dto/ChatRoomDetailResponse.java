package com.noh.Sibun_SpringBoot.controller.dto;

import com.noh.Sibun_SpringBoot.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatRoomDetailResponse {

    private Address deliveryAddress;
    private LocalDateTime orderExpectedTime;
    private String storeName;

}
