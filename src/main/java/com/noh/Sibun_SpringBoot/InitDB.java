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
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            System.out.println("Init1" + this.getClass());
            Member member = createMember("storeOwner@gmail.com", "storePwd", MemberType.STORE);
            em.persist(member);

            Category category = createCategory("야식");
            em.persist(category);

            Store store = createStore("부산시", "명륜로", "12345", "던벼락막창", category, member);
            em.persist(store);
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

        private Store createStore(String city, String street, String zipcode, String name, Category category, Member member) {
            Store store = new Store();
            store.setAddress(new Address(city, street, zipcode));
            store.setName(name);
            store.setCategory(category);
            store.setOwner(member);
            return store;
        }
    }
}
