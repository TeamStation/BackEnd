package com.teamstation.teamstation.service.Impl;

import com.teamstation.teamstation.dto.MemberDto;
import com.teamstation.teamstation.dto.MemberUpdateDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.dto.MemberSignUpRequestDto;
import com.teamstation.teamstation.service.MemberService;
import com.teamstation.teamstation.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public MemberDto signUp(MemberSignUpRequestDto requestDto) throws Exception {
        if(!requestDto.getPassword().equals(requestDto.getCheckedPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        validateDuplicateMember(requestDto);

        Member newMember = memberRepository.save(requestDto.toEntity());

        newMember.encodePassword(passwordEncoder);

        MemberDto memberDto = new MemberDto();
        memberDto.setId(newMember.getId());
        memberDto.setEmail(newMember.getEmail());
        memberDto.setMemberName(newMember.getMemberName());

        return memberDto;
    }

    private void validateDuplicateMember(MemberSignUpRequestDto requestDto) {
        Optional<Member> findMember = memberRepository.findByEmail(requestDto.getEmail());
        if(findMember.isPresent()){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    public Member getMemberById(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            // 멤버가 존재하지 않을 경우 예외 처리
            throw new IllegalArgumentException("멤버를 찾을 수 없습니다.");
        }
        return optionalMember.get();
    }

    public List<Member> getMembersByIds(List<Long> memberIds) {
        List<Member> members = memberRepository.findByIdIn(memberIds);
        return members;
    }

    @Override
    public MemberDto getMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        MemberDto memberDto = new MemberDto();
        if(member.isPresent()){
            memberDto.setId(member.get().getId());
            memberDto.setMemberName(member.get().getMemberName());
            memberDto.setEmail(member.get().getEmail());
        }
        return memberDto;
    }

    @Override
    public MemberUpdateDto updateMember(MemberUpdateDto memberUpdateDto, Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isEmpty()){
            throw new EntityNotFoundException("회원이 존재하지 않습니다");
        }

        member.get().setEmail(memberUpdateDto.getEmail());
        member.get().setMemberName(memberUpdateDto.getMemberName());

        MemberUpdateDto updatedMember = new MemberUpdateDto();

        updatedMember.setEmail(member.get().getEmail());
        updatedMember.setMemberName(member.get().getMemberName());

        return updatedMember;
    }

    @Override
    public void deleteMember(Long id) throws Exception {
        Optional<Member> member = memberRepository.findById(id);
        member.ifPresent(memberRepository::delete);
    }

    @Override
    public String login(Map<String, String> member) {
        Member loginMember = memberRepository.findByEmail(member.get("email"))
                .orElseThrow(() -> new EntityNotFoundException("가입되지 않은 Email 입니다."));
        String password = member.get("password");
        if (!passwordEncoder.matches(member.get("password"), loginMember.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(loginMember.getUsername());
    }

}
