package com.teamstation.teamstation.service;

import com.teamstation.teamstation.constant.ProjectMemberRole;
import com.teamstation.teamstation.constant.ProjectState;
import com.teamstation.teamstation.dto.ProjectDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectMember;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.repository.ProjectMemberRepository;
import com.teamstation.teamstation.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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

    public Long saveProject(ProjectDto projectDto, String curUserEmail) {
        Project project = projectDto.createProject();
        projectRepository.save(project);
        List<Long> projectMemberList = projectDto.getProjectMemberIdList();
        for (Long memberId: projectMemberList) {
            Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
            if (StringUtils.equals(curUserEmail, member.getEmail())){
                ProjectMember projectMember = ProjectMember.createProjectMember(project, member, ProjectMemberRole.ADMIN);
            } else {
                // 친구 초대 메일 보내기 로직 추가 필요
            }
        }
        return project.getId();
    }

    public List<Long> getProjectIdList(String email){
        List<ProjectMember> projectMemberList = projectMemberRepository.findByEmail(email);
        List<Long> projectIdList = new ArrayList<>();
        for (ProjectMember projectMember: projectMemberList){
            projectIdList.add(projectMember.getProject().getId());
        }
        return projectIdList;
    }

    public int getProceedingProjectSize(String email){
        List<ProjectMember> projectMemberList = projectMemberRepository.findByEmail(email);
        List<Long> projectIdList = new ArrayList<>();
        for (ProjectMember projectMember: projectMemberList){
            if (StringUtils.equals(projectMember.getProject().getProjectState().toString(), ProjectState.Proceeding.toString())) {
                projectIdList.add(projectMember.getProject().getId());
            }
        }
        return projectIdList.size();
    }

    public int getDoneProjectSize(String email){
        List<ProjectMember> projectMemberList = projectMemberRepository.findByEmail(email);
        List<Long> projectIdList = new ArrayList<>();
        for (ProjectMember projectMember: projectMemberList){
            if (StringUtils.equals(projectMember.getProject().getProjectState().toString(), ProjectState.Done.toString())) {
                projectIdList.add(projectMember.getProject().getId());
            }
        }
        return projectIdList.size();
    }

    public ProjectDto getProjectDtl(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(EntityNotFoundException::new);
        ProjectDto projectDto = new ProjectDto(project);
        List<ProjectMember> projectMemberList = projectMemberRepository.findByProjectId(project.getId());
        List<Long> memberIdList = new ArrayList<>();
        for (ProjectMember member: projectMemberList){
            memberIdList.add(member.getMember().getId());
        }
        projectDto.setProjectMemberIdList(memberIdList);
        return projectDto;
    }

    public boolean validateProject(Long projectId, String email) {
        Member curMember = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        ProjectMember projectMember = projectMemberRepository.findByMemberIdAndProjectId(curMember.getId(), projectId);
        return StringUtils.equals(ProjectMemberRole.ADMIN.toString(), projectMember.getProjectMemberRole().toString());
    }

    public Long updateProject(Long projectId, ProjectDto projectDto, String email) {
        Project project = projectRepository.findById(projectId).orElseThrow(EntityNotFoundException::new);
        if (validateProject(projectId, email)) {
            project.updateProject(projectDto);
        }
        return project.getId();
    }

    public Long deleteProject(Long projectId,String email) {
        Project project = projectRepository.findById(projectId).orElseThrow(EntityNotFoundException::new);
        if (validateProject(projectId, email)) {
            projectRepository.delete(project);
        }
        return project.getId();
    }
}
