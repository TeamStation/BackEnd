package com.teamstation.teamstation.entity;


import com.teamstation.teamstation.constant.ProjectMemberRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "project_member")
@Getter
@Setter
public class ProjectMember {
    @Id
    @Column(name = "project_member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private ProjectMemberRole projectMemberRole;

    public ProjectMember createProjectMember(Project project, Member member, ProjectMemberRole projectMemberRole) {
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(project);
        projectMember.setMember(member);
        projectMember.setProjectMemberRole(projectMemberRole);
        return projectMember;
    }
}
