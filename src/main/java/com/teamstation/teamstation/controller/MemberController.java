package com.teamstation.teamstation.controller;

import com.teamstation.teamstation.dto.MemberDto;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.dto.MemberSignUpRequestDto;
import com.teamstation.teamstation.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody MemberSignUpRequestDto memberDto) throws Exception {
        try{
            MemberDto savedMember = memberService.signUp(memberDto);
            return ResponseEntity.status(HttpStatus.OK).body(savedMember);
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 회원입니다.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("비밀번호가 일치하지 않습니다.");
        }
    }

}
