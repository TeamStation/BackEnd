package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.constant.ProjectMemberRole;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProjectMemberDto {
    private Long projectId;
    private Long memberId;
    private ProjectMemberRole projectMemberRole;
}
