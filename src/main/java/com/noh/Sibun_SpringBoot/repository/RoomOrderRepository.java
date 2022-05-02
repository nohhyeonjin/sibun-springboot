package com.noh.Sibun_SpringBoot.repository;

import com.noh.Sibun_SpringBoot.model.RoomOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class RoomOrderRepository {

    private final EntityManager em;

    public void save(RoomOrder roomOrder) {
        em.persist(roomOrder);
    }
}
