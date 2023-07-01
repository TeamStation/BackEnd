package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.constant.TodoState;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectTodoRequestDTO {
    private String todoName;
    private List<Long> memberIds;
    private String category;
    private LocalDateTime todoDeadLine;
    private TodoState todoState;

    public String getTodoName() {
        return todoName;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }
    public String getCategory() {
        return category;
    }

    public LocalDateTime getTodoDeadLine() {
        return todoDeadLine;
    }

    public TodoState getTodoState() {
        return todoState;
    }

    public ProjectTodoRequestDTO() {
    }


    @Builder
    public ProjectTodoRequestDTO(String todoName, List<Long> memberIds, String category, LocalDateTime todoDeadLine, TodoState todoState) {
        this.todoName = todoName;
        this.memberIds = memberIds;
        this.category = category;
        this.todoDeadLine = todoDeadLine;
        this.todoState = todoState;
    }
}

