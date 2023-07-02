package com.teamstation.teamstation.controller;

import com.teamstation.teamstation.dto.*;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectTodo;
import com.teamstation.teamstation.entity.Todo;
import com.teamstation.teamstation.repository.ProjectTodoRepository;
import com.teamstation.teamstation.service.MemberService;
import com.teamstation.teamstation.service.ProjectService;
import com.teamstation.teamstation.service.ProjectTodoService;
import com.teamstation.teamstation.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
public class ProjectTodoController {

    private final ProjectTodoRepository projectTodoRepository;
    @Autowired
    private final ProjectTodoService projectTodoService;

    private final MemberService memberService;

    private final TodoService todoService;
    private final ProjectService projectService;

    private Map<String, List<ProjectTodo>> projectTodos = new HashMap<>();

    public ProjectTodoController(ProjectTodoService projectTodoService, MemberService memberService, TodoService todoService, ProjectService projectService, ProjectTodoRepository projectTodoRepository) {
        this.projectTodoService = projectTodoService;
        this.memberService = memberService;
        this.todoService = todoService;
        this.projectService = projectService;
        this.projectTodoRepository = projectTodoRepository;
    }

    // ProjectTodo 목록 전체 조회 API
    @GetMapping(value="/{projectId}/todos")
    public ResponseEntity<List<Map<String, Object>>> getTodosByProject(@PathVariable Long projectId) {
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
    @PostMapping(value="/{projectId}/todos/create")
    public ResponseEntity<String> createProjectTodo(@PathVariable("projectId") Long projectId, @RequestBody ProjectTodoRequestDto requestDTO) {
        // 1. 프로젝트 조회
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. 할 일 생성
        Todo todo = new Todo();
        todo.setTodoName(requestDTO.getTodoName());
        todo.setTodoDeadLine(requestDTO.getTodoDeadLine());
        todo.setTodoState(requestDTO.getTodoState());
        // Set other fields of Todo

        Todo savedTodo = todoService.createTodo(todo);

        // 3. 멤버 조회
        List<Member> members = new ArrayList<>();
        for (Long memberId : requestDTO.getMemberIds()) {
            Member member = memberService.getMemberById(memberId);
            if (member != null) {
                members.add(member);
            }
        }
        // 4. 프로젝트 할 일 생성
        ProjectTodo projectTodo = new ProjectTodo();
        projectTodo.setTodo(savedTodo);
        projectTodo.setProject(project);
        projectTodo.setMembers(members);
        projectTodo.setCategory(requestDTO.getCategory());

        ProjectTodo savedProjectTodo = projectTodoService.createProjectTodo(projectTodo);

        return ResponseEntity.ok("ProjectTodo created successfully with ID: " + savedProjectTodo.getId());
    }

    // ProjectTodo 삭제 API
    @DeleteMapping("/{projectId}/todos/delete/{id}")
    public void deleteTodo(@PathVariable("projectId") Long projectId, @PathVariable("id") Long id)  {
        projectTodoRepository.deleteById(id);
    }

    // ProjectTodo 수정 API
    @PutMapping(value="/{projectId}/todos/{todoId}")
    public ResponseEntity<String> updateTodoByProject(@PathVariable Long projectId, @PathVariable Long todoId, @RequestBody ProjectTodoDto projectTodoDTO) {
        // projectId에 해당하는 프로젝트 할 일 목록을 조회합니다.
        List<ProjectTodo> todos = projectTodoRepository.findByProjectId(projectId);
        Optional<ProjectTodo> optionalProjectTodo = todos.stream()
                .filter(todo -> todo.getId().equals(todoId))
                .findFirst();

        if (!optionalProjectTodo.isPresent()) {
            // 요청된 todoId에 해당하는 프로젝트 할 일을 찾을 수 없을 경우 404 상태 코드 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("프로젝트 할 일을 찾을 수 없습니다.");
        }

        ProjectTodo projectTodo = optionalProjectTodo.get();

        // ProjectTodoDTO에서 전달된 정보를 사용하여 프로젝트 할 일을 업데이트합니다.
        projectTodo.setCategory(projectTodoDTO.getCategory());

        // 프로젝트 할 일을 저장합니다.
        projectTodoRepository.save(projectTodo);

        return ResponseEntity.ok("프로젝트 할 일이 성공적으로 업데이트되었습니다.");
    }
}
