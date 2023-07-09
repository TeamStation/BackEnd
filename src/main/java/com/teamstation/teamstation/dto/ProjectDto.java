package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.constant.ProjectState;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProjectDto {
    private Long id;
    private String projectName;
    private String projectDetail;
    private String projectDeadLine;
    private ProjectState projectState;

}
