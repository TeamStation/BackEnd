package com.teamstation.teamstation.service.Impl;

import com.teamstation.teamstation.dto.MemberDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.dto.MemberSignUpRequestDto;
import com.teamstation.teamstation.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto signUp(MemberSignUpRequestDto requestDto) throws Exception {
        if(!requestDto.getPassword().equals(requestDto.getCheckedPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        validateDuplicateMember(requestDto);

        Member newMember = memberRepository.save(requestDto.toEntity());

        newMember.encodePassword(passwordEncoder);

        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(newMember.getEmail());
        memberDto.setUserName(newMember.getUserName());

        return memberDto;
    }

    private void validateDuplicateMember(MemberSignUpRequestDto requestDto) {
        Optional<Member> findMember = memberRepository.findByEmail(requestDto.getEmail());
        if(findMember.isPresent()){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
