package com.noh.Sibun_SpringBoot.controller;

import com.noh.Sibun_SpringBoot.model.*;
import com.noh.Sibun_SpringBoot.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final RoomOrderService roomOrderService;
    private final StoreService storeService;
    private final MemberService memberService;
    private final ParticipationService participationService;

    @PostMapping("/createChatRoom")
    public Long createChatRoom(@RequestBody ChatRoomForm chatRoomForm) {
        // 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setDeliveryAddress(chatRoomForm.getDeliveryAddress());
        chatRoom.setOrderExpectedTime(chatRoomForm.getOrderExpectedTime());
        chatRoomService.createChatRoom(chatRoom);

        // 채팅방 주문 생성
        RoomOrder roomOrder = new RoomOrder();
        roomOrder.setChatRoom(chatRoom);
        Store store = storeService.findById(chatRoomForm.getStoreId());
        roomOrder.setStore(store);
        roomOrderService.createRoomOrder(roomOrder);

        // 채팅방 참여
        Participation participation = new Participation();
        participation.setChatRoom(chatRoom);
        Member member = memberService.findById(chatRoomForm.getMemberId());
        participation.setMember(member);
        participation.setRole(Role.MASTER);
        participationService.participate(participation);

        return chatRoom.getId();
    }
}
