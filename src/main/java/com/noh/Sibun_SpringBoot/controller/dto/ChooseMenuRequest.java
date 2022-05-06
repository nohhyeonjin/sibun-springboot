package com.noh.Sibun_SpringBoot.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChooseMenuRequest {

    private Long memberId;
    private Long chatRoomId;
    private Long menuId;
    private int amount;

}
