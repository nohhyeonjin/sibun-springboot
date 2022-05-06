package com.noh.Sibun_SpringBoot.controller.dto;

import com.noh.Sibun_SpringBoot.controller.dto.CombinedIndividualMenuInfo;
import com.noh.Sibun_SpringBoot.model.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoomOrderDetail {

    private Long roomOrderId;
    private OrderState orderState;
    private List<CombinedIndividualMenuInfo> menuInfoList;
    private int totalPrice;

}
