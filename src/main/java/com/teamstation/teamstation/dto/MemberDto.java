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

    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Member toEntity() {
        Member member = new Member();
        member.setId(this.id);
        member.setUserName(this.userName);
        member.setEmail(this.email);

        return member;
    }
}
