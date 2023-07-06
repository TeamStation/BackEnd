package com.teamstation.teamstation.controller;


import com.teamstation.teamstation.dto.ProjectSessionDto;
import com.teamstation.teamstation.dto.SessionDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectSession;
import com.teamstation.teamstation.entity.Session;
import com.teamstation.teamstation.service.MemberService;
import com.teamstation.teamstation.service.ProjectService;
import com.teamstation.teamstation.service.ProjectSessionService;
import com.teamstation.teamstation.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SessionController {

    private final SessionService sessionService;
    private final ProjectSessionService projectSessionService;
    private final MemberService memberService;
    private final ProjectService projectService;

    public SessionController(SessionService sessionService ,ProjectSessionService projectSessionService, MemberService memberService, ProjectService projectService) {
        this.sessionService = sessionService;
        this.projectSessionService = projectSessionService;
        this.memberService = memberService;
        this.projectService = projectService;
    }

    // 회의 조회 API
    @GetMapping(value="/{userId}/project/{projectId}/session")
    public ResponseEntity<List<ProjectSession>> getProjectSessions(
            @PathVariable Long userId,
            @PathVariable Long projectId
    ) {
        // 1. 유저 조회
        Member user = memberService.getMemberById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. 프로젝트 조회
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        // 3. 유저가 프로젝트에 속해 있는지 확인
        boolean isUserInProject = projectService.isUserInProject(user, project);
        if (!isUserInProject) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // 4. 프로젝트의 모든 회의 조회
        List<ProjectSession> projectSessions = projectSessionService.getProjectSessionsByProject(project);

        return ResponseEntity.ok(projectSessions);
    }

    // 회의 생성 API
    @PostMapping(value="/{userId}/project/{projectId}/session/new")
    public ResponseEntity<ProjectSession> createProjectSession(
            @PathVariable Long userId,
            @PathVariable Long projectId,
            @RequestBody ProjectSessionDto projectSessionDto
    ) {

        // 1. 유저 조회
        Member user = memberService.getMemberById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. 프로젝트 조회
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        // 3. 사용자가 프로젝트에 속해 있는지 확인
        if (!projectService.isUserInProject(user, project)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // 4. 세션 생성
        Session session = new Session();
        session.setSessionName(projectSessionDto.getSession().getSessionName());
        session.setSessionDateTime(projectSessionDto.getSession().getSessionDateTime());
        session.setSessionState(projectSessionDto.getSession().getSessionState());
        sessionService.createSession(session);

        // 5. 프로젝트 세션 생성
        ProjectSession projectSession = new ProjectSession();
        projectSession.setSession(session);
        projectSession.setProject(project);
        ProjectSession createdProjectSession = projectSessionService.createProjectSession(projectSession);

        return ResponseEntity.ok(createdProjectSession);
    }

    // 회의 삭제 API
    @DeleteMapping("/{userId}/project/{projectId}/session/{sessionId}")
    public ResponseEntity<Void> deleteProjectSession(
            @PathVariable Long userId,
            @PathVariable Long projectId,
            @PathVariable Long sessionId
    ) {
        // 1. 유저 조회
        Member user = memberService.getMemberById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. 프로젝트 조회
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        // 3. 세션 조회
        ProjectSession projectSession = projectSessionService.getProjectSessionById(sessionId);
        if (projectSession == null) {
            return ResponseEntity.notFound().build();
        }

        // 4. 사용자가 프로젝트에 속해 있는지 확인
        if (!projectService.isUserInProject(user, project)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // 5. 프로젝트와 세션의 연관 관계 확인
        if (!projectSession.getProject().equals(project)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // 6. 회의 삭제
        projectSessionService.deleteProjectSession(projectSession);

        return ResponseEntity.noContent().build();
    }

    // 회의 수정 API
    @PostMapping(value="/{userId}/project/{projectId}/session/{sessionId}")
    public ResponseEntity<ProjectSession> updateProjectSession(
            @PathVariable Long userId,
            @PathVariable Long projectId,
            @PathVariable Long sessionId,
            @RequestBody SessionDto sessionDto
    ) {
        // 1. 유저 조회
        Member user = memberService.getMemberById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. 프로젝트 조회
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        // 3. 회의 조회
        ProjectSession projectSession = projectSessionService.getProjectSessionById(sessionId);
        if (projectSession == null) {
            return ResponseEntity.notFound().build();
        }

        // 4. 회의 속한 프로젝트 확인
        if (!projectSession.getProject().equals(project)) {
            return ResponseEntity.badRequest().build();
        }

        // 5. 사용자가 프로젝트에 속해 있는지 확인
        if (!projectService.isUserInProject(user, project)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // 6. 회의 정보 수정
        Session session = projectSession.getSession();
        session.setSessionName(sessionDto.getSessionName());
        session.setSessionDateTime(sessionDto.getSessionDateTime());
        session.setSessionState(sessionDto.getSessionState());

        // 7. 수정된 회의 저장
        projectSessionService.updateProjectSession(projectSession);

        return ResponseEntity.ok(projectSession);
    }
}
