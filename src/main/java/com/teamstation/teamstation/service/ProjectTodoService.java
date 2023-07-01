package com.teamstation.teamstation.service;

import com.teamstation.teamstation.entity.ProjectTodo;
import com.teamstation.teamstation.repository.ProjectTodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProjectTodoService {
    private final ProjectTodoRepository projectTodoRepository;

    @Autowired
    public ProjectTodoService(ProjectTodoRepository projectTodoRepository) {
        this.projectTodoRepository = projectTodoRepository;
    }

    // ProjectTodo 목록 조회
    @Transactional
    public List<ProjectTodo> getAllProjectTodos() {
        return projectTodoRepository.findAll();
    }

    // 프로젝트별 ProjectTodo 목록 조회
    @Transactional
    public List<ProjectTodo> getProjectTodosByProjectId(Long projectId) {
        return projectTodoRepository.findByProjectId(projectId);
    }

    // ProjectTodo 생성
    @Transactional
    public ProjectTodo createProjectTodo(ProjectTodo projectTodo) {
        return projectTodoRepository.save(projectTodo);
    }

    // 수정, 삭제 메소드 추가 구현
}




