package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.IndividualOrder;
import com.noh.Sibun_SpringBoot.model.Member;
import com.noh.Sibun_SpringBoot.model.Menu;
import com.noh.Sibun_SpringBoot.model.RoomOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class IndividualOrderServiceTest {

    @Autowired
    IndividualOrderService individualOrderService;

    @Test
    public void 개인주문합() throws Exception {
        //given
        Member member1 = new Member();
        Member member2 = new Member();

        Menu menu1 = createMenu("후라이드", 17000);
        Menu menu2 = createMenu("뿌링클", 22000);

        IndividualOrder individualOrder1 = createIndividualOrder(member1, menu1, 2);
        IndividualOrder individualOrder2 = createIndividualOrder(member1, menu2, 1);
        IndividualOrder individualOrder3 = createIndividualOrder(member2, menu1, 1);

        RoomOrder roomOrder = new RoomOrder();
        roomOrder.addIndividualOrder(individualOrder1);
        roomOrder.addIndividualOrder(individualOrder2);
        roomOrder.addIndividualOrder(individualOrder3);

        //when
        HashMap<String, Integer> menuMap = individualOrderService.combineIndividualOrder(roomOrder);

        //then
        assertEquals(3, menuMap.get("후라이드"));
        assertEquals(1, menuMap.get("뿌링클"));
    }

    private Menu createMenu(String name, int price) {
        Menu menu= new Menu();
        menu.setName(name);
        menu.setPrice(price);
        return menu;
    }

    private IndividualOrder createIndividualOrder(Member member, Menu menu, int amount) {
        IndividualOrder individualOrder = new IndividualOrder();
        individualOrder.setMenu(menu);
        individualOrder.setAmount(amount);
        individualOrder.setMember(member);
        return individualOrder;
    }
}