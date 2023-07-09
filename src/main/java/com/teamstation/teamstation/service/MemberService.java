package com.teamstation.teamstation.service;

import com.teamstation.teamstation.dto.MemberDto;
import com.teamstation.teamstation.dto.MemberSignUpRequestDto;
import com.teamstation.teamstation.dto.MemberUpdateDto;
import com.teamstation.teamstation.entity.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {

    MemberDto signUp(MemberSignUpRequestDto requestDto) throws Exception;

    String login(Map<String, String> member);

    Member getMemberById(Long memberId);
    List<Member> getMembersByIds(List<Long> memberIds);

    MemberDto getMember(Long id) throws Exception;
    MemberUpdateDto updateMember(MemberUpdateDto memberUpdateDto, Long memberId) throws Exception;
    void deleteMember(Long id) throws Exception;

}
