package com.noh.Sibun_SpringBoot.controller;

import com.noh.Sibun_SpringBoot.controller.dto.ChangeMenuRequest;
import com.noh.Sibun_SpringBoot.controller.dto.ChooseMenuRequest;
import com.noh.Sibun_SpringBoot.controller.dto.MenuResponse;
import com.noh.Sibun_SpringBoot.model.*;
import com.noh.Sibun_SpringBoot.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoomOrderController {

    private final ChatRoomService chatRoomService;
    private final RoomOrderService roomOrderService;
    private final IndividualOrderService individualOrderService;

    @GetMapping("/order/chatRoomMenuList")
    public List<MenuResponse> getChatRoomMenuList(Long chatRoomId) {
        List<Menu> menuList = chatRoomService.findChatRoomStoreMenus(chatRoomId);
        List<MenuResponse> result = menuList.stream()
                .map(menu -> new MenuResponse(menu.getName(), menu.getPrice()))
                .collect(Collectors.toList());

        return result;
    }

    @PostMapping("/order/chooseMenu")
    public Long chooseMenu(@RequestBody ChooseMenuRequest request) {
        IndividualOrder individualOrder = individualOrderService.createIndividualOrder(request.getMemberId(), request.getMenuId(), request.getAmount());
        roomOrderService.addIndividualOrder(request.getChatRoomId(), individualOrder);
        return individualOrder.getId();
    }

    @PostMapping("/order/changeMenu")
    public Long changeMenu(@RequestBody ChangeMenuRequest request) {
        Long id = individualOrderService.changeMenu(request.getIndividualOrderId(), request.getMenuId(), request.getAmount());
        return id;
    }

    @PutMapping("/order/{id}")
    public Long order(@PathVariable("id") Long id) {
        RoomOrder roomOrder = roomOrderService.findById(id);
        roomOrderService.order(roomOrder);
        
        return roomOrder.getId();
    }

}
