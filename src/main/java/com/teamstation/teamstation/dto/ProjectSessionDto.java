package com.teamstation.teamstation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectSessionDto {
    private SessionDto session;
    private Long projectId;
}
