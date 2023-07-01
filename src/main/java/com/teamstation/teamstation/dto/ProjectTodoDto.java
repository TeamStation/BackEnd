package com.teamstation.teamstation.dto;

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
    private List<MemberDTO> teamMembers; //할 일 목록 팀원리스트

    public ProjectTodoDto() {
        // 기본 생성자 내용 추가
    }


    @Builder
    public ProjectTodoDto(Long id, TodoDto todo, ProjectDto project, String category, List<MemberDTO> teamMembers) {
        this.id = id;
        this.todo = todo;
        this.project = project;
        this.category = category;
        this.teamMembers = teamMembers;
    }
}
