package com.noh.Sibun_SpringBoot.repository;

import com.noh.Sibun_SpringBoot.model.ChatRoom;
import com.noh.Sibun_SpringBoot.model.RoomOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomOrderRepository {

    private final EntityManager em;

    public void save(RoomOrder roomOrder) {
        em.persist(roomOrder);
    }

    public RoomOrder findOneByChatRoom(ChatRoom chatRoom) {
        return em.createQuery("select ro from RoomOrder ro where ro.chatRoom=:chatRoom", RoomOrder.class)
                .setParameter("chatRoom", chatRoom)
                .getSingleResult();
    }

    public RoomOrder findById(Long id) {
        return em.find(RoomOrder.class, id);
    }

    public void remove(RoomOrder roomOrder) {
        em.remove(roomOrder);
    }
}
