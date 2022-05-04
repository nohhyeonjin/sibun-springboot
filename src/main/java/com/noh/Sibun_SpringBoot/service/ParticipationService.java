package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.ChatRoom;
import com.noh.Sibun_SpringBoot.model.Participation;
import com.noh.Sibun_SpringBoot.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final ChatRoomService chatRoomService;

    public Long participate(Participation participation) {
        participationRepository.save(participation);
        return participation.getId();
    }

    public void removeParticipationInChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
        participationRepository.removeInChatRoom(chatRoom);
    }
}
