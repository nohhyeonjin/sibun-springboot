package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.controller.dto.CombinedIndividualMenuInfo;
import com.noh.Sibun_SpringBoot.controller.dto.RoomOrderDetail;
import com.noh.Sibun_SpringBoot.model.*;
import com.noh.Sibun_SpringBoot.repository.ChatRoomRepository;
import com.noh.Sibun_SpringBoot.repository.RoomOrderRepository;
import com.noh.Sibun_SpringBoot.repository.StoreRepository;
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
    private final ChatRoomRepository chatRoomRepository;
    private final StoreRepository storeRepository;
    private final IndividualOrderService individualOrderService;

    @Transactional
    public RoomOrder createRoomOrder(ChatRoom chatRoom, Long storeId) {
        Store store = storeRepository.findById(storeId);
        RoomOrder roomOrder = new RoomOrder();
        roomOrder.setChatRoom(chatRoom);
        roomOrder.setStore(store);
        roomOrder.setOrderState(OrderState.READY);
        roomOrderRepository.save(roomOrder);

        return roomOrder;
    }

    @Transactional
    public void addIndividualOrder(Long chatRoomId, IndividualOrder individualOrder) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        RoomOrder roomOrder = roomOrderRepository.findOneByChatRoom(chatRoom);
        roomOrder.addIndividualOrder(individualOrder);
    }

    @Transactional
    public Long order(Long id) {
        RoomOrder roomOrder = roomOrderRepository.findById(id);
        Store store = roomOrder.getStore();
        int minimumPrice = store.getMinimumPrice();
        int roomOrderPrice = roomOrder.getTotalPrice();

        if (roomOrderPrice < minimumPrice) {
            throw new IllegalStateException("최소주문금액을 만족하지 않습니다.");
        }

        roomOrder.setOrderState(OrderState.COMPLETE);
        store.addOrder(roomOrder);

        return roomOrder.getId();
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
