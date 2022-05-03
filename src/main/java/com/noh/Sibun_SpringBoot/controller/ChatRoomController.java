package com.noh.Sibun_SpringBoot.controller;

import com.noh.Sibun_SpringBoot.model.*;
import com.noh.Sibun_SpringBoot.service.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


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

    @PostMapping("/modifyChatRoom")
    public Long modifyChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.modifyChatRoom(chatRoom);
    }

    @GetMapping("/participateChatRoom")
    public Long participateChatRoom(@RequestParam Long memberId,
                                    @RequestParam Long chatRoomId) {
        Participation participation = new Participation();
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
        participation.setChatRoom(chatRoom);
        Member member = memberService.findById(memberId);
        participation.setMember(member);
        participation.setRole(Role.NORMAL);

        return participationService.participate(participation);
    }

    @GetMapping("/chatRoomDetail")
    public ChatRoomDetailResponse chatRoomDetail(@RequestParam Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
        Store store = storeService.findByChatRoom(chatRoom);
        return new ChatRoomDetailResponse(chatRoom.getDeliveryAddress(), chatRoom.getOrderExpectedTime(), store.getName());
    }

    @Data
    private class ChatRoomDetailResponse {

        private Address deliveryAddress;
        private LocalDateTime orderExpectedTime;
        private String storeName;

        public ChatRoomDetailResponse(Address deliveryAddress, LocalDateTime orderExpectedTime, String storeName) {
            this.deliveryAddress = deliveryAddress;
            this.orderExpectedTime = orderExpectedTime;
            this.storeName = storeName;
        }
    }
}
