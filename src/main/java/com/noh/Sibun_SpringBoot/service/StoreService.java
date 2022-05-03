package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.ChatRoom;
import com.noh.Sibun_SpringBoot.model.RoomOrder;
import com.noh.Sibun_SpringBoot.model.Store;
import com.noh.Sibun_SpringBoot.repository.RoomOrderRepository;
import com.noh.Sibun_SpringBoot.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final RoomOrderRepository roomOrderRepository;

    public Store findById(Long id) {
        return storeRepository.findById(id);
    }

    public Store findByChatRoom(ChatRoom chatRoom) {
        RoomOrder roomOrder = roomOrderRepository.findOneByChatRoom(chatRoom);
        return roomOrder.getStore();
    }
}
