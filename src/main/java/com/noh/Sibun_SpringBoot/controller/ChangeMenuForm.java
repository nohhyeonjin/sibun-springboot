package com.noh.Sibun_SpringBoot.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChangeMenuForm {

    private Long individualOrderId;
    private Long menuId;
    private int amount;

}
