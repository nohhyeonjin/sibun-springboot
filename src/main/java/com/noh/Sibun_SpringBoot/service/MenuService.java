package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.Menu;
import com.noh.Sibun_SpringBoot.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Menu findById(Long menuId) {
        return menuRepository.findById(menuId);
    }
}
