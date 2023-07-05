package com.teamstation.teamstation.controller;

import com.teamstation.teamstation.dto.*;
import com.teamstation.teamstation.entity.*;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.repository.ProjectTodoMemberRepository;
import com.teamstation.teamstation.repository.ProjectTodoRepository;
import com.teamstation.teamstation.service.MemberService;
import com.teamstation.teamstation.service.ProjectService;
import com.teamstation.teamstation.service.ProjectTodoService;
import com.teamstation.teamstation.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class ProjectTodoController {

    private final ProjectTodoRepository projectTodoRepository;
    private final ProjectTodoMemberRepository projectTodoMemberRepository;
    private final MemberRepository memberRepository;
    @Autowired
    private final ProjectTodoService projectTodoService;

    private final MemberService memberService;

    private final TodoService todoService;
    private final ProjectService projectService;

    private Map<String, List<ProjectTodo>> projectTodos = new HashMap<>();

    public ProjectTodoController(ProjectTodoService projectTodoService, MemberService memberService, TodoService todoService, ProjectService projectService, ProjectTodoRepository projectTodoRepository, ProjectTodoMemberRepository projectTodoMemberRepository, MemberRepository memberRepository) {
        this.projectTodoService = projectTodoService;
        this.memberService = memberService;
        this.todoService = todoService;
        this.projectService = projectService;
        this.projectTodoRepository = projectTodoRepository;
        this.projectTodoMemberRepository = projectTodoMemberRepository;
        this.memberRepository = memberRepository;
    }

    // ProjectTodo 목록 전체 조회 API
    @GetMapping(value="/{userId}/project/{projectId}/todos")
    public ResponseEntity<List<Map<String, Object>>> getTodosByProject(@PathVariable Long userId, @PathVariable Long projectId) {
        // 1. 사용자 조회
        Member user = memberService.getMemberById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. 프로젝트 조회
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        // 프로젝트헤 해당하는 할 일 목록 조회
        List<ProjectTodo> todos = projectTodoRepository.findByProjectId(projectId);
        if (todos.isEmpty()) {
            // 프로젝트에 해당하는 Todo가 없을 경우 404 상태 코드 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (ProjectTodo todo : todos) {
            Map<String, Object> todoMap = new HashMap<>();
            todoMap.put("todoName", todo.getTodo().getTodoName());
            todoMap.put("category", todo.getCategory());
            todoMap.put("deadlineTime", todo.getTodo().getTodoDeadLine());
            todoMap.put("members", todo.getMembers().stream().map(Member::getMemberName).collect(Collectors.toList()));
            todoMap.put("todoState", todo.getTodo().getTodoState());

            result.add(todoMap);
        }

        return ResponseEntity.ok(result);
    }

    // ProjectTodo 생성 API
    @PostMapping(value="/{userId}/project/{projectId}/todos/new")
    public ResponseEntity<String> createProjectTodo(
            @PathVariable Long userId,
            @PathVariable("projectId") Long projectId,
            @RequestBody ProjectTodoRequestDto requestDto
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

        // 3. 할 일 생성
        Todo todo = new Todo();
        todo.setTodoName(requestDto.getTodoName());
        // todo.setTodoDeadLine(requestDto.getTodoDeadLine());
        if (requestDto.getTodoDeadLine() != null) {
            String todoDeadLine = requestDto.getTodoDeadLine();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            LocalDateTime todoDeadLine = LocalDateTime.parse(todoDeadLineString, formatter);
            todo.setTodoDeadLine(todoDeadLine);
        }
        todo.setTodoState(requestDto.getTodoState());

        Todo savedTodo = todoService.createTodo(todo);

        // 4. 멤버 조회
        List<Member> members = memberService.getMembersByIds(requestDto.getMemberIds());

        // 5. 프로젝트 할 일 생성
        ProjectTodo projectTodo = new ProjectTodo();
        projectTodo.setTodo(savedTodo);
        projectTodo.setProject(project);
        projectTodo.setMembers(members);
        projectTodo.setCategory(requestDto.getCategory());

        // 6. 멤버와 프로젝트 할 일의 관계 생성
        List<ProjectTodoMember> projectTodoMembers = new ArrayList<>();
        for (Member member : members) {
            ProjectTodoMember projectTodoMember = new ProjectTodoMember();
            projectTodoMember.setMember(member);
            projectTodoMember.setProjectTodo(projectTodo);
            projectTodoMembers.add(projectTodoMember);
        }

        // 한 번에 저장
        projectTodoMemberRepository.saveAll(projectTodoMembers);


        ProjectTodo savedProjectTodo = projectTodoService.createProjectTodo(projectTodo);

        return ResponseEntity.ok("ProjectTodo created successfully with ID: " + savedProjectTodo.getId());
    }

    // ProjectTodo 삭제 API
    @DeleteMapping("/{userId}/project/{projectId}/todos/{projectTodoId}")
    public ResponseEntity<String> deleteTodo(
            @PathVariable("userId") Long userId,
            @PathVariable("projectId") Long projectId,
            @PathVariable("projectTodoId") Long projectTodoId
    ) {
        try {
            // 1. 사용자 조회
            Member user = memberService.getMemberById(userId);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            // 2. 프로젝트 조회
            Project project = projectService.getProjectById(projectId);
            if (project == null) {
                return ResponseEntity.notFound().build();
            }

            // 3. 프로젝트 할 일 조회
            Optional<ProjectTodo> optionalProjectTodo = projectTodoRepository.findById(projectTodoId);
            if (optionalProjectTodo.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            ProjectTodo projectTodo = optionalProjectTodo.get();

            // 4. 프로젝트 할 일에 연관된 멤버 정보 삭제
            List<ProjectTodoMember> membersToRemove = projectTodoMemberRepository.findByProjectTodo(projectTodo);
            for (ProjectTodoMember member : membersToRemove) {
                projectTodoMemberRepository.delete(member);
            }

            // 5. 프로젝트 할 일 삭제
            projectTodoRepository.delete(projectTodo);

            return ResponseEntity.ok("할 일이 삭제되었습니다.");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("할 일을 삭제하는 동안 오류가 발생했습니다.");
        }
    }


    // ProjectTodo 수정 API
    @PostMapping(value="/{userId}/project/{projectId}/todos/{projectTodoId}")
    @Transactional
    public ResponseEntity<String> updateTodoByProject(
            @PathVariable("userId") Long userId,
            @PathVariable("projectId") Long projectId,
            @PathVariable("projectTodoId") Long projectTodoId,
            @RequestBody ProjectTodoRequestDto projectTodoRequestDto
    ) {
        // 1. 사용자 조회
        Member user = memberService.getMemberById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }

        // 2. 프로젝트 조회
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("프로젝트를 찾을 수 없습니다.");
        }

        // 3. 프로젝트 할 일 조회
        ProjectTodo projectTodo = projectTodoRepository.findById(projectTodoId).orElse(null);
        if (projectTodo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("프로젝트 할 일을 찾을 수 없습니다.");
        }

        // 4. 프로젝트 할 일 멤버 업데이트
        if (projectTodoRequestDto.getMemberIds() != null) {
            List<Long> newMemberIds = projectTodoRequestDto.getMemberIds();

            // 기존 멤버 중에서 새로운 멤버 목록에 포함되지 않은 멤버 삭제
            List<ProjectTodoMember> existingMembers = projectTodoMemberRepository.findByProjectTodoId(projectTodoId);
            for (ProjectTodoMember existingMember : existingMembers) {
                if (!newMemberIds.contains(existingMember.getMember().getId())) {
                    projectTodoMemberRepository.delete(existingMember);
                }
            }

            // 새로운 멤버 목록 생성
            List<Member> newMembers = new ArrayList<>();
            for (Long memberId : newMemberIds) {
                Member member = memberRepository.findById(memberId).orElse(null);
                if (member != null) {
                    newMembers.add(member);
                }
            }

            // 매핑된 멤버 목록을 모두 삭제하고 새로운 멤버 목록을 추가
            projectTodo.setMembers(newMembers);
        }



        // 5. 프로젝트 할 일 업데이트
        if (projectTodoRequestDto.getCategory() != null) {
            projectTodo.setCategory(projectTodoRequestDto.getCategory());
        }
        if (projectTodoRequestDto.getTodoName() != null) {
            projectTodo.getTodo().setTodoName(projectTodoRequestDto.getTodoName());
        }
//        if (projectTodoRequestDto.getTodoDeadLine() != null) {
//            projectTodo.getTodo().setTodoDeadLine(projectTodoRequestDto.getTodoDeadLine());
//        }
        if (projectTodoRequestDto.getTodoState() != null) {
            projectTodo.getTodo().setTodoState(projectTodoRequestDto.getTodoState());
        }

        // todoDeadLine 값 업데이트
        if (projectTodoRequestDto.getTodoDeadLine() != null) {
            String todoDeadLine = projectTodoRequestDto.getTodoDeadLine();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            LocalDateTime todoDeadLine = LocalDateTime.parse(todoDeadLineString, formatter);
            projectTodo.getTodo().setTodoDeadLine(todoDeadLine);
        }

        // 프로젝트 할 일을 저장
        projectTodoRepository.save(projectTodo);

        return ResponseEntity.ok("프로젝트 할 일이 성공적으로 업데이트되었습니다.");
    }

}
