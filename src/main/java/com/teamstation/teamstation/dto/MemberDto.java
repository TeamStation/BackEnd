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
public class MemberDto {
    private Long id;
    private String email;

    private String memberName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Member toEntity() {
        Member member = new Member();
        member.setId(this.id);
        member.setMemberName(this.memberName);
        member.setEmail(this.email);

        return member;
    }
}
