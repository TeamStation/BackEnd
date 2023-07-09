package com.teamstation.teamstation.entity;

import com.teamstation.teamstation.constant.ProjectState;
import com.teamstation.teamstation.dto.ProjectDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@Getter
@Setter
@ToString
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String detail;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    private String deadLine;

    @Enumerated(EnumType.STRING)
    private ProjectState projectState;

    public Project createProject(ProjectDto projectDto){
        Project project = new Project();
        project.name = projectDto.getName();
        project.detail = projectDto.getDetail();
        project.deadLine = projectDto.getDeadLine();
        project.projectState = projectDto.getProjectState();
        return project;
    }

    public void updateProject(ProjectDto projectDto){
        this.name = projectDto.getName();
        this.detail = projectDto.getDetail();
        this.updateDate = projectDto.getUpdateDate();
        this.deadLine = projectDto.getDeadLine();
        this.projectState = projectDto.getProjectState();
    }


}
