package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpRequestDto {

    private String email;

    private String password;

    private String userName;

    private String checkedPassword;

    @Builder
    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .build();
    }

}
