package com.noh.Sibun_SpringBoot.repository;

import com.noh.Sibun_SpringBoot.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final EntityManager em;

    public void save(ChatRoom chatRoom) {
        em.persist(chatRoom);
    }

    public ChatRoom findById(Long id) {
        return em.find(ChatRoom.class, id);
    }

    public void remove(ChatRoom chatRoom) {
        em.remove(chatRoom);
    }
}
