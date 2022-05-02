package com.noh.Sibun_SpringBoot.repository;

import com.noh.Sibun_SpringBoot.model.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class StoreRepository {

    private final EntityManager em;

    public Store findById(Long id) {
        return em.find(Store.class, id);
    }
}
