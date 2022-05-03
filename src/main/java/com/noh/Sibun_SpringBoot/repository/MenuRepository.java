package com.noh.Sibun_SpringBoot.repository;

import com.noh.Sibun_SpringBoot.model.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MenuRepository {

    private final EntityManager em;

    public Menu findById(Long menuId) {
        return em.find(Menu.class, menuId);
    }
}
