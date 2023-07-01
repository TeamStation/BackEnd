package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.entity.ProjectTodo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProjectTodoDTO {
    private Long id;
    private TodoDTO todo;
    private ProjectDTO project;
    private String category;
    private List<MemberDTO> teamMembers; //할 일 목록 팀원리스트

    public ProjectTodoDTO() {
        // 기본 생성자 내용 추가
    }


    @Builder
    public ProjectTodoDTO(Long id, TodoDTO todo, ProjectDTO project, String category, List<MemberDTO> teamMembers) {
        this.id = id;
        this.todo = todo;
        this.project = project;
        this.category = category;
        this.teamMembers = teamMembers;
    }
}
