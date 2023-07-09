package com.teamstation.teamstation.service;

import com.teamstation.teamstation.dto.ProjectDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectMember;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.repository.ProjectMemberRepository;
import com.teamstation.teamstation.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ProjectMemberRepository projectMemberRepository;

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

    public Long saveProject(ProjectDto projectDto, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Project project = new Project();
    }
}
