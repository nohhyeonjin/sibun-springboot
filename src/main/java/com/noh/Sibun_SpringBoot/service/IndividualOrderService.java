package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.IndividualOrder;
import com.noh.Sibun_SpringBoot.model.Member;
import com.noh.Sibun_SpringBoot.model.Menu;
import com.noh.Sibun_SpringBoot.model.RoomOrder;
import com.noh.Sibun_SpringBoot.repository.IndividualOrderRepository;
import com.noh.Sibun_SpringBoot.repository.MemberRepository;
import com.noh.Sibun_SpringBoot.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IndividualOrderService {

    private final IndividualOrderRepository individualOrderRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    public IndividualOrder findById(Long id) {
        return individualOrderRepository.findById(id);
    }

    @Transactional
    public Long changeMenu(Long individualOrderId, Long menuId, int amount) {
        IndividualOrder individualOrder = individualOrderRepository.findById(individualOrderId);
        Menu menu = menuRepository.findById(menuId);

        int previousPrice = individualOrder.getPrice();
        individualOrder.setMenu(menu);
        individualOrder.setAmount(amount);
        int changedPrice = individualOrder.getPrice();

        RoomOrder roomOrder = individualOrder.getRoomOrder();
        roomOrder.updateTotalPrice(previousPrice, changedPrice);

        return individualOrder.getId();
    }

    public HashMap<String, Integer> combineIndividualOrder(RoomOrder roomOrder) {
        List<IndividualOrder> individualOrderList = roomOrder.getIndividualOrderList();
        HashMap<String, Integer> menuMap = new HashMap<>();
        for (IndividualOrder individualOrder : individualOrderList) {
            String name = individualOrder.getMenu().getName();
            int orderAmount = individualOrder.getAmount();
            menuMap.compute(name, (k, v) -> v == null ? orderAmount : v + orderAmount);
        }
        return menuMap;
    }

    public IndividualOrder createIndividualOrder(Long memberId, Long menuId, int amount) {
        Member member = memberRepository.findById(memberId);
        Menu menu = menuRepository.findById(menuId);
        IndividualOrder individualOrder = new IndividualOrder();
        individualOrder.setMember(member);
        individualOrder.setMenu(menu);
        individualOrder.setAmount(amount);

        return individualOrder;
    }
}
