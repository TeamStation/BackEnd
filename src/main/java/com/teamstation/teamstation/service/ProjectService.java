package com.teamstation.teamstation.service;

import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectMember;
import com.teamstation.teamstation.repository.ProjectMemberRepository;
import com.teamstation.teamstation.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberService memberService;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectService(ProjectRepository projectRepository, MemberService memberService, ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.memberService = memberService;
        this.projectMemberRepository = projectMemberRepository;
    }

    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    public boolean isUserInProject(Member user, Project project) {
        // 1. 프로젝트의 멤버 목록 조회
        List<ProjectMember> projectMembers = projectMemberRepository.findByProject(project);

        // 2. 사용자가 프로젝트에 속해 있는지 확인
        for (ProjectMember projectMember : projectMembers) {
            if (projectMember.getMember().equals(user)) {
                return true;
            }
        }
        return false;
    }
}
