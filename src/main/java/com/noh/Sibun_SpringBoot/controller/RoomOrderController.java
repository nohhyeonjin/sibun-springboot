package com.noh.Sibun_SpringBoot.controller;

import com.noh.Sibun_SpringBoot.model.*;
import com.noh.Sibun_SpringBoot.service.ChatRoomService;
import com.noh.Sibun_SpringBoot.service.MemberService;
import com.noh.Sibun_SpringBoot.service.MenuService;
import com.noh.Sibun_SpringBoot.service.RoomOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomOrderController {

    private final ChatRoomService chatRoomService;
    private final MemberService memberService;
    private final MenuService menuService;
    private final RoomOrderService roomOrderService;

    @GetMapping("/chatRoomMenuList")
    public List<Menu> getChatRoomMenuList(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
        return chatRoomService.findChatRoomStoreMenus(chatRoom);
    }

    @PostMapping("/chooseMenu")
    public Long chooseMenu(@RequestBody ChooseMenuForm menuForm) {
        Member member = memberService.findById(menuForm.getMemberId());
        ChatRoom chatRoom = chatRoomService.findById(menuForm.getChatRoomId());
        Menu menu = menuService.findById(menuForm.getMenuId());

        IndividualOrder individualOrder = new IndividualOrder();
        individualOrder.setMember(member);
        individualOrder.setMenu(menu);
        individualOrder.setAmount(menuForm.getAmount());

        roomOrderService.addIndividualOrder(chatRoom, individualOrder);
        return individualOrder.getId();
    }

}
