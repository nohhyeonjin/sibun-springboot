package com.noh.Sibun_SpringBoot.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuResponse {

    private String name;
    private int price;

}
