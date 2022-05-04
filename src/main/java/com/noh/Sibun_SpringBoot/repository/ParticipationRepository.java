package com.noh.Sibun_SpringBoot.repository;

import com.noh.Sibun_SpringBoot.model.ChatRoom;
import com.noh.Sibun_SpringBoot.model.Member;
import com.noh.Sibun_SpringBoot.model.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParticipationRepository {

    private final EntityManager em;

    public void save(Participation participation) {
        em.persist(participation);
    }

    public int removeInChatRoom(ChatRoom chatRoom) {
        return em.createQuery("delete from Participation p where p.chatRoom=:chatRoom")
                .setParameter("chatRoom", chatRoom)
                .executeUpdate();
    }
}
