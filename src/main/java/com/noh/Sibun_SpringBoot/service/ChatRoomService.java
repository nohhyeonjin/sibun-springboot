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
    private final RoomOrderService roomOrderService;

    @Transactional
    public ChatRoom createChatRoom(Address deliveryAddress, LocalDateTime orderExpectedTime) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setDeliveryAddress(deliveryAddress);
        chatRoom.setOrderExpectedTime(orderExpectedTime);

        chatRoomRepository.save(chatRoom);
        return chatRoom;
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

    public List<Menu> findChatRoomStoreMenus(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        RoomOrder roomOrder = roomOrderRepository.findOneByChatRoom(chatRoom);
        return roomOrder.getStore().getMenuList();
    }

    public List<IndividualOrder> chatRoomOrderList(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        RoomOrder roomOrder = roomOrderRepository.findOneByChatRoom(chatRoom);
        return roomOrder.getIndividualOrderList();
    }

    @Transactional
    public void removeChatRoom(Long chatRoomid) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomid);
        roomOrderService.removeRelatedToChatRoom(chatRoom);
        chatRoomRepository.remove(chatRoom);
    }
}
