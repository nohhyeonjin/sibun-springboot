package com.noh.Sibun_SpringBoot.controller.dto;

import com.noh.Sibun_SpringBoot.model.Address;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CreateChatRoomRequest {

    Long memberId;
    Long storeId;

    Address deliveryAddress;
    LocalDateTime orderExpectedTime;

}
