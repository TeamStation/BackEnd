package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.constant.TodoState;

import java.time.LocalDateTime;

import com.teamstation.teamstation.entity.Todo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 할 일 데이터 정보를 전달하는 DTO
public class TodoDto {
    private Long id;
    private String todoName;
    private LocalDateTime todoRegDate;
    private LocalDateTime todoUpdateDate;
    private LocalDateTime todoDeadLine;
    private TodoState todoState;

    public Todo toEntity() {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTodoName(todoName);
        todo.setTodoRegDate(todoRegDate);
        todo.setTodoUpdateDate(todoUpdateDate);
        todo.setTodoDeadLine(todoDeadLine);
        todo.setTodoState(todoState);

        return todo;
    }

    public static TodoDto fromEntity(Todo todo) {
        TodoDto todoDto = new TodoDto();
        todoDto.setId(todo.getId());
        todoDto.setTodoName(todo.getTodoName());
        todoDto.setTodoRegDate(todo.getTodoRegDate());
        todoDto.setTodoUpdateDate(todo.getTodoUpdateDate());
        todoDto.setTodoDeadLine(todo.getTodoDeadLine());
        todoDto.setTodoState(todo.getTodoState());
        return todoDto;
    }
}
