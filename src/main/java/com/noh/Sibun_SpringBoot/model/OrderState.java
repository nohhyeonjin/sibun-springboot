package com.noh.Sibun_SpringBoot.model;

public enum OrderState {
    //주문전 //주문대기 //주문승낙 //주문거절
    BEFORE,
    WAITING,
    CONSENT,
    REJECT
}
