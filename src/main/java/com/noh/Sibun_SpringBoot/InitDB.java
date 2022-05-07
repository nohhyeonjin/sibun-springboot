package com.noh.Sibun_SpringBoot;

import com.noh.Sibun_SpringBoot.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initMember();
        initService.initStore1();
        initService.initStore2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void initMember() {
            Member member = createMember("testMember@naver.com", "testPwd", MemberType.NORMAL);
            em.persist(member);
        }

        public void initStore1() {
            Member member = createMember("storeOwner@gmail.com", "storePwd", MemberType.STORE);
            em.persist(member);

            Category category = createCategory("야식");
            em.persist(category);

            Store store = createStore("부산시", "명륜로", "12345", "던벼락막창", category, member, 15000, 2500);
            em.persist(store);

            Menu menu1 = createMenu(store, "막창", 16900);
            Menu menu2 = createMenu(store, "삼겹살", 8500);
            em.persist(menu1);
            em.persist(menu2);
        }

        public void initStore2() {
            Member member = createMember("secondStore@daum.net", "storePwdd", MemberType.STORE);
            em.persist(member);

            Category category = createCategory("치킨");
            em.persist(category);

            Store store = createStore("부산시", "동래로", "98766", "처갓집치킨", category, member, 12000, 3000);
            em.persist(store);

            Menu menu1 = createMenu(store, "슈프림골드", 21000);
            Menu menu2 = createMenu(store, "후라이드", 17000);
            em.persist(menu1);
            em.persist(menu2);
        }

        private Member createMember(String email, String pwd, MemberType type) {
            Member member = new Member();
            member.setEmail(email);
            member.setPwd(pwd);
            member.setType(type);
            return member;
        }

        private Category createCategory(String name) {
            Category category = new Category();
            category.setName(name);
            return category;
        }

        private Store createStore(String city, String street, String zipcode, String name, Category category, Member member, int minimumPrice, int deliveryFee) {
            Store store = new Store();
            store.setAddress(new Address(city, street, zipcode));
            store.setName(name);
            store.setCategory(category);
            store.setOwner(member);
            store.setMinimumPrice(minimumPrice);
            store.setDeliveryFee(deliveryFee);
            return store;
        }

        private Menu createMenu(Store store, String name, int price) {
            Menu menu = new Menu();
            menu.setStore(store);
            menu.setName(name);
            menu.setPrice(price);
            return menu;
        }
    }
}
