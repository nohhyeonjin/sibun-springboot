package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.*;
import com.noh.Sibun_SpringBoot.repository.ChatRoomRepository;
import com.noh.Sibun_SpringBoot.repository.RoomOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final RoomOrderRepository roomOrderRepository;

    @Transactional
    public Long createChatRoom(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
        return chatRoom.getId();
    }

    @Transactional
    public Long modifyChatRoom(ChatRoom chatRoom) {
        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoom.getId());
        findChatRoom.setDeliveryAddress(chatRoom.getDeliveryAddress());
        findChatRoom.setOrderExpectedTime(chatRoom.getOrderExpectedTime());
        return findChatRoom.getId();
    }

    public ChatRoom findById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }

    public List<Menu> findChatRoomStoreMenus(ChatRoom chatRoom) {
        RoomOrder roomOrder = roomOrderRepository.findOneByChatRoom(chatRoom);
        Store store = roomOrder.getStore();
        return store.getMenuList();
    }
}
