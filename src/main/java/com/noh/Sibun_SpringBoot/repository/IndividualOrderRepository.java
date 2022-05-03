package com.noh.Sibun_SpringBoot.repository;

import com.noh.Sibun_SpringBoot.model.IndividualOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class IndividualOrderRepository {

    private final EntityManager em;

    public IndividualOrder findById(Long id) {
        return em.find(IndividualOrder.class, id);
    }
}
