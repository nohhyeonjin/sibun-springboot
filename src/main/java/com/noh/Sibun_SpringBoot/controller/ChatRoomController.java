package com.noh.Sibun_SpringBoot.controller;

import com.noh.Sibun_SpringBoot.controller.dto.ChatRoomDetailResponse;
import com.noh.Sibun_SpringBoot.controller.dto.ChatRoomOrderResponse;
import com.noh.Sibun_SpringBoot.controller.dto.CreateChatRoomRequest;
import com.noh.Sibun_SpringBoot.controller.dto.IndividualOrderInfo;
import com.noh.Sibun_SpringBoot.model.*;
import com.noh.Sibun_SpringBoot.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final RoomOrderService roomOrderService;
    private final StoreService storeService;
    private final ParticipationService participationService;

    @PostMapping("/chatRoom/create")
    public Long createChatRoom(@RequestBody CreateChatRoomRequest request) {
        // 채팅방 생성
        ChatRoom chatRoom = chatRoomService.createChatRoom(request.getDeliveryAddress(), request.getOrderExpectedTime());

        // 채팅방 주문 생성
        RoomOrder roomOrder = roomOrderService.createRoomOrder(chatRoom, request.getStoreId());

        // 채팅방 참여
        participationService.participate(chatRoom.getId(), request.getMemberId(), Role.MASTER);

        return chatRoom.getId();
    }

    @PostMapping("/chatRoom/modify")
    public Long modifyChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.modifyChatRoom(chatRoom);
    }

    @GetMapping("/chatRoom/participate")
    public Long participateChatRoom(@RequestParam Long memberId,
                                    @RequestParam Long chatRoomId) {
        Participation participation = participationService.participate(chatRoomId, memberId, Role.NORMAL);

        return participation.getId();
    }

    @GetMapping("/chatRoom/detail")
    public ChatRoomDetailResponse chatRoomDetail(@RequestParam Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
        Store store = storeService.findByChatRoom(chatRoom);
        return new ChatRoomDetailResponse(chatRoom.getDeliveryAddress(), chatRoom.getOrderExpectedTime(), store.getName());
    }

    @GetMapping("/chatRoom/orderList")
    public ChatRoomOrderResponse chatRoomOrderList(@RequestParam Long chatRoomId) {
        List<IndividualOrder> individualOrderList = chatRoomService.chatRoomOrderList(chatRoomId);
        List<IndividualOrderInfo> collect = individualOrderList.stream()
                .map(m -> new IndividualOrderInfo(m.getMember().getId(), m.getMenu().getName(), m.getAmount(), m.getPrice()))
                .collect(Collectors.toList());

        int totalPrice = 0;
        if (!individualOrderList.isEmpty()) {
            totalPrice = individualOrderList.get(0).getRoomOrder().getTotalPrice();
        }
        return new ChatRoomOrderResponse(totalPrice, collect);
    }

    @DeleteMapping("/chatRoom/remove/{id}")
    public void removeChatRoom(@PathVariable("id") Long id) {
        participationService.removeParticipationInChatRoom(id);
        chatRoomService.removeChatRoom(id);
    }
}
