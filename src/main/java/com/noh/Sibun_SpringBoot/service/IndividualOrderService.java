package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.IndividualOrder;
import com.noh.Sibun_SpringBoot.model.Menu;
import com.noh.Sibun_SpringBoot.model.RoomOrder;
import com.noh.Sibun_SpringBoot.repository.IndividualOrderRepository;
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

    public IndividualOrder findById(Long id) {
        return individualOrderRepository.findById(id);
    }

    @Transactional
    public Long changeMenu(IndividualOrder individualOrder, Menu menu, int amount) {
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
}
