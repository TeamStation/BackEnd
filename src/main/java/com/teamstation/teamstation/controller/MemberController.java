package com.teamstation.teamstation.controller;

import com.teamstation.teamstation.dto.MemberDto;
import com.teamstation.teamstation.dto.MemberUpdateDto;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.dto.MemberSignUpRequestDto;
import com.teamstation.teamstation.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @CrossOrigin(origins = "http://localhost:8080")
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


    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> member) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(memberService.login(member));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("가입되지 않은 E-MAIL입니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 비밀번호입니다.");
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping()
    public ResponseEntity<Object> getMembers(@PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(userId));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("가입되지 않은 회원입니다.");
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getMember(@PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(userId));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("가입되지 않은 회원입니다.");
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/update/{memberId}")
    public ResponseEntity<Object> updateMember(@RequestBody MemberUpdateDto memberUpdateDto, @PathVariable Long memberId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(memberUpdateDto, memberId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("가입되지 않은 회원입니다.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteMember(@PathVariable Long userId) throws Exception {
        try {
            memberService.deleteMember(userId);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 삭제되었습니다.");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("가입되지 않은 회원입니다.");
        }
    }

}
