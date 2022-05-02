package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.Address;
import com.noh.Sibun_SpringBoot.model.ChatRoom;
import com.noh.Sibun_SpringBoot.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

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
}