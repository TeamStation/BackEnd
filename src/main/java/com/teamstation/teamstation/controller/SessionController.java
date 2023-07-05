package com.teamstation.teamstation.controller;


import com.teamstation.teamstation.dto.ProjectSessionDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectSession;
import com.teamstation.teamstation.entity.Session;
import com.teamstation.teamstation.service.MemberService;
import com.teamstation.teamstation.service.ProjectService;
import com.teamstation.teamstation.service.ProjectSessionService;
import com.teamstation.teamstation.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        // 3. 세션 생성
        Session session = new Session();
        session.setSessionName(projectSessionDto.getSession().getSessionName());
        session.setSessionDateTime(projectSessionDto.getSession().getSessionDateTime());
        session.setSessionState(projectSessionDto.getSession().getSessionState());
        sessionService.createSession(session);

        // 4. 프로젝트 세션 생성
        ProjectSession projectSession = new ProjectSession();
        projectSession.setSession(session);
        projectSession.setProject(project);
        ProjectSession createdProjectSession = projectSessionService.createProjectSession(projectSession);

        return ResponseEntity.ok(createdProjectSession);
    }
}
