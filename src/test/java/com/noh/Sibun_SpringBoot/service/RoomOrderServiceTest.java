package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoomOrderServiceTest {

    @Autowired RoomOrderService roomOrderService;
    @Autowired EntityManager em;

    @Test
    public void 주문_최소주문금액미달() throws Exception {
        //given
        Member member = new Member();
        Menu menu = createMenu("후라이드", 17000);
        IndividualOrder individualOrder = createIndividualOrder(member, menu, 1);
        Store store = createStore("처갓집치킨", 20000, 3000);
        RoomOrder roomOrder = createRoomOrder(store, individualOrder);

        //when
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            roomOrderService.order(roomOrder.getId());
        });

        //then
        assertEquals("최소주문금액을 만족하지 않습니다.", thrown.getMessage());
    }

    private Menu createMenu(String name, int price) {
        Menu menu= new Menu();
        menu.setName(name);
        menu.setPrice(price);
        em.persist(menu);

        return menu;
    }

    private IndividualOrder createIndividualOrder(Member member, Menu menu, int amount) {
        IndividualOrder individualOrder = new IndividualOrder();
        individualOrder.setMenu(menu);
        individualOrder.setAmount(amount);
        individualOrder.setMember(member);
        em.persist(individualOrder);

        return individualOrder;
    }

    private Store createStore(String name, int minimumPrice, int deliveryFee) {
        Store store = new Store();
        store.setName(name);
        store.setMinimumPrice(minimumPrice);
        store.setDeliveryFee(deliveryFee);
        em.persist(store);

        return store;
    }

    private RoomOrder createRoomOrder(Store store, IndividualOrder individualOrder) {
        RoomOrder roomOrder = new RoomOrder();
        roomOrder.setStore(store);
        roomOrder.addIndividualOrder(individualOrder);
        em.persist(roomOrder);

        return roomOrder;
    }

}