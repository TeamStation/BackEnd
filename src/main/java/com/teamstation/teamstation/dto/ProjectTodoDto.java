package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.constant.TodoState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectTodoDto {
    private Long id;
    private TodoDto todo;
    private ProjectDto project;
    private String category;
    private List<MemberDto> teamMembers; //할 일 목록 팀원리스트

    public ProjectTodoDto() {
        // 기본 생성자 내용 추가
    }


    public TodoState getTodoState() {
        return this.todo.getTodoState();
    }


    @Builder
    public ProjectTodoDto(Long id, TodoDto todo, ProjectDto project, String category, List<MemberDto> teamMembers) {
        this.id = id;
        this.todo = todo;
        this.project = project;
        this.category = category;
        this.teamMembers = teamMembers;
    }
}
