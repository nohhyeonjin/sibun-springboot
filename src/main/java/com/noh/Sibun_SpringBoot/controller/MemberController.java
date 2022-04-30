package com.noh.Sibun_SpringBoot.controller;

import com.noh.Sibun_SpringBoot.model.Member;
import com.noh.Sibun_SpringBoot.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public JoinResponse join(@RequestBody Member member) {
        Long id = memberService.join(member);
        return new JoinResponse(id);
    }

    @Data
    private class JoinResponse {
        private Long id;

        public JoinResponse(Long id){
            this.id = id;
        }
    }

    @GetMapping("/login")
    public LoginResponse login(@RequestParam String email,
                               @RequestParam String pwd) {
        Member member = new Member();
        member.setEmail(email);
        member.setPwd(pwd);

        System.out.println("member.getEmail() = " + member.getEmail());
        System.out.println("member.getPwd() = " + member.getPwd());

        Long id = memberService.login(member);
        return new LoginResponse(id);
    }

    @Data
    private class LoginResponse {
        private Long id;

        public LoginResponse(Long id) {
            this.id = id;
        }
    }
}
