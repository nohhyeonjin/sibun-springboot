package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.controller.CombinedIndividualMenuInfo;
import com.noh.Sibun_SpringBoot.controller.RoomOrderDetail;
import com.noh.Sibun_SpringBoot.model.*;
import com.noh.Sibun_SpringBoot.repository.RoomOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomOrderService {

    private final RoomOrderRepository roomOrderRepository;
    private final IndividualOrderService individualOrderService;

    @Transactional
    public Long createRoomOrder(RoomOrder roomOrder) {
        roomOrder.setOrderState(OrderState.READY);
        roomOrderRepository.save(roomOrder);
        return roomOrder.getId();
    }

    @Transactional
    public void addIndividualOrder(ChatRoom chatRoom, IndividualOrder individualOrder) {
        RoomOrder roomOrder = roomOrderRepository.findOneByChatRoom(chatRoom);
        roomOrder.addIndividualOrder(individualOrder);
    }

    @Transactional
    public void order(RoomOrder roomOrder) {
        roomOrder.setOrderState(OrderState.COMPLETE);
        Store store = roomOrder.getStore();
        store.addOrder(roomOrder);
    }

    public RoomOrder findById(Long id) {
        return roomOrderRepository.findById(id);
    }

    public RoomOrderDetail getRoomOrderDetail(RoomOrder roomOrder) {
        HashMap<String, Integer> combinedIndividualOrder = individualOrderService.combineIndividualOrder(roomOrder);
        Long roomOrderId = roomOrder.getId();
        OrderState state = roomOrder.getOrderState();
        List<CombinedIndividualMenuInfo> menuInfoList = combinedIndividualOrder.entrySet().stream()
                .map(m -> new CombinedIndividualMenuInfo(m.getKey(), m.getValue()))
                .collect(Collectors.toList());
        int totalPrice = roomOrder.getTotalPrice();
        return new RoomOrderDetail(roomOrderId, state, menuInfoList, totalPrice);
    }

    @Transactional
    public void removeRelatedToChatRoom(ChatRoom chatRoom) {
        RoomOrder roomOrder = roomOrderRepository.findOneByChatRoom(chatRoom);
        roomOrderRepository.remove(roomOrder);
    }
}
