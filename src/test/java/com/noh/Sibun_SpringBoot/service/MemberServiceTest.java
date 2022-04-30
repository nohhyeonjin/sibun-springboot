package com.noh.Sibun_SpringBoot.service;

import com.noh.Sibun_SpringBoot.model.Member;
import com.noh.Sibun_SpringBoot.model.MemberType;
import com.noh.Sibun_SpringBoot.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setEmail("test@gmail.com");
        member.setPwd("pwd123");
        member.setType(MemberType.NORMAL);

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findById(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setEmail("test@gmail.com");
        member2.setEmail("test@gmail.com");

        //when
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });

        //then
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
    }

    @Test
    public void 로그인() throws Exception {
        //given
        Member member = new Member();
        member.setEmail("test@gmail.com");
        member.setPwd("pwd123");
        memberService.join(member);

        Member logMember = new Member();
        logMember.setEmail("test@gmail.com");
        logMember.setPwd("pwd123");

        //when
        Long loginId = memberService.login(logMember);

        //then
        assertEquals(member, memberRepository.findById(loginId));
    }

    @Test
    public void 비밀번호_오류_예외() throws Exception {
        //given
        Member member = new Member();
        member.setEmail("test@gmail.com");
        member.setPwd("pwd123");
        memberService.join(member);

        Member logMember = new Member();
        logMember.setEmail("test@gmail.com");
        logMember.setPwd("wrongpwd");

        //when
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            memberService.login(logMember);
        });

        //then
        assertEquals("존재하지 않는 회원입니다.", thrown.getMessage());
    }
}