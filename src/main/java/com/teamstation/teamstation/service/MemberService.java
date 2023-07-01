package com.teamstation.teamstation.service;

import com.teamstation.teamstation.dto.MemberDto;
import com.teamstation.teamstation.dto.MemberSignUpRequestDto;

public interface MemberService {

    // 회원가입
     MemberDto signUp(MemberSignUpRequestDto requestDto) throws Exception;
}
