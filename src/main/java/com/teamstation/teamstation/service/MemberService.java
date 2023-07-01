package com.teamstation.teamstation.service;

import com.teamstation.teamstation.dto.MemberDto;
import com.teamstation.teamstation.dto.MemberSignUpRequestDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
    }
    public List<Member> getMembersByIds(List<Long> memberIds) {
        return memberRepository.findAllById(memberIds);
    }

    // 회원가입
    MemberDto signUp(MemberSignUpRequestDto requestDto) throws Exception;
}
