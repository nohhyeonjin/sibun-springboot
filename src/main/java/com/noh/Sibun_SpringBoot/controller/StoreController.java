package com.noh.Sibun_SpringBoot.controller;

import com.noh.Sibun_SpringBoot.controller.dto.RoomOrderDetail;
import com.noh.Sibun_SpringBoot.model.RoomOrder;
import com.noh.Sibun_SpringBoot.service.RoomOrderService;
import com.noh.Sibun_SpringBoot.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final RoomOrderService roomOrderService;

    /**
     * (가맹점용) 주문 목록 확인
     */
    @GetMapping("/orderList")
    public List<RoomOrderDetail> orderList(Long storeId) {
        List<RoomOrder> roomOrderList = storeService.getStoreRoomOrder(storeId);
        List<RoomOrderDetail> result = roomOrderList.stream()
                .map(roomOrder -> roomOrderService.getRoomOrderDetail(roomOrder))
                .collect(Collectors.toList());

        return result;
    }

}