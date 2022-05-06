package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.ChatRoom;
import com.noh.Sibun_SpringBoot.model.Member;
import com.noh.Sibun_SpringBoot.model.Participation;
import com.noh.Sibun_SpringBoot.model.Role;
import com.noh.Sibun_SpringBoot.repository.ChatRoomRepository;
import com.noh.Sibun_SpringBoot.repository.MemberRepository;
import com.noh.Sibun_SpringBoot.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    public Participation participate(Long chatRoomId, Long memberId, Role role) {
        Participation participation = new Participation();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        participation.setChatRoom(chatRoom);
        Member member = memberRepository.findById(memberId);
        participation.setMember(member);
        participation.setRole(role);

        participationRepository.save(participation);
        return participation;
    }

    public void removeParticipationInChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        participationRepository.removeInChatRoom(chatRoom);
    }
}
