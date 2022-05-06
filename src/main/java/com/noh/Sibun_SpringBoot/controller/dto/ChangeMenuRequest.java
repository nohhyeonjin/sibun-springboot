package com.noh.Sibun_SpringBoot.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChangeMenuRequest {

    private Long individualOrderId;
    private Long menuId;
    private int amount;

}
