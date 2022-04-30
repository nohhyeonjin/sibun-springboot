package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.Member;
import com.noh.Sibun_SpringBoot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 로그인
     */
    public Long login(Member member) {
        List<Member> findMember = memberRepository.findByEmail(member.getEmail());
        validateCorrectMember(member, findMember);
        return findMember.get(0).getId();
    }

    private void validateCorrectMember(Member member, List<Member> findMember) {
        if (findMember.isEmpty() || !member.getPwd().equals(findMember.get(0).getPwd())) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }

}
