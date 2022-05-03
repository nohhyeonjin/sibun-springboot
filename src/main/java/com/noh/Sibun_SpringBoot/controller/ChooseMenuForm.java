package com.noh.Sibun_SpringBoot.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChooseMenuForm {

    private Long memberId;
    private Long chatRoomId;
    private Long menuId;
    private int amount;

}
