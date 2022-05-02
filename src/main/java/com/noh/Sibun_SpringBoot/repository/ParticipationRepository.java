package com.noh.Sibun_SpringBoot.repository;

import com.noh.Sibun_SpringBoot.model.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ParticipationRepository {

    private final EntityManager em;

    public void save(Participation participation) {
        em.persist(participation);
    }
}
