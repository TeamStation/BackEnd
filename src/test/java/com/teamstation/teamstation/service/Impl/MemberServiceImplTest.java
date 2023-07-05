package com.teamstation.teamstation.service.Impl;

import com.teamstation.teamstation.dto.MemberSignUpRequestDto;
import com.teamstation.teamstation.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceImplTest {

    @Autowired
    MemberService memberService;

    public MemberSignUpRequestDto createMember(){
        MemberSignUpRequestDto memberDto = new MemberSignUpRequestDto();
        memberDto.setEmail("test@email.com");
        memberDto.setUserName("홍길동");
        memberDto.setPassword("1234");
        memberDto.setCheckedPassword("1234");
        return memberDto;
    }

    public MemberSignUpRequestDto createMember2(){
        MemberSignUpRequestDto memberDto = new MemberSignUpRequestDto();
        memberDto.setEmail("test@email.com");
        memberDto.setUserName("홍길동");
        memberDto.setPassword("1234");
        memberDto.setCheckedPassword("12345");
        return memberDto;
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void signUpTest() throws Exception {
        MemberSignUpRequestDto member = createMember();
        MemberDto savedMember = memberService.signUp(member);

        assertEquals(member.toEntity().getEmail(), savedMember.getEmail());
        assertEquals(member.toEntity().getUserName(), savedMember.getUserName());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() throws Exception {
        MemberSignUpRequestDto member1 = createMember();
        MemberSignUpRequestDto member2 = createMember();
        memberService.signUp(member1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.signUp(member2);});

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

    @Test
    @DisplayName("비밀번호 중복 테스트")
    public void passwordTest() throws Exception {
        MemberSignUpRequestDto member2 = createMember2();

        Throwable e = assertThrows(IllegalArgumentException.class, () -> {
            memberService.signUp(member2);});

        assertEquals("비밀번호가 일치하지 않습니다.", e.getMessage());
    }

}