package com.teamstation.teamstation.service;

import com.teamstation.teamstation.dto.MemberDto;
import com.teamstation.teamstation.dto.MemberSignUpRequestDto;
import com.teamstation.teamstation.entity.Member;

public interface MemberService {

    // 회원가입
     MemberDto signUp(MemberSignUpRequestDto requestDto) throws Exception;

    Member getMemberById(Long memberId);

}
