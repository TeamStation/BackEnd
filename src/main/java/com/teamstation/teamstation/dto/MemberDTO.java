package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.entity.Member;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
