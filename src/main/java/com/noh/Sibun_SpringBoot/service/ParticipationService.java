package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.Participation;
import com.noh.Sibun_SpringBoot.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    @Transactional
    public Long participate(Participation participation) {
        participationRepository.save(participation);
        return participation.getId();
    }
}
