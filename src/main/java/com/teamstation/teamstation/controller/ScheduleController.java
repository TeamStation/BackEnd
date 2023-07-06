package com.teamstation.teamstation.controller;

import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectSchedule;
import com.teamstation.teamstation.entity.Schedule;
import com.teamstation.teamstation.service.MemberService;
import com.teamstation.teamstation.service.ProjectScheduleService;
import com.teamstation.teamstation.service.ProjectService;
import com.teamstation.teamstation.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ProjectService projectService;
    private final MemberService memberService;
    private final ProjectScheduleService projectScheduleService;

    public ScheduleController(ScheduleService scheduleService, ProjectService projectService, MemberService memberService, ProjectScheduleService projectScheduleService) {
        this.scheduleService = scheduleService;
        this.projectService = projectService;
        this.memberService = memberService;
        this.projectScheduleService = projectScheduleService;
    }

    // 일정 생성 API
    @PostMapping("/{userId}/project/{projectId}/schedule/new")
    public ResponseEntity<ProjectSchedule> createSchedule(
            @PathVariable Long userId,
            @PathVariable Long projectId,
            @RequestBody Schedule schedule
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

        // 4. 스케줄 생성
        Schedule createdSchedule = scheduleService.createSchedule(schedule);

        // 5. 프로젝트 일정 생성
        ProjectSchedule projectSchedule = new ProjectSchedule();
        projectSchedule.setSchedule(createdSchedule);
        projectSchedule.setProject(project);

        ProjectSchedule createdProjectSchedule = projectScheduleService.createProjectSchedule(projectSchedule);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProjectSchedule);
    }
}