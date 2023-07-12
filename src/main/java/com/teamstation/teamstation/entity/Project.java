package com.teamstation.teamstation.entity;

import com.teamstation.teamstation.constant.ProjectState;
import com.teamstation.teamstation.dto.ProjectDto;
import com.teamstation.teamstation.repository.MemberRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProjectMember> projectMembers = new ArrayList<>();

//    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<ProjectMember> projectMembers = new ArrayList<>();

    public Project createProject(ProjectDto projectDto, List<ProjectMember> projectMemberList){
        Project project = new Project();
        project.name = projectDto.getName();
        project.detail = projectDto.getDetail();
        project.regDate = LocalDateTime.now();
        project.updateDate = LocalDateTime.now();
        project.deadLine = projectDto.getDeadLine();
        project.projectState = projectDto.getProjectState();
        for (ProjectMember projectMember: projectMemberList){
            project.projectMembers.add(projectMember);
            projectMember.setProject(this);
        }
        return project;
    }

    public void updateProject(ProjectDto projectDto){
        this.name = projectDto.getName();
        this.detail = projectDto.getDetail();
        this.deadLine = projectDto.getDeadLine();
        this.projectState = projectDto.getProjectState();
    }


}
