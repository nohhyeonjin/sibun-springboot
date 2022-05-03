package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.ChatRoom;
import com.noh.Sibun_SpringBoot.model.IndividualOrder;
import com.noh.Sibun_SpringBoot.model.OrderState;
import com.noh.Sibun_SpringBoot.model.RoomOrder;
import com.noh.Sibun_SpringBoot.repository.RoomOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomOrderService {

    private final RoomOrderRepository roomOrderRepository;

    @Transactional
    public Long createRoomOrder(RoomOrder roomOrder) {
        roomOrder.setOrderState(OrderState.BEFORE);
        roomOrderRepository.save(roomOrder);
        return roomOrder.getId();
    }

    @Transactional
    public void addIndividualOrder(ChatRoom chatRoom, IndividualOrder individualOrder) {
        RoomOrder roomOrder = roomOrderRepository.findOneByChatRoom(chatRoom);
        roomOrder.addIndividualOrder(individualOrder);
    }
}
