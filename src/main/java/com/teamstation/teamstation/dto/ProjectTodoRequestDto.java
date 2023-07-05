package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.constant.TodoState;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectTodoRequestDto {
    private String todoName;
    private List<Long> memberIds;
    private String category;
    private String todoDeadLine;
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

    public String getTodoDeadLine() {
        return todoDeadLine;
    }

    public TodoState getTodoState() {
        return todoState;
    }

    public ProjectTodoRequestDto() {
    }


    @Builder
    public ProjectTodoRequestDto(String todoName, List<Long> memberIds, String category, String todoDeadLine, TodoState todoState) {
        this.todoName = todoName;
        this.memberIds = memberIds;
        this.category = category;
        this.todoDeadLine = todoDeadLine;
        this.todoState = todoState;
    }
}

