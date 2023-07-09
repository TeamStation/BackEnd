package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.constant.ProjectState;
import com.teamstation.teamstation.entity.Project;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ProjectDto {
    private String name;
    private String detail;
    private LocalDateTime regDate;

    private LocalDateTime updateDate;
    private String deadLine;
    private ProjectState projectState;
    private List<Long> projectMemberIdList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();
    public Project createProject() {
        return modelMapper.map(this, Project.class);
    }

    public ProjectDto(Project project) {
        this.name = project.getName();
        this.detail = project.getDetail();
        this.deadLine = project.getDeadLine();
        this.projectState = project.getProjectState();
    }
}
